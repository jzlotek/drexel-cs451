package tbc.client.checkers;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class VectorTest {

    @Test
    public void test_vectorAddition() {
        Vector v1 = new Vector(0, 0);
        Vector add = new Vector(1, 2);
        Vector v2 = Vector.add(v1, add);

        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), 2);

        v1 = new Vector(0, 0);
        add = new Vector(-1, -2);
        v2 = Vector.add(v1, add);

        assertEquals(v2.getX(), -1);
        assertEquals(v2.getY(), -2);
    }

    @Test
    public void test_vectorSubtract() {
        Vector v1 = new Vector(0, 0);
        Vector sub = new Vector(1, 2);
        Vector v2 = Vector.subtract(v1, sub);

        assertEquals(v2.getX(), -1);
        assertEquals(v2.getY(), -2);

        v1 = new Vector(0, 0);
        sub = new Vector(-1, -2);
        v2 = Vector.subtract(v1, sub);

        assertEquals(v2.getX(), 1);
        assertEquals(v2.getY(), 2);
    }

    @Test
    public void test_vectorMultiplication() {
        Vector v1 = new Vector(1, 2);
        int mult = 5;
        Vector v2 = Vector.multiply(v1, mult);

        assertEquals(v2.getX(), 5);
        assertEquals(v2.getY(), 10);

        v1 = new Vector(1, 2);
        mult = -5;
        v2 = Vector.multiply(v1, mult);

        assertEquals(v2.getX(), -5);
        assertEquals(v2.getY(), -10);
    }


    @Test
    public void test_Equals() {
        Vector v1 = new Vector(1,2);
        Vector v2 = new Vector(1,2);
        Vector v3 = new Vector(0,0);

        assertTrue(v1.equals(v2));
        assertFalse(v1.equals(v3));
    }

}