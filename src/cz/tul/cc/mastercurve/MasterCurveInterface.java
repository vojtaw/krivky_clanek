package cz.tul.cc.mastercurve;

import cz.tul.cc.point.Point;
import java.util.List;

/** ************************************************************************
 * Basic interface for Master Curve Genetaror
 *
 * @author wojta
 */
public interface MasterCurveInterface {
    /** ********************************************************************
     * Curve starts at[0.0; 0.0] and ends at [1.0; 0.0]
     */
    final Point startPoint = new Point(0.0, 0.0);
    final Point endPoint = new Point(1.0, 0.0);
    
    public double getLength();
    public double getCurvatureRatio();
    public double getAlpha();
    public List<Point> getDiscretizationPoints(int numberOfParts);
    
    public Point getStartPoint();
    public Point getMidPoint();
    public Point getEndPoint();
    public Point getCentrePoint();
    
}
