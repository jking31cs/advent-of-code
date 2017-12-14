package com.jking31cs;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiralMemmory {

    public static int steps(int startPoint) {

        if (startPoint == 1) {
            return 0;
        }
        int sqrt = 1;
        int bottomRight = 1;
        while (bottomRight < startPoint) {
            sqrt += 2;
            bottomRight = sqrt * sqrt;
        }
        int maxDist = ((sqrt) / 2) * 2;
        int maxYDist = (sqrt) / 2;

        int toRet = -1;
        int startLookPoint = bottomRight;
        int curDistance = maxDist;
        int denoter = -1;
        while (toRet < 0) {
            startLookPoint -= 1;
            curDistance = curDistance + denoter;
            if (curDistance == maxYDist || curDistance == maxDist) {
                denoter *= -1;
            }
            if (startLookPoint == startPoint) {
                toRet = curDistance;
            }
        }

        return curDistance;
    }

    public static int part2(int input) {
        Map<Pair<Integer, Integer>, Integer> matrix = new HashMap<>();
        matrix.put(new Pair<>(0,0), 1);
        int curMax = 1;
        int lastValue = 1;
        int xModifier = 1;
        int yModifier = 1;
        int curX = 0;
        int curY = 0;
        boolean isX = true;
        while (lastValue < input) {
            if (isX) {
                curX += xModifier;
                if (Math.abs(curX) == curMax) {
                    isX = !isX;
                    xModifier = xModifier * -1;
                }
            } else {
                curY += yModifier;
                if (Math.abs(curY) == curMax) {
                    isX = !isX;
                    yModifier = yModifier * -1;
                }
            }
            int value = 0;
            value += getValue(matrix, curX, curY+1);
            value += getValue(matrix, curX, curY-1);
            value += getValue(matrix, curX + 1, curY-1);
            value += getValue(matrix, curX + 1, curY);
            value += getValue(matrix, curX + 1, curY+1);
            value += getValue(matrix, curX -1, curY-1);
            value += getValue(matrix, curX -1 , curY);
            value += getValue(matrix, curX -1, curY+1);
            matrix.put(new Pair<>(curX, curY), value);
            lastValue = value;

            if ((-1*curY) == curMax && curX == curMax) {
                curMax += 1;
                isX = true;
                xModifier = 1;
                yModifier = 1;
            }
        }

        return lastValue;
    }

    private static Integer getValue(Map<Pair<Integer, Integer>, Integer> matrix, int x, int y) {
        Integer value = matrix.get(new Pair<>(x, y));
        if (value == null) return 0;
        return value;
    }

    public static void main(String[] args) {
        System.out.println(part2(289326));
    }
}
