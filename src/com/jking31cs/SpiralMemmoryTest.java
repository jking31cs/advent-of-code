package com.jking31cs;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpiralMemmoryTest {

    @Test
    public void test1() {
        assertEquals(0, SpiralMemmory.steps(1));
    }

    @Test
    public void test2() {
        assertEquals(3, SpiralMemmory.steps(12));
    }

    @Test
    public void test3() {
        assertEquals(2, SpiralMemmory.steps(23));
    }

    @Test
    public void test4() {
        assertEquals(31, SpiralMemmory.steps(1024));
    }

}