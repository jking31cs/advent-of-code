package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HexEd {

    public static int stepCount(List<String> input) {
        int count = 0;
        int maxCount = 0;
        List<String> visited = new ArrayList<>();
        for (String dir : input) {
            if (visited.isEmpty()) {
                count+=1;
                visited.add(dir);
                continue;
            }
            switch (dir) {
                case "n":
                    if (visited.contains("s")) {
                        count -= 1;
                        visited.remove("s");
                    } else if (visited.contains("se")) {
                        visited.remove("se");
                        visited.add("ne");
                    } else if (visited.contains("sw")) {
                        visited.remove("sw");
                        visited.add("nw");
                    } else {
                        visited.add(dir);
                        count += 1;
                    }
                    break;
                case "ne":
                    if (visited.contains("sw")) {
                        count -= 1;
                        visited.remove("sw");
                    } else if (visited.contains("s")) {
                        visited.remove("s");
                        visited.add("se");
                    } else if (visited.contains("nw")) {
                        visited.remove("nw");
                        visited.add("n");
                    } else {
                        visited.add(dir);
                        count += 1;
                    }
                    break;
                case "se":
                    if (visited.contains("nw")) {
                        count -= 1;
                        visited.remove("nw");
                    } else if (visited.contains("n")) {
                        visited.remove("n");
                        visited.add("ne");
                    } else if (visited.contains("sw")) {
                        visited.remove("sw");
                        visited.add("s");
                    } else {
                        visited.add(dir);
                        count += 1;
                    }
                    break;
                case "s":
                    if (visited.contains("n")) {
                        count -= 1;
                        visited.remove("n");
                    } else if (visited.contains("ne")) {
                        visited.remove("ne");
                        visited.add("se");
                    } else if (visited.contains("nw")) {
                        visited.remove("nw");
                        visited.add("sw");
                    } else {
                        visited.add(dir);
                        count += 1;
                    }
                    break;
                case "sw":
                    if (visited.contains("ne")) {
                        count -= 1;
                        visited.remove("ne");
                    } else if (visited.contains("n")) {
                        visited.remove("n");
                        visited.add("nw");
                    } else if (visited.contains("se")) {
                        visited.remove("se");
                        visited.add("s");
                    } else {
                        visited.add(dir);
                        count += 1;
                    }
                    break;
                case "nw":
                    if (visited.contains("se")) {
                        count -= 1;
                        visited.remove("se");
                    } else if (visited.contains("s")) {
                        visited.remove("s");
                        visited.add("sw");
                    } else if (visited.contains("ne")) {
                        visited.remove("ne");
                        visited.add("n");
                    } else {
                        visited.add(dir);
                        count += 1;
                    }
                    break;
                default:
                    throw new RuntimeException("don't recognize " + dir);
            }
            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }

    public static List<String> parseFile(File f) throws FileNotFoundException {
        List<String> toRet = new ArrayList<>();
        try (Scanner scanner = new Scanner(f)) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                toRet.add(scanner.next().trim());
            }
        }
        return toRet;

    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(stepCount(parseFile(new File("day11input.txt"))));
    }
}
