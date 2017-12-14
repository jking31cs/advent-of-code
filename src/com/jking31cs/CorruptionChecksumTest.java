package com.jking31cs;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CorruptionChecksumTest {

    @Test
    public void test1() {
        int result = CorruptionChecksum.doMethod1(Arrays.asList(
                Arrays.asList(5,1,9,5),
                Arrays.asList(7,5,3),
                Arrays.asList(2,4,6,8)
                )
        );

        assertEquals(18, result);
    }

    @Test
    public void test2() {
        int result = CorruptionChecksum.doMethod2(Arrays.asList(
                Arrays.asList(5,9,2,8),
                Arrays.asList(9,4,7,3),
                Arrays.asList(3,8,6,5)
                )
        );

        assertEquals(9, result);
    }
}