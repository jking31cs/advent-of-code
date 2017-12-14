package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.util.Pair;

public class RecursiveCircus {

    public static Map<String, Pair<Integer, List<String>>> parseFile(File f) throws FileNotFoundException {
        Map<String, Pair<Integer, List<String>>> toRet = new HashMap<>();
        try (Scanner scan = new Scanner(f)) {
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                String[] split = s.split("->");
                String name = split[0].split(" ")[0];
                String weightStr = split[0].split(" ")[1].trim();
                Integer weight = Integer.parseInt(split[0].split(" ")[1].substring(1, weightStr.length() - 1));
                List<String> children = new ArrayList<>();
                if (split.length > 1) {
                    for (String child : split[1].trim().split(",")) {
                        child = child.trim();
                        children.add(child);
                    }
                }
                toRet.put(name, new Pair<>(weight, children));
            }
        }
        return toRet;
    }

    public static String getBottom(Map<String, Pair<Integer, List<String>>> map) {
        while (map.size() > 1) {
            //First, remove all towers not holding anything
            Set<String> toRemove = new HashSet<>();
            for (Map.Entry<String, Pair<Integer, List<String>>> entry : map.entrySet()) {
                if (entry.getValue().getValue().size() == 0) {
                    toRemove.add(entry.getKey());
                }
            }
            for (String s : toRemove) map.remove(s);

            //Now remove all children not in the map
            for (Map.Entry<String, Pair<Integer, List<String>>> entry : map.entrySet()) {
                List<String> heldTowers = entry.getValue().getValue();
                List<String> newTowers = new ArrayList<>(heldTowers.size());
                for (String s : heldTowers) {
                    if (map.containsKey(s)) newTowers.add(s);
                }
                map.put(entry.getKey(), new Pair<>(entry.getValue().getKey(), newTowers));
            }
        }
        return new ArrayList<>(map.keySet()).get(0);
    }

    public static int fixedWeight(Map<String, Pair<Integer, List<String>>> map) {
        Map<Integer, Set<String>> depthMap = new HashMap<>();
        Set<String> unvisited = new HashSet<>(map.keySet());
        Map<String, Pair<Integer, List<String>>> clone = clone(map);
        //First create a depth map
        String bottom = getBottom(clone(map));
        depthMap.put(0, new HashSet<>(Arrays.asList(bottom)));
        unvisited.remove(bottom);
        int depth = 0;
        while (unvisited.size() > 0) {
            Set<String> stuffToFetchFrom = depthMap.get(depth);
            Set<String> newStuffToAdd = new HashSet<>();
            for (String s : stuffToFetchFrom) {
                newStuffToAdd.addAll(map.get(s).getValue());
            }
            depthMap.put(depth+1, newStuffToAdd);
            unvisited.removeAll(newStuffToAdd);
            depth+=1;
        }

        //Now starting at the top, we remove everything not holding anything, and check weights and update
        //The top down.
        int goodWeight = -1;
        int badWeight = -1;
        String badEgg = null;
        while (depth > 0) {
            Set<String> toRemove = depthMap.get(depth);
            depth-=1;
            boolean hasFound = false;
            for (String s : depthMap.get(depth)) {
                Pair<Integer, List<String>> info = map.get(s);
                Map<Integer, Integer> weightCounts = new HashMap<>();
                for (String child : info.getValue()) {
                    Integer weight = map.get(child).getKey();
                    Integer count = weightCounts.get(weight);
                    if (count == null) count = 0;
                    weightCounts.put(weight, count+1);
                }
                if (weightCounts.isEmpty()) {
                    continue;
                }
                if (weightCounts.size() == 1) {
                    Integer newWeight = info.getKey() +
                            (((Integer) weightCounts.values().toArray()[0]) * ((Integer) weightCounts.keySet().toArray()[0]));
                    map.put(s, new Pair<>(newWeight, new ArrayList<>()));
                } else {
                    for (String child : info.getValue()) {
                        Integer weight = map.get(child).getKey();
                        if (weightCounts.get(weight).equals(1)) {
                            badWeight = weight;
                            badEgg = child;
                        } else {
                            goodWeight = weight;
                        }
                    }
                    hasFound = true;
                    break;
                }
            }
            if (hasFound) break;
        }

        return clone.get(badEgg).getKey() - (badWeight - goodWeight);
    }

    public static Map<String, Pair<Integer, List<String>>> clone(Map<String, Pair<Integer, List<String>>> map) {
        Map<String, Pair<Integer, List<String>>> toRet = new HashMap<>(map.size());
        for (Map.Entry<String, Pair<Integer, List<String>>> entry : map.entrySet()) {
            toRet.put(entry.getKey(), new Pair<>(entry.getValue().getKey(), new ArrayList<>(entry.getValue().getValue())));
        }
        return toRet;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(fixedWeight(parseFile(new File("day7input.txt"))));
    }
}