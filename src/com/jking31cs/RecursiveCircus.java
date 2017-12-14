package com.jking31cs;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

    public static void main(String[] args) throws FileNotFoundException {
        parseFile(new File("day7TestInput.txt"));
    }
}
