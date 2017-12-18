package com.jking31cs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javafx.util.Pair;

public class DiskDefragmentation {

    public static int usedCount(String input) {
        int count = 0;
        for (int i = 0; i < 128; i++) {
            String tempInput = input + "-" + i;
            String binary = row(KnotHash.knotHash(tempInput));
            for (char c : binary.toCharArray()) {
                if (c == '1') count += 1;
            }
        }
        return count;
    }

    public static int regionCount(String input) {
        int count = 0;
        //First create the grid
        String[] grid = new String[128];
        for (int i = 0; i < 128; i++) {
            String tempInput = input + "-" + i;
            String hex = KnotHash.knotHash(tempInput);
            String binary = "";
            for (char c : hex.toCharArray()) {
                int value = Integer.parseInt(Character.toString(c), 16);
                String temp = Integer.toBinaryString(value);
                while (temp.length() < 4) temp = "0".concat(temp);
                binary = binary.concat(temp);
            }
            grid[i] = binary;
        }

        Map<Pair<Integer, Integer>, Integer> regions = new HashMap<>();
        Map<Integer, List<Pair<Integer, Integer>>> labeledRegions = new HashMap<>();
        int currentRegion = 0;
        Stack<Pair<Integer, Integer>> toVisit = new Stack<>();
        Set<Pair<Integer, Integer>> visited = new HashSet<>(128*128);
        for (int i = 127; i >= 0; i--) {
            for (int j = 127; j >= 0; j--) toVisit.push(new Pair<>(j,i));
        }
        while (!toVisit.isEmpty()) {
            Pair<Integer, Integer> coord = toVisit.pop();
            if (visited.contains(coord)) continue;
            if (grid[coord.getKey()].toCharArray()[coord.getValue()] == '1') {
                boolean sub1X = coord.getKey() > 0;
                boolean sub1Y = coord.getValue() > 0;
                boolean add1X = coord.getKey()  < 127;
                boolean add1Y = coord.getValue() < 127;
                boolean addedToRegions = false;
                if (sub1X) {
                    Pair<Integer, Integer> toCheck = new Pair<>(coord.getKey() - 1, coord.getValue());
                    if (regions.containsKey(toCheck)) {
                        regions.put(coord, regions.get(toCheck));
                        labeledRegions.get(regions.get(toCheck)).add(coord);
                        addedToRegions = true;
                    } else {
                        toVisit.push(toCheck);
                    }
                }
                if (sub1Y) {
                    Pair<Integer, Integer> toCheck = new Pair<>(coord.getKey(), coord.getValue() - 1);
                    if (regions.containsKey(toCheck)) {
                        regions.put(coord, regions.get(toCheck));
                        labeledRegions.get(regions.get(toCheck)).add(coord);
                        addedToRegions = true;
                    } else {
                        toVisit.push(toCheck);
                    }
                }
                if (add1X) {
                    Pair<Integer, Integer> toCheck = new Pair<>(coord.getKey() + 1, coord.getValue());
                    if (regions.containsKey(toCheck)) {
                        regions.put(coord, regions.get(toCheck));
                        labeledRegions.get(regions.get(toCheck)).add(coord);
                        addedToRegions = true;
                    } else {
                        toVisit.push(toCheck);
                    }
                }
                if (add1Y) {
                    Pair<Integer, Integer> toCheck = new Pair<>(coord.getKey(), coord.getValue() + 1);
                    if (regions.containsKey(toCheck)) {
                        regions.put(coord, regions.get(toCheck));
                        labeledRegions.get(regions.get(toCheck)).add(coord);
                        addedToRegions = true;
                    } else {
                        toVisit.push(toCheck);
                    }
                }
                if (!addedToRegions) {
                    currentRegion += 1;
                    regions.put(coord, currentRegion);
                    labeledRegions.put(currentRegion, new ArrayList<>(Arrays.asList(coord)));
                }
            }
            visited.add(coord);
        }


        return currentRegion;
    }

    public static String row(String hex) {
        BigInteger integer = new BigInteger(hex, 16);
        String binaryStr = integer.toString(2);
        return binaryStr;
    }

    public static void main(String[] args) {
        System.out.println(regionCount("hwlqcszp"));
    }
}
