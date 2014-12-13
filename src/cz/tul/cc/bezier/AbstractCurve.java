package cz.tul.cc.bezier;

import cz.tul.cc.bezier.box.BoundingBoxFunction;
import java.util.ArrayList;
import java.util.List;

import cz.tul.cc.bezier.length.LengthFunction;
import cz.tul.cc.line.Line;
import cz.tul.cc.point.Point;

/**base class for all curves involved in model */
public abstract class AbstractCurve {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AbstractCurve.class.getName());
	
	/**@description freeLength of curve segment
         * nemá smysl jej počítat při vytvoření segmentu, protože segmenty jsou při aplikaci gravury rozdělovány,
         * je lepší je zpočítat až před výpočtem
         * proto to není final a setFreeLength() je public ...         */
	protected double freeLength = 0.0;
	
	/**List of Bezier control points */
	protected List<Point> B;
	/**List of S points - curve goes through these points
	 * in general, usually is B[0]==S[0] and B[n]==S[n]*/
	protected List<Point> S;
	
	/**Line2D segments for intersection
	 * jeste se musi rozhodnout jestli u kazdeho segmentu ukladat, nebo podle potreby pocitat*/
	protected List<Line> lines;
	
	/**Inverse matrixes (1 4 1) for computing B points,
	 * its usefull to store them as constants instead of computing them each time*/
//	M2
//	M3
//	M4
//	M5
	
	/** Computes Bezier control points B from S points*/
//	public abstract void computeBPoints();
	
	/** Sets the free length of segmente*/
	public abstract void setFreeLength();
	
	/** Computes length of bezier curve*/
	protected abstract double computeSegmentFreeLength(LengthFunction function);
	
//	/** Computes and returnes bounding box of this curve*/
	public abstract List<Point> segmentBoundingBox(BoundingBoxFunction function);
	
	/** Returns true if BoundingBox of this courve segment intersect rectangle*/
	public abstract boolean intersectsBoundingBox(Point bl, Point tr);
	
	/** Returns true if this courve segment intersect curveSegment*/
//	public abstract List<Point> intersects(CurveSegment cs, IntersectionFunction func);
	
	/** Returns List of lines for intersections*/
	public abstract List<Line> lineList();
	
	/**@description returns tangent of curve segment at specific time t*/
	public abstract Line tangent(double t);
	
	 /***********************************************************/
    /** General getters and setters ****************************/
    /***********************************************************/
	
	public List<Line> getLines(){
		return lines;
	}
	
	public Point getStartPoint() {
		return B.get(0);
	}
	public Point getEndPoint() {
		return B.get(B.size()-1);
	}

	public double getFreeLength() {
		return freeLength;
	}
}
