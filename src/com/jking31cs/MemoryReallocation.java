package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MemoryReallocation {

    public static List<Integer> parseFile(File f) throws FileNotFoundException {
        List<Integer> toRet = new ArrayList<>(16);
        try (Scanner scanner = new Scanner(f)) {
            while(scanner.hasNextInt()) {
                toRet.add(scanner.nextInt());
            }
        }
        return toRet;
    }

    private static List<Integer> clone(List<Integer> list) {
        List<Integer> toRet = new ArrayList<>(list.size());
        for (Integer i : list) {
            toRet.add(i.intValue());
        }
        return toRet;
    }

    public static int stepsToRepeat(List<Integer> banks) {
        int steps = 0;
        Map<List<Integer>, Integer> prevLists = new HashMap<>();
        while (!prevLists.containsKey(clone(banks))) {
            prevLists.put(clone(banks), steps);

            //First find max index/value
            int maxIndex=-1, maxValue=Integer.MIN_VALUE;
            for (int i = 0; i < banks.size(); i++) {
                if (banks.get(i) > maxValue) {
                    maxIndex = i;
                    maxValue = banks.get(i);
                }
            }

            //Now redistribute
            banks.set(maxIndex, 0);
            int start = maxIndex + 1;
            while (maxValue > 0) {
                int curValue = banks.get(start % banks.size());
                banks.set(start % banks.size(), curValue+1);
                start+=1;
                maxValue-=1;
            }

            //Now increment steps
            steps+=1;
        }

        return steps - prevLists.get(banks);
    }

    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println(stepsToRepeat(Arrays.asList(0,2,7,0)));

        System.out.println(stepsToRepeat(parseFile(new File("day6input.txt"))));
    }
}
