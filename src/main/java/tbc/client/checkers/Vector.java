package tbc.client.checkers;

import java.io.Serializable;

public class Vector implements Serializable
{
        private int x;
        private int y;

        private Vector(){}

        public Vector(int _x, int _y)
        {
            setX(_x);
            setY(_y);
        }

        public int getX()
        {
            return x;
        }

        public void setX(int _x)
        {
            x = _x;
        }

        public int getY()
        {
            return y;
        }

        public void setY(int _y)
        {
            y = _y;
        }
        
        public static Vector add(Vector _base, Vector _add)
        {
        	return new Vector(_base.getX() + _add.getX(), _base.getY() + _add.getY());
        }
        
        public static Vector subtract(Vector _base, Vector _sub)
        {
        	return new Vector(_base.getX() - _sub.getX(), _base.getY() - _sub.getY());
        }
        
        public static Vector multiply(Vector _base, int _mult)
        {
        	return new Vector(_base.getX() * _mult, _base.getY() * _mult);
        }

        public boolean equals(Vector v) {
                return v.getX() == this.getX() && v.getY() == this.getY();
        }
}
