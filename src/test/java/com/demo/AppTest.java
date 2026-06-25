package com.demo;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testAdd() {
        App app = new App();
        assertEquals(5, app.add(2, 3));
    }

    @Test
    public void testAddNegative() {
        App app = new App();
        assertEquals(-1, app.add(2, -3));
    }
}
