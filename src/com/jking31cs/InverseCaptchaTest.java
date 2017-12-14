package com.jking31cs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InverseCaptchaTest {

    @Test
    public void test1() {
        int result = InverseCaptcha.inverseCaptcha("1122");
        assertEquals(3, result);
    }

    @Test
    public void test2() {
        int result = InverseCaptcha.inverseCaptcha("1111");
        assertEquals(4, result);
    }

    @Test
    public void test3() {
        int result = InverseCaptcha.inverseCaptcha("1234");
        assertEquals(0, result);
    }

    @Test
    public void test4() {
        int result = InverseCaptcha.inverseCaptcha("91212129");
        assertEquals(9, result);
    }

    @Test
    public void test5() {
        int result = InverseCaptcha.inverseCaptcha2("1212");
        assertEquals(6, result);
    }

    @Test
    public void test6() {
        int result = InverseCaptcha.inverseCaptcha2("1221");
        assertEquals(0, result);
    }

    @Test
    public void test7() {
        int result = InverseCaptcha.inverseCaptcha2("123425");
        assertEquals(4, result);
    }

    @Test
    public void test8() {
        int result = InverseCaptcha.inverseCaptcha2("123123");
        assertEquals(12, result);
    }

    @Test
    public void test9() {
        int result = InverseCaptcha.inverseCaptcha2("12131415");
        assertEquals(4, result);
    }

}