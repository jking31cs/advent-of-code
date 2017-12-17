package com.jking31cs;

public class SpinLock {

    static int listSize = 1;
    static int lastAdded = -1;
    static int curPosition = 0;
    static int nextValue = 1;
    public static int next(int step) {
        int nextPos = curPosition;
        nextPos = (nextPos + step) % listSize;
        if (nextPos == 0) {
            lastAdded = nextValue;
        }
        nextValue += 1;
        listSize += 1;
        curPosition = (nextPos + 1) % listSize;
        return  lastAdded;
    }

    public static void main(String[] args) {
        int toRet = -1;
        for (long i = 0; i < 50000000; i++) {
            toRet = next(377);
        }
        System.out.println(lastAdded);
    }
}
