package com.jking31cs;

import java.util.*;

public class MemoryAllocation {

    public static List<Integer> clone(List<Integer> list) {
        List<Integer> toRet = new ArrayList<>(list.size());
        for (Integer i : list) {
            toRet.add(i.intValue());
        }
        return toRet;
    }

    public static int stepsToRepeat(List<Integer> banks) {
        Set<List<Integer>> previouslySeen = new HashSet<>();
        int steps = 0;
        while (!previouslySeen.contains(clone(banks))) {

            //First, add copy of original initially
           // if (steps==0) {
                previouslySeen.add(clone(banks));
           // }

            //Now find the max value index.
            int maxIndex = -1;
            int maxValue = Integer.MIN_VALUE;
            for (int i = 0; i < banks.size(); i++) {
                if (banks.get(i) > maxValue) {
                    maxIndex = i;
                    maxValue = banks.get(i);
                }
            }

            //Now redistribute
            int distAmount = maxValue / (banks.size() - 1);
            banks.set(maxIndex, 0);
            for (int i = 1; i <= banks.size(); i++) {
                int indexToSet = (maxIndex + i) % banks.size();
                int curVal = banks.get(indexToSet);
                banks.set(indexToSet, curVal + Math.min(distAmount, maxValue));
                maxValue -= distAmount;
            }

            //Now save current banks to previously seen
            //previouslySeen.add(clone(banks));

            //Increment steps
            steps+=1;

        }
        return steps;
    }

    public static void main(String[] args) {
        //System.out.println(stepsToRepeat(Arrays.asList(0,2,7,0)));
        System.out.println(stepsToRepeat(Arrays.asList(10,3,15,10,5,15,5,15,9,2,5,8,5,2,3,6)));
    }
}
