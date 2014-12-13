package cz.tul.cc.point;

import cz.tul.cc.util.Util;

/**
 * ************************************************************************
 * Simple Point representation class
 *
 * @author wojta
 */
public class Point {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Point.class.getName());

    protected double[] coordinates;

    /**
     * constructor from (x,y) coordinates
     */
    public Point(double x, double y) {
        coordinates = new double[Util.dimension];
        coordinates[0] = x;
        coordinates[1] = y;
    }

    /**
     * constructor from (x,y,z) coordinates
     */
    public Point(double x, double y, double z) {
        coordinates = new double[Util.dimension];
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = z;
    }

    public Point(double[] coordinates) {
        this.coordinates = new double[Util.dimension];
        System.arraycopy(coordinates, 0, this.coordinates, 0, Util.dimension);
    }

    /**
     * copy constructor :-)
     */
    public Point(Point point) {
        this.coordinates = new double[Util.dimension];
        System.arraycopy(point.getCoordinates(), 0, this.coordinates, 0, Util.dimension);
    }

    /**
     * @description Euclidean distance between this point and point
     */
    public double distanceTo(Point point) {
        /**
         * @return distance from this point to point dist = sqrt (x*x + y*y +
         * z*z)
         */
//		double distance = 0.0;

//		distance = Math.sqrt((point.getX() - this.getX()) * (point.getX() - this.getX()) + 
//							 (point.getY() - this.getY()) * (point.getY() - this.getY()));
//		return distance;
        double square = 0.0;
        double[] vec = new double[Util.dimension];
        for (int i = 0; i < Util.dimension; i++) {
            vec[i] = point.getCoordinates()[i] - this.getCoordinates()[i];
        }
        for (int i = 0; i < Util.dimension; i++) {
            square += vec[i] * vec[i];
        }
        return Math.sqrt(square);
    }

    /**
     * @description shifts this.point by by, posune bod o sou�adnice by
     */
    public void shift(double[] by) {
        /**
         * @param shifting coordinates
         */
        for (int i = 0; i < Util.dimension; i++) {
            this.coordinates[i] += by[i];
        }
    }

    /**
     * @description shifts this.point to new coordinates
     */
    public void shift(double[] posun, double delka) {
        /**
         * @return void - point is shifted to new coordinates,
         * @param posun - o kolik se posunuje cel� vzorek
         * @param delka - jak� je delka vzorku
         */

//		double[] coord = new double[Util.dimension];
        for (int i = 0; i < Util.dimension; i++) {
            coordinates[i] += coordinates[i] * (posun[i] / delka);
        }

    }

    public boolean isNull() {
        if (coordinates == null) {
            return true;
        }
        return false;
    }

    /**
     * ********************************************************
     */
    /**
     * Overridden Object methods *****************************
     */
    /**
     * ********************************************************
     */
    /**
     * convert to string
     */
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
//   		double precision = 1E-32;
        Point p = (Point) o;
    	//	return abs(x-other.x)<=0.1 && abs(y-other.y)<=0.1; }

        for (int i = 0; i < Util.dimension; i++) {
            if (Math.abs(this.coordinates[i] - p.getCoordinates()[i]) >= Util.doublePrecision) {
                return false;
            }
        }
        return true;
    }

    /**
     * ********************************************************
     */
    /**
     * General getters and setters ***************************
     */
    /**
     * ********************************************************
     */
    public double getX() {
        return coordinates[0];
    }

    public double getY() {
        return coordinates[1];
    }

    public double[] getCoordinates() {
        return coordinates;
    }

}
