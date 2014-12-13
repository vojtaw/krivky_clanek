package cz.tul.cc.bezier.box;

import java.util.List;

import cz.tul.cc.point.Point;

public interface BoundingBoxFunction {
	
	/**sets B points to compute Bounding Box */
	public void setbPoints(List<Point> bPoints);
	
	public List<Point> boundingBox();

}
