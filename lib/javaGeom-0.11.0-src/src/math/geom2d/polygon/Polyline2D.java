/* file : Polyline2D.java
 * 
 * Project : geometry
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
 * 
 * Created on 8 mai 2006
 *
 */

package math.geom2d.polygon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import math.geom2d.AffineTransform2D;
import math.geom2d.GeometricObject2D;
import math.geom2d.Point2D;
import math.geom2d.Shape2D;
import math.geom2d.circulinear.CirculinearContinuousCurve2D;
import math.geom2d.circulinear.PolyCirculinearCurve2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.StraightLine2D;
import math.geom2d.transform.CircleInversion2D;

/**
 * A polyline is a continuous curve where each piece of the curve is a
 * LineSegment2D.
 * 
 * @author dlegland
 */
public class Polyline2D extends LinearCurve2D
implements CirculinearContinuousCurve2D, Cloneable {

    // ===================================================================
    // Static methods
    
    /**
     * Static factory for creating a new Polyline2D from a collection of
     * points.
     * @since 0.8.1
     */
    public static Polyline2D create(Collection<? extends Point2D> points) {
    	return new Polyline2D(points);
    }
    
    /**
     * Static factory for creating a new Polyline2D from an array of
     * points.
     * @since 0.8.1
     */
    public static Polyline2D create(Point2D... points) {
    	return new Polyline2D(points);
    }

    
    // ===================================================================
    // Contructors

    public Polyline2D() {
    	super();
    }

    /**
     * Creates a new polyline by allocating enough memory for the specified
     * number of vertices.
     * @param nVertices
     */
    public Polyline2D(int nVertices) {
    	super(nVertices);
    }

    public Polyline2D(Point2D initialPoint) {
        this.vertices.add(initialPoint);
    }

    public Polyline2D(Point2D... vertices) {
        super(vertices);
    }

    public Polyline2D(Collection<? extends Point2D> vertices) {
        super(vertices);
    }

    public Polyline2D(double[] xcoords, double[] ycoords) {
    	super(xcoords, ycoords);
    }
    

    // ===================================================================
    // methods implementing the CirculinearCurve2D interface

	/* (non-Javadoc)
	 * @see math.geom2d.circulinear.CirculinearCurve2D#transform(math.geom2d.transform.CircleInversion2D)
	 */
	public CirculinearContinuousCurve2D transform(CircleInversion2D inv) {
		
		// Create array for storing transformed arcs
		Collection<LineSegment2D> edges = this.edges();
		ArrayList<CirculinearContinuousCurve2D> arcs = 
			new ArrayList<CirculinearContinuousCurve2D>(edges.size());
		
		// Transform each arc
		for(LineSegment2D edge : edges) {
			arcs.add(edge.transform(inv));
		}
		
		// create the transformed shape
		return new PolyCirculinearCurve2D<CirculinearContinuousCurve2D>(arcs);
	}

    // ===================================================================
    // Methods inherited from ContinuousCurve2D


    /*
     * (non-Javadoc)
     * 
     * @see math.geom2d.OrientedCurve2D#getWindingAngle(Point2D)
     */
    public double windingAngle(Point2D point) {
        double angle = 0;
        int n = vertices.size();
        for (int i = 0; i<n-1; i++)
            angle += new LineSegment2D(vertices.get(i), vertices.get(i+1))
                    .windingAngle(point);

        return angle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see math.geom2d.OrientedCurve2D#isInside(Point2D)
     */
    public boolean isInside(Point2D pt) {
        if (new LinearRing2D(this.vertexArray()).isInside(pt))
            return true;

        if (this.vertices.size()<3)
            return false;

        Point2D p0 = this.firstPoint();
        Point2D q0 = this.vertices.get(1);
        if (new StraightLine2D(q0, p0).isInside(pt))
            return false;

        Point2D p1 = this.lastPoint();
        Point2D q1 = this.vertices.get(vertices.size()-2);
        if (new StraightLine2D(p1, q1).isInside(pt))
            return false;

        if (new StraightLine2D(p0, p1).isInside(pt))
            return true;

        return false;
    }

    // ===================================================================
    // Methods inherited from ContinuousCurve2D

    /**
     * Returns false, as Polyline2D is not closed by definition.
     */
    public boolean isClosed() {
        return false;
    }

    // ===================================================================
    // Methods inherited from Curve2D interface

    /*
     * (non-Javadoc)
     * 
     * @see math.geom2d.Curve2D#getPoint(double, math.geom2d.Point2D)
     */
    public math.geom2d.Point2D point(double t) {
        // format position to stay between limits
        double t0 = this.getT0();
        double t1 = this.getT1();
        t = Math.max(Math.min(t, t1), t0);

        // index of vertex before point
        int ind0 = (int) Math.floor(t+Shape2D.ACCURACY);
        double tl = t-ind0;
        Point2D p0 = vertices.get(ind0);

		// check if equal to a vertex
		if (Math.abs(t - ind0) < Shape2D.ACCURACY)
			return new Point2D(p0);

        // index of vertex after point
        int ind1 = ind0+1;
        Point2D p1 = vertices.get(ind1);

        // position on line;

        double x0 = p0.getX();
        double y0 = p0.getY();
        double dx = p1.getX()-x0;
        double dy = p1.getY()-y0;

        return new Point2D(x0+tl*dx, y0+tl*dy);
    }

    /**
     * Returns the number of points in the polyline, minus one.
     */
    public double getT1() {
        return vertices.size() - 1;
    }

	/**
     * Returns the last point of this polyline, or null if the polyline does 
     * not contain any point.
     */
	@Override
    public Point2D lastPoint() {
        if (vertices.size() == 0)
            return null;
        return vertices.get(vertices.size()-1);
    }

    /**
     * Returns the polyline with same points considered in reverse order.
     * Reversed polyline keep same references as original polyline.
     */
    public Polyline2D reverse() {
        Point2D[] points2 = new Point2D[vertices.size()];
        int n = vertices.size();
        for (int i = 0; i < n; i++)
        	points2[i] = vertices.get(n-1-i);
        return new Polyline2D(points2);
    }

	@Override
    public Collection<? extends Polyline2D> continuousCurves() {
    	return wrapCurve(this);
    }


    /**
     * Return an instance of Polyline2D. If t1 is lower than t0, return an
     * instance of Polyline2D with zero points.
     */
    public Polyline2D subCurve(double t0, double t1) {
        // code adapted from CurveSet2D

        Polyline2D res = new Polyline2D();

        if (t1<t0)
            return res;

        // number of points in the polyline
        int indMax = (int) this.getT1();

        // format to ensure t is between T0 and T1
        t0 = Math.min(Math.max(t0, 0), indMax);
        t1 = Math.min(Math.max(t1, 0), indMax);

        // find curves index
        int ind0 = (int) Math.floor(t0);
        int ind1 = (int) Math.floor(t1);

        // need to subdivide only one line segment
        if (ind0 == ind1) {
            // extract limit points
            res.addVertex(this.point(t0));
            res.addVertex(this.point(t1));
            // return result
            return res;
        }

        // add the point corresponding to t0
        res.addVertex(this.point(t0));

        // add all the whole points between the 2 cuts
        for (int n = ind0 + 1; n <= ind1; n++)
            res.addVertex(vertices.get(n));

        // add the last point
        res.addVertex(this.point(t1));

        // return the polyline
        return res;
    }

    // ===================================================================
    // Methods implementing the Shape2D interface

    /*
     * (non-Javadoc)
     * 
     * @see math.geom2d.Shape2D#transform(math.geom2d.AffineTransform2D)
     */
    public Polyline2D transform(AffineTransform2D trans) {
        Point2D[] pts = new Point2D[vertices.size()];
        for (int i = 0; i<vertices.size(); i++)
            pts[i] = trans.transform(vertices.get(i));
        return new Polyline2D(pts);
    }

    /*
     * (non-Javadoc)
     * 
     * @see math.geom2d.ContinuousCurve2D#appendPath(java.awt.geom.GeneralPath)
     */
    public java.awt.geom.GeneralPath appendPath(java.awt.geom.GeneralPath path) {

        if (vertices.size()<2)
            return path;

        // get point iterator
        Iterator<Point2D> iter = vertices.iterator();

        // avoid first point
        Point2D point = iter.next();
       
        // line to each other point
        while (iter.hasNext()) {
            point = iter.next();
            path.lineTo((float) (point.getX()), (float) (point.getY()));
        }

        return path;
    }

    /**
     * Return a general path iterator.
     */
    public java.awt.geom.GeneralPath asGeneralPath() {
        java.awt.geom.GeneralPath path = new java.awt.geom.GeneralPath();
        if (vertices.size()<2)
            return path;

        // get point iterator
        Iterator<Point2D> iter = vertices.iterator();

        // move to first point
        Point2D point = iter.next();
        path.moveTo((float) (point.getX()), (float) (point.getY()));

        // line to each other point
        while (iter.hasNext()) {
            point = iter.next();
            path.lineTo((float) (point.getX()), (float) (point.getY()));
        }

        return path;
    }


	// ===================================================================
	// methods implementing the GeometricObject2D interface

	/* (non-Javadoc)
	 * @see math.geom2d.GeometricObject2D#almostEquals(math.geom2d.GeometricObject2D, double)
	 */
    public boolean almostEquals(GeometricObject2D obj, double eps) {
    	if (this == obj)
    		return true;
    	
        if (!(obj instanceof Polyline2D))
            return false;
        Polyline2D polyline = (Polyline2D) obj;

        if (vertices.size() != polyline.vertices.size())
            return false;
        
        for (int i = 0; i < vertices.size(); i++)
            if (!(vertices.get(i)).almostEquals(polyline.vertices.get(i), eps))
                return false;
        return true;
    }

    // ===================================================================
    // Methods inherited from the Object Class

    @Override
    public boolean equals(Object object) {
    	if (this==object)
    		return true;
        if (!(object instanceof Polyline2D))
            return false;
        Polyline2D polyline = (Polyline2D) object;

        if (vertices.size()!=polyline.vertices.size())
            return false;
        for (int i = 0; i<vertices.size(); i++)
            if (!(vertices.get(i)).equals(polyline.vertices.get(i)))
                return false;
        return true;
    }
    
    @Override
    public Polyline2D clone() {
        ArrayList<Point2D> array = new ArrayList<Point2D>(vertices.size());
        for(Point2D point : vertices)
            array.add(point.clone());
        return new Polyline2D(array);
    }

}
