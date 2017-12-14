package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class DigitalPlumber {

    public static Map<Integer, List<Integer>> parseFile(File f) throws FileNotFoundException {
        Map<Integer, List<Integer>> toRet = new HashMap<>();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String [] split = line.split("<->");
                Integer key = Integer.parseInt(split[0].trim());
                List<Integer> value = new ArrayList<>();
                for (String s : split[1].trim().split(",")) {
                    value.add(Integer.parseInt(s.trim()));
                }
                toRet.put(key, value);
            }
        }
        return toRet;
    }

    public static Set<Integer> group(int target, Map<Integer, List<Integer>> pipes) {
        Set<Integer> toVisit = new HashSet<>();
        toVisit.add(target);
        Set<Integer> visited = new HashSet<>();
        Set<Integer> group = new HashSet<>();
        group.add(target);
        while (!toVisit.isEmpty()) {
            Set<Integer> toAdd = new HashSet<>();
            for (int key : toVisit) {
                group.addAll(pipes.get(key));
                toAdd.addAll(pipes.get(key));
                visited.add(key);
            }
            toVisit.clear();
            toVisit.addAll(toAdd);
            toVisit.removeAll(visited);

        }
        return group;
    }

    public static int groupCount(Map<Integer, List<Integer>> pipes) {
        Queue<Integer> unvisited = new ArrayDeque<>(pipes.keySet());
        int groupCount = 0;
        while (!unvisited.isEmpty()) {
            Integer next = unvisited.poll();
            Set<Integer> grouping = group(next, pipes);
            groupCount+=1;
            unvisited.removeAll(grouping);
        }
        return groupCount;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(groupCount(parseFile(new File("day12input.txt"))));
    }
}
