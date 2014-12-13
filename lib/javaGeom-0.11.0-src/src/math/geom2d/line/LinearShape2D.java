/**
 * 
 */

package math.geom2d.line;

import math.geom2d.AffineTransform2D;
import math.geom2d.Point2D;
import math.geom2d.Vector2D;
import math.geom2d.circulinear.CirculinearCurve2D;

/**
 * A curve that can be inscribed in a straight line, line a ray, a straight
 * line, or a line segment. Classes implementing this interface can be
 * discontinuous, contrary to the interface LinearElement2D.
 * 
 * @author dlegland
 */
public interface LinearShape2D extends CirculinearCurve2D {

    public abstract StraightLine2D supportingLine();

    /**
     * Gets Angle with axis (O,i), counted counter-clockwise. Result is given
     * between 0 and 2*pi.
     */
    public abstract double horizontalAngle();

    /**
     * Returns a point in the linear shape.
     * 
     * @return a point in the linear shape.
     */
    public abstract Point2D origin();

    /**
     * Return one direction vector of the linear shape.
     * 
     * @return a direction vector
     */
    public abstract Vector2D direction();

    /**
     * Returns the unique intersection with a linear shape. If the intersection
     * doesn't exist (parallel lines), returns null.
     */
    public abstract Point2D intersection(LinearShape2D line);

    public LinearShape2D transform(AffineTransform2D trans);
}
