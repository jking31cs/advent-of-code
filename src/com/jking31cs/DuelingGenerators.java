package com.jking31cs;

public class DuelingGenerators {

    static final int aFactor = 16807;
    static final int bFactor = 48271;

    public static int judgeCount(int aStart, int bStart) {
        int matchCount = 0;
        long prevAValue = aStart;
        long prevBValue = bStart;
        for (long i = 0; i < 5000000; i++) {
            //Force it to get at least 1 new value.
            prevAValue = getNextValue(prevAValue, aFactor);
            prevBValue = getNextValue(prevBValue, bFactor);
            while (prevAValue % 4 != 0) prevAValue = getNextValue(prevAValue, aFactor);
            while (prevBValue % 8 != 0) prevBValue = getNextValue(prevBValue, bFactor);

            String aBin = Long.toBinaryString(prevAValue);
            while (aBin.length() < 16) aBin = "0".concat(aBin);
            String bBin = Long.toBinaryString(prevBValue);
            while (bBin.length() < 16) bBin = "0".concat(bBin);

            if (aBin.substring(aBin.length()-16).equals(bBin.substring(bBin.length() - 16))) {
                matchCount += 1;
            }
        }
        return matchCount;
    }

    static long getNextValue(long prevValue, int factor) {
        return (prevValue * factor) % 2147483647;
    }

    public static void main(String[] args) {
        System.out.println(judgeCount(783, 325));
    }
}
