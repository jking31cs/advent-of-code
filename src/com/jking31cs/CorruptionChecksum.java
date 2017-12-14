package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CorruptionChecksum {

    public static List<List<Integer>> convertFile(File f) throws FileNotFoundException {
        List<List<Integer>> output = new ArrayList<>();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                List<String> split = Arrays.asList(s.split("\t"));
                List<Integer> row =
                        split.stream().map(Integer::parseInt).collect(Collectors.toList());
                output.add(row);
            }
        }
        return output;
    }

    public static int doMethod1(List<List<Integer>> input) {
        int sum = 0;
        for (List<Integer> row : input) {
            int max = 0;
            int min = Integer.MAX_VALUE;
            for (int i : row) {
                if (i > max) {
                    max = i;
                }
                if (i < min) {
                    min = i;
                }
            }
            sum += (max - min);
        }

        return sum;
    }

    public static int doMethod2(List<List<Integer>> input) {
        int sum = 0;
        for (List<Integer> row : input) {
            for (int i = 0; i < row.size(); i++) {
                for (int j = i + 1; j < row.size(); j++) {
                    int val1 = row.get(i);
                    int val2 = row.get(j);
                    if (val1 % val2 == 0) {
                        sum += (val1/val2);
                        break;
                    } else if (val2 % val1 == 0) {
                        sum += (val2/val1);
                        break;
                    }
                }
            }
        }
        return sum;
    }

}
