package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TubeSeries {
    static final char[] alphasArr = "abcdefghijklmnoppqrstuvwxyz".toUpperCase().toCharArray();
    static final Set<Character> alphas = new HashSet<Character>() {{
        for (char c : alphasArr) this.add(c);
    }};

    public static List<String> parseFile(File file) throws FileNotFoundException {
        List<String> toRet = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) toRet.add(scanner.nextLine());
        }
        return toRet;
    }

    public static int traverse(List<String> graph) {
        int curRow = 0;
        int curCol = 0;
        int rowDir = 1;
        int colDir = 1;
        boolean isMovingVert = true;
        for (int i = 0; i < graph.get(0).length(); i++) {
            if (graph.get(0).charAt(i) == '|') {
                curCol = i;
                break;
            }
        }
        String toRet = "";
        int count = 0;
        while (inbounds(graph, curRow, curCol)) {
            if (isMovingVert) {
                int newRow = curRow + rowDir;
                if (!inbounds(graph, newRow, curCol)) {
                    break;
                }
                curRow += rowDir;
                char c = graph.get(curRow).charAt(curCol);
                if (alphas.contains(c)) {
                    toRet = toRet.concat(Character.toString(c));
                }

                count+=1;
                if (c == ' ') break;
                if (c == '+') {
                    isMovingVert = !isMovingVert;
                    //Calculate direction.
                    char neighborRight = ' ';
                    if (curCol+1 < graph.get(curRow).length()) {
                        neighborRight = graph.get(curRow).charAt(curCol + 1);
                    }
                    if (neighborRight == '-' || alphas.contains(neighborRight)) {
                        colDir = 1;
                    } else {
                        colDir = -1;
                    }
                }
            } else {
                int newCol = curCol + colDir;
                if (!inbounds(graph, curRow, newCol)) {
                    break;
                }
                curCol += colDir;
                char c = graph.get(curRow).charAt(curCol);
                if (alphas.contains(c)) {
                    toRet = toRet.concat(Character.toString(c));
                }

                count+=1;
                if (c == ' ') break;
                if (c == '+') {
                    isMovingVert = !isMovingVert;
                    //Calculate direction.
                    char neighborBelow = ' ';
                    if (curRow+1 < graph.size()) {
                        neighborBelow =
                                graph.get(curRow + 1).charAt(curCol);
                    }
                    if (neighborBelow == '|' || alphas.contains(neighborBelow)) {
                        rowDir = 1;
                    } else {
                        rowDir = -1;
                    }
                }
            }
        }
        return count;
    }

    private static boolean inbounds(List<String> graph, int curRow, int curCol) {
        if (curRow >= graph.size() || curRow < 0) return false;
        if (curCol >= graph.get(curRow).length() || curCol < 0) return false;
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(traverse(parseFile(new File("day19input.txt"))));
    }
}
