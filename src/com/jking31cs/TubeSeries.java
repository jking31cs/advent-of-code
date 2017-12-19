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

    public static String traverse(List<String> graph) {
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
        while (inbounds(graph, curRow, curCol)) {
            if (isMovingVert) {
                int newRow = curRow + rowDir;
                if (!inbounds(graph, newRow, curCol)) break;
                curRow += rowDir;
                char c = graph.get(curRow).charAt(curCol);
                if (alphas.contains(c)) {
                    toRet = toRet.concat(Character.toString(c));
                }
                if (c == '+') {
                    isMovingVert = !isMovingVert;
                }
            }
        }
        return toRet;
    }

    private static boolean inbounds(List<String> graph, int curRow, int curCol) {
        if (curRow >= graph.size() || curRow < 0) return false;
        if (curCol >= graph.get(curRow).length() || curCol < 0) return false;
        return true;
    }
}
