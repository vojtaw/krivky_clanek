package cz.tul.cc.intepolation;

import cz.tul.cc.filament.Filament;
import cz.tul.cc.point.Point;
import java.util.List;

/** ************************************************************************
 *
 * @author wojta
 */
public class BezierInterpolation implements InterpolationInterface {
    private final List<Point> points;
    private final Filament filament;

    public BezierInterpolation(List<Point> points) {
        this.points = points;
        filament = new Filament(points);
    }
    
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    @Override
    public double getLength() {
        System.out.println("BezierInterpolation getLength");

        return filament.actualFreeLength();
    }

    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
}
