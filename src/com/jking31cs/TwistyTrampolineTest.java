package com.jking31cs;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TwistyTrampolineTest {

    @Test
    public void test1() throws Exception {
        assertEquals(5, TwistyTrampoline.escape(new int[] {0,3,0,1,-3}));
    }

    @Test
    public void test2() throws Exception {
        assertEquals(10, TwistyTrampoline.escape2(new int[] {0,3,0,1,-3}));
    }
}