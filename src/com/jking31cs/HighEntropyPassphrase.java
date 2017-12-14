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

public class HighEntropyPassphrase {
    public static List<String> parse(File file) throws FileNotFoundException {
        List<String> toRet = new ArrayList<String>();
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                toRet.add(scan.nextLine());
            }
        }
        return toRet;
    }

    public static long numValid1(List<String> input) {
        return input.stream().filter(s -> {
            List<String> words = Arrays.asList(s.split(" "));
            Set<String> distinct = new HashSet<>(words.size());
            for (String word : words) {
                if (distinct.contains(word)) {
                    return false;
                }
                distinct.add(word);
            }
            return true;
        }).count();
    }

    public static long numValid2(List<String> input) {
        return input.stream().filter(s -> {
            List<String> words = Arrays.asList(s.split(" "));
            for (int i = 0; i < words.size(); i++) {
                for (int j = i+1; j < words.size(); j++) {
                    if (isAnagram(words.get(i), words.get(j))) {
                        return false;
                    }
                }
            }
            return true;
        }).count();
    }

    public static boolean isAnagram(String w1, String w2) {
        Map<Character, Integer> w1Info = new HashMap<>();
        Map<Character, Integer> w2Info = new HashMap<>();
        for (char c : w1.toCharArray()) {
            Integer count = w1Info.get(c);
            if (count == null) count = 0;
            w1Info.put(c, count + 1);
        }
        for (char c : w2.toCharArray()) {
            Integer count = w2Info.get(c);
            if (count == null) count = 0;
            w2Info.put(c, count + 1);
        }
        return w1Info.equals(w2Info);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(numValid2(parse(new File("day4input.txt"))));
    }
}
