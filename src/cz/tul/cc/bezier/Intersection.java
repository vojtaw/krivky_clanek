package cz.tul.cc.bezier;

import java.util.ArrayList;
import java.util.List;

import cz.tul.cc.point.Point;

public class Intersection {
	@SuppressWarnings("unused")
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Intersection.class.getName());
	
	private List<Point> intersectionPoints;
	private CurveSegment segment;
	
	public Intersection(List<Point> intersectionPoints, CurveSegment segment){
		this.intersectionPoints = new ArrayList<Point>();
		this.intersectionPoints.addAll(intersectionPoints);
		
		this.segment = segment;
	}

	/***********************************************************/
    /** General getters and setters ****************************/
    /***********************************************************/
	public List<Point> getIntersectionPoints() {
		return intersectionPoints;
	}

	public Point getIntersectionPoint(int position) {
		if(position > intersectionPoints.size()){
			return null;
		}
		return intersectionPoints.get(position);
	}
	
	public CurveSegment getSegment() {
		return segment;
	}

	
}
