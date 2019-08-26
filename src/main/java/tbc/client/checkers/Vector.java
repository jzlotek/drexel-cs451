package tbc.client.checkers;

import java.io.Serializable;

public class Vector implements Serializable
{
    private int x;
    private int y;

    /*
     * Default, empty constructor
     */
    private Vector(){}

    /*
     * Constructor for a Vector with a given x and y coordinate
     */
    public Vector(int _x, int _y)
    {
        setX(_x);
        setY(_y);
    }

    /*
     * Get the x coordinate
     */
    public int getX()
    {
        return x;
    }

    /*
     * Set the x coordinate
     */
    public void setX(int _x)
    {
        x = _x;
    }

    /*
     * Get the y coordinate
     */
    public int getY()
    {
        return y;
    }

    /*
     * Set the y coordinate
     */
    public void setY(int _y)
    {
        y = _y;
    }

    /*
     * Add two Vectors together (x1 + x2, y1 + y2)
     */
    public static Vector add(Vector _base, Vector _add)
    {
        return new Vector(_base.getX() + _add.getX(), _base.getY() + _add.getY());
    }

    /*
     * Subtract one Vector from another (x1 - x2, y1 - y2)
     */
    public static Vector subtract(Vector _base, Vector _sub)
    {
        return new Vector(_base.getX() - _sub.getX(), _base.getY() - _sub.getY());
    }

    /*
     * Multiply a Vector times a scalar (x * scal, y * scal)
     */
    public static Vector multiply(Vector _base, int _mult)
    {
        return new Vector(_base.getX() * _mult, _base.getY() * _mult);
    }

    @Override
    public boolean equals(Object _other) {
        boolean returnValue = false;
        if(_other instanceof Vector) {
            Vector v = (Vector)_other;
            returnValue = v.getX() == this.getX() && v.getY() == this.getY();
        }

        return returnValue;
    }

    @Override
    public int hashCode()
    {
        return x * 100 + y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
