package cz.tul.cc.intepolation;

import cz.tul.cc.point.Point;
import java.util.List;

/** ************************************************************************
 *
 * @author wojta
 */
public class LinearInterpolation implements InterpolationInterface {
    private final List<Point> points;

    public LinearInterpolation(List<Point> points) {
        this.points = points;
    }
    
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    @Override
    public double getLength() {
        System.out.println("LinearInterpolation getLength");
//        for (Point point : points) {
//            System.out.println(point);
//        }
        
        double length = 0.0;
        Point previous = points.get(0);
        Point next;
        double x=0, y=0;
        for (int i = 1; i < points.size(); i++) {
            next = points.get(i);
            x = next.getX() - previous.getX();
            y = next.getY() - previous.getY();
            length += Math.sqrt(x*x +y*y);
            previous = next;
        }
        return length;
    }

    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
}
