package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

public class Duet {

    static Map<String, Long> registers0 = new HashMap<>();
    static int lastInst0 = 0;
    static Queue<Long> receive0 = new ArrayDeque<>();
    static int sent0 = 0;
    static Map<String, Long> registers1 = new HashMap<>();
    static int lastInst1 = 0;
    static Queue<Long> receive1 = new ArrayDeque<>();
    static int sent1 = 0;

    private static long valueOf(String s, Map<String, Long> registers) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException nfe) {
            return registers.get(s);
        }
    }

    public static long recoveredValue(List<String> instructions) {
        long recoveredValue = 0;
        int nextInst = 0;
        long lastPlayed = 0;
        String registersStr = "abcdefghijklmnopqrstuvwxyz";
        for (char c : registersStr.toCharArray()) {
            registers0.put(Character.toString(c), 0L);
            registers1.put(Character.toString(c), 0L);
        }
        registers0.put("p", 0L);
        registers1.put("p", 1L);
        Map<String, Long> registers = registers0;
        Queue<Long> curStackToPop = receive0;
        Queue<Long> curStackToPush = receive1;
        int curId = 0;
        boolean done = false;
        while (nextInst < instructions.size()) {
            String inst = instructions.get(nextInst);
            String[] split = inst.split(" ");
            long curValue = valueOf(split[1], registers);
            switch (split[0]) {
                case "snd":
                    curStackToPush.add(curValue);
                    if (curId == 0) {
                        sent0 += 1;
                    }
                    else {
                        sent1 += 1;
                    }
                    break;
                case "set":
                    registers.put(split[1], valueOf(split[2], registers));
                    break;
                case "add":
                    registers.put(split[1], curValue + valueOf(split[2], registers));
                    break;
                case "mul":
                    curValue = registers.get(split[1]);
                    registers.put(split[1], curValue * valueOf(split[2], registers));
                    break;
                case "mod":
                    curValue = registers.get(split[1]);
                    registers.put(split[1], curValue % valueOf(split[2], registers));
                    break;
                case "rcv":
                    try {
                        long received = curStackToPop.remove();
                        registers.put(split[1], received);
                    } catch (NoSuchElementException ex) {
                        if (curId == 0) {
                            curId = 1;
                            registers = registers1;
                            curStackToPop = receive1;
                            curStackToPush = receive0;
                            lastInst0 = nextInst;
                            nextInst = lastInst1 - 1;
                        } else {
                            curId = 0;
                            registers = registers0;
                            curStackToPop = receive0;
                            curStackToPush = receive1;
                            lastInst1 = nextInst;
                            nextInst = lastInst0 - 1;
                        }
                        if (receive0.size() == 0 && receive1.size() == 0) {
                            done = true;
                        }
                    }
                    break;
                case "jgz":
                    if (curValue > 0) {
                        nextInst += (valueOf(split[2], registers) - 1);
                    }
                    break;
            }
            nextInst += 1;
            if (done) {
                break;
            }
        }
        return sent1;
    }

    static List<String> parseFile(File f) throws FileNotFoundException {
        List<String> toRet = new ArrayList<>();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) toRet.add(scanner.nextLine());
        }
        return toRet;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(recoveredValue(parseFile(new File("day18TestFile.txt"))));
    }
}
