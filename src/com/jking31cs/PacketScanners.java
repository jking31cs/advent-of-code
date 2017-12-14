package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketScanners {

    public static State prevState = new State(
            new HashMap<>(),
            new HashMap<>()
    );

    public static class State {
        Map<Integer, Integer> currentIndices;
        Map<Integer, Boolean> currentDirections;

        public State(
                Map<Integer, Integer> currentIndices,
                Map<Integer, Boolean> currentDirections
        ) {
            this.currentDirections = clone(currentDirections);
            this.currentIndices = clone(currentIndices);
        }

        private <K, V> Map<K,V> clone(Map<K, V> map) {
            Map<K,V> clone = new HashMap<>();
            for (K key : map.keySet()) clone.put(key, map.get(key));
            return clone;
        }
    }

    public static Map<Integer, Integer> parseFile(File f) throws FileNotFoundException {
        Map<Integer, Integer> toRet = new HashMap<>();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String[] split = s.split(":");
                toRet.put(Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim()));
            }
        }
        return toRet;
    }

    public static boolean caught(int delay, Map<Integer, Integer> map) {
        boolean caught = false;
        int layerCount = map.keySet().stream().max(Comparator.naturalOrder()).get();
        //First, set up current scanner index;
        Map<Integer, Integer> currentIndices = new HashMap<>(layerCount);
        Map<Integer, Boolean> currentDirections = new HashMap<>(layerCount);
        if (delay == 0) {
            for (int i = 0; i <= layerCount; i++) {
                currentIndices.put(i, 0);
                currentDirections.put(i, true);
            }
        } else {
            currentIndices = prevState.currentIndices;
            currentDirections = prevState.currentDirections;
            updatePositions(map, currentIndices, currentDirections);
        }

        prevState = new State(currentIndices, currentDirections);

        //Now go through the count process
        for (int i = 0; i <= layerCount; i++) {
            Integer current = map.get(i);
            if (current != null && currentIndices.get(i) == 0) {
                caught = true;
                break;
            }
            updatePositions(map, currentIndices, currentDirections);
        }
        return caught;
    }

    private static void updatePositions(Map<Integer, Integer> map, Map<Integer, Integer> currentIndices, Map<Integer, Boolean> currentDirections) {
        for (Integer key : currentIndices.keySet()) {
            if (map.get(key) == null) continue;
            boolean dir = currentDirections.get(key);
            int index = currentIndices.get(key);
            if (dir) {
                currentIndices.put(key, index + 1);
            } else {
                currentIndices.put(key, index - 1);
            }
            if (currentIndices.get(key) == 0 ||
                    currentIndices.get(key) == (map.get(key) - 1)) {
                currentDirections.put(key, !dir);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        AtomicInteger i = new AtomicInteger(0);
        AtomicBoolean b = new AtomicBoolean(false);
        Map<Integer, Integer> map = parseFile(new File("day13TestFile.txt"));
        int delay = i.getAndIncrement();
        while (caught(delay, map) && !b.get()) {
            delay = i.getAndIncrement();
            if (delay % 1000 == 0) {
                System.out.println(delay);
            }
        }
        b.set(true);
        System.out.println(delay);

//        System.out.println(caught(3941460, map));
    }
}
