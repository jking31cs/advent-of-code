package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwistyTrampoline {

    public static int[] parseInput(File f) throws FileNotFoundException {
        List<Integer> ints = new ArrayList<>();
        try (Scanner scan = new Scanner(f)) {
            while (scan.hasNextLine()) {
                ints.add(Integer.parseInt(scan.nextLine()));
            }
        }
        int[] toRet = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            toRet[i] = ints.get(i);
        }
        return toRet;
    }

    public static int escape(int[] maze) {
        int index = 0;
        int stepCount = 0;
        while (index < maze.length) {
            int curValue = maze[index];
            maze[index] += 1;
            index += curValue;
            stepCount += 1;
        }
        return stepCount;
    }

    public static int escape2(int[] maze) {
        int index = 0;
        int stepCount = 0;
        while (index < maze.length) {
            int curValue = maze[index];
            if ((curValue) >= 3) {
                maze[index] -= 1;
            } else {
                maze[index] += 1;
            }
            index += curValue;
            stepCount += 1;
        }
        return stepCount;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(escape2(parseInput(new File("day5input.txt"))));
    }
}
