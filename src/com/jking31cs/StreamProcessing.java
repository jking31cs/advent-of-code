package com.jking31cs;

import java.io.*;
import java.util.Scanner;

public class StreamProcessing {

    public static int score(File f) throws IOException {
        int score = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            char prev = '/';
            int r;
            boolean skipNext = false;
            boolean isGarbage = false;
            while ((r = br.read()) != -1) {
                if (skipNext) {
                    skipNext = false;
                    continue;
                }
                char cur = (char) r;
                if (!isGarbage) {
                    if (cur == '<') isGarbage = true;
                } else {
                    if (cur == '!') skipNext = true;
                    else if (cur == '>') isGarbage = false;
                    else  {
                        score+=1;
                    }

                }

            }
        }

        return score;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(score(new File("day9Input.txt")));
    }
}
