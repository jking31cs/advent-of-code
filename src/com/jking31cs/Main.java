package com.jking31cs;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
	    System.out.println(CorruptionChecksum.doMethod2(CorruptionChecksum.convertFile(new File("day2Input.txt"))));
    }
}
