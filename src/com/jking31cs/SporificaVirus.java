package com.jking31cs;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SporificaVirus {

    static List<String> parseFile(File f) throws FileNotFoundException {
        List<String> toRet = new ArrayList<>();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) toRet.add(scanner.nextLine());
        }
        return toRet;
    }

    static int infected(List<String> grid) {
        int size = grid.size();
        int curRow = 0, curCol =0, count=0;
        curRow = curCol = (grid.size()/2);
        Map<Pair<Integer,Integer>, Boolean> initiallyInfected = new HashMap<>(grid.size()*grid.size());
        int r = 0,c=0;
        for (String s : grid) {
            c=0;
            for (char ch : s.toCharArray()) {
                if (ch == '.') {
                    initiallyInfected.put(new Pair<>(r,c), false);
                } else {
                    initiallyInfected.put(new Pair<>(r,c), true);
                }
                c +=1;
            }
            r+=1;
        }

        //Directions are simple, 0 is up, 1 is right, 2 is down, 3 is left.
        //do mods to detect, +1 to turn right, -1 to turn left.
        int direction = 0;

        for (int i = 0; i < 10000000; i++) {
            initiallyInfected.putIfAbsent(new Pair<>(curRow, curCol), false);
            String row = grid.get(curRow);
            char[] arr = row.toCharArray();
            char inspect = arr[curCol];
            if (inspect == '#') {
                arr[curCol] = 'F';
                direction += 1;
            } else if (inspect == '.') {
                arr[curCol] = 'W';
                direction -= 1;
            } else if (inspect == 'W') {
                arr[curCol] = '#';
                count+=1;
            } else {
                arr[curCol] = '.';
                direction += 2; //turns around
            }
            grid.set(curRow, new String(arr));
            switch (Math.floorMod(direction, 4)) {
                case 0:
                    curRow -= 1;
                    break;
                case 1:
                    curCol += 1;
                    break;
                case 2:
                    curRow += 1;
                    break;
                case 3:
                    curCol -=1;
                    break;
            }
            if (curRow < 0 || curCol < 0 || curRow >= size || curCol >= size) {
                String newString = "";
                for (int j =0; j < size; j++) {
                    newString = newString.concat(".");
                }
                grid.add(0, newString);
                grid.add(newString);
                for (int j = 0; j < grid.size(); j++) {
                    String s = grid.get(j);
                    grid.set(j, ".".concat(s).concat("."));
                }
                curCol +=1;
                curRow +=1;
                size = grid.size();

            }
        }

        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(infected(parseFile(new File("day22Input.txt"))));
    }
}
