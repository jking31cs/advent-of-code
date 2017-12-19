package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PermutationPromenade {
    static String programs = "abcdefghijklmnop";

    static List<String> parseFile(File f) throws FileNotFoundException {
        List<String> toRet = new ArrayList<>();
        try (Scanner scanner = new Scanner(f)) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                toRet.add(scanner.next());
            }
        }
        return toRet;
    }


    public static String dance(List<String> instructions) {
        for (String inst : instructions) {
            inst = inst.trim();
            if (inst.startsWith("s")) {
                int spinAmount = Integer.parseInt(inst.substring(1));
                for (int i = 0; i < spinAmount; i++) {
                    String lastBit = programs.substring(programs.length() - 1);
                    String frontBit = programs.substring(0, programs.length() - 1);
                    programs = lastBit.concat(frontBit);
                }
            } else if (inst.startsWith("x")) {
                String[] split = inst.substring(1).split("/");
                char[] arr = programs.toCharArray();
                char temp = arr[Integer.parseInt(split[0])];
                arr[Integer.parseInt(split[0].trim())] = arr[Integer.parseInt(split[1].trim())];
                arr[Integer.parseInt(split[1].trim())] = temp;
                programs = new String(arr);
            } else if (inst.startsWith("p")) {
                String[] split = inst.substring(1).split("/");
                char[] arr = programs.toCharArray();
                int[] indices = new int[2];
                for (int i = 0 ; i < arr.length; i++) {
                    if (arr[i] == split[0].trim().toCharArray()[0]) {
                        indices[0] = i;
                    }
                    if (arr[i] == split[1].trim().toCharArray()[0]) {
                        indices[1] = i;
                    }
                }

                char temp = arr[indices[0]];
                arr[indices[0]] = arr[indices[1]];
                arr[indices[1]] = temp;
                programs = new String(arr);
            }
        }
        return programs;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> instructions = parseFile(new File("day16input.txt"));
        long repeatSteps = 0;
        for (long i = 0; i < 1000000000; i++) {
            dance(instructions);
            if (programs.equals("abcdefghijklmnop")) {
                repeatSteps = i+1;
                break;
            }
        }
        programs = "abcdefghijklmnop";
        long startIndex = 1000000000;
        while (startIndex % repeatSteps != 0) startIndex-=1;
        for (long i = startIndex; i < 1000000000; i++) {
            dance(instructions);
        }
        System.out.println(programs);
    }
}
