package cz.tul.cc.bezier.length;

import java.util.List;

import cz.tul.cc.point.Point;


public interface LengthFunction {

	/** Computes length of bezier curve*/
	public double length();
	
	public void setbPoints(List<Point> bPoints);

}
