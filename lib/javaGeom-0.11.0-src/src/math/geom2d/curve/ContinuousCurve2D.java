/* File ContinuousCurve2D.java 
 *
 * Project : Java Geometry Library
 *
 * ===========================================
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY, without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. if not, write to :
 * The Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

// package

package math.geom2d.curve;

// Imports
import java.util.*;

import math.geom2d.AffineTransform2D;
import math.geom2d.Box2D;
import math.geom2d.Vector2D;
import math.geom2d.polygon.LinearCurve2D;

/**
 * Interface for all curves which can be drawn with one stroke. This includes
 * closed curves (ellipses, polygon boundaries...), infinite curves (straight
 * lines, parabolas, ...), and 'finite' curves, such as polylines, conic arcs,
 * line segments, splines... Note that an hyperbola is compound of 2 continuous
 * curves.
 * <p>
 * Such curves accept parametric representation, in the form :
 * <code>p(t)={x(t),y(t)}</code>, with <code>t</code> contained in
 * appropriate domain. Bounds of domain of definition can be obtained by methods
 * <code>getT0()</code> and <code>getT1()</code>.
 * <p>
 */

public interface ContinuousCurve2D extends Curve2D {

    // ===================================================================
    // constants

    // ===================================================================
    // general methods

    /**
     * Returns true if the curve makes a loop, that is come back to starting
     * point after covering the path.
     */
    public abstract boolean isClosed();

    public Vector2D leftTangent(double t);
    public Vector2D rightTangent(double t);
    public abstract double curvature(double t);

   
    /**
     * Returns a set of smooth curves.
     */
    public abstract Collection<? extends SmoothCurve2D> smoothPieces();

    /**
     * Returns an approximation of the curve as a polyline with <code>n</code>
     * line segments. If the curve is closed, the method should return an
     * instance of LinearRing2D. Otherwise, it returns an instance of
     * Polyline2D.
     * 
     * @param n the number of line segments
     * @return a polyline with <code>n</code> line segments.
     */
    public abstract LinearCurve2D asPolyline(int n);

    /**
     * Append the path of the curve to the given path.
     * 
     * @param path a path to modify
     * @return the modified path
     */
    public abstract java.awt.geom.GeneralPath appendPath(
            java.awt.geom.GeneralPath path);

    // ===================================================================
    // Curvr2D methods

    public abstract ContinuousCurve2D reverse();

    public abstract ContinuousCurve2D subCurve(double t0, double t1);

    // ===================================================================
    // Shape2D methods

    public abstract CurveSet2D<? extends ContinuousCurve2D> clip(Box2D box);

    public abstract ContinuousCurve2D transform(AffineTransform2D trans);
}