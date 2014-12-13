package cz.tul.cc.bezier.box;

import java.util.ArrayList;
import java.util.List;

import cz.tul.cc.bezier.Constants;
import cz.tul.cc.point.Point;
import cz.tul.cc.util.Util;

public class OrdinaryBox implements BoundingBoxFunction {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OrdinaryBox.class.getName());
	private List<Point> bPoints;
	
	@Override
	public void setbPoints(List<Point> bPoints) {
		this.bPoints = new ArrayList<Point>(bPoints);

	}

	@Override
	public List<Point> boundingBox() {
		List<Point> list = new ArrayList<Point>();
		
		double[] max = new double[Util.dimension];
		double[] min = new double[Util.dimension];
		for(int i=0; i<Util.dimension; i++){
			max[i] = Double.NEGATIVE_INFINITY;
			min[i] = Double.POSITIVE_INFINITY;
		}
		
		for(Point p : bPoints){
//			logger.info(p.toString());
			for(int i=0; i<Util.dimension; i++){
				max[i] = p.getCoordinates()[i]>=max[i]?p.getCoordinates()[i]:max[i];
				min[i] = p.getCoordinates()[i]<=min[i]?p.getCoordinates()[i]:min[i];
			}
		}
		
//		for(int i=0; i<Util.dimension; i++){
		list.add(new Point(min[0], min[1]));/**bottom left point of Bounding box*/
		list.add(new Point(max[0], max[1]));/**top right point of Bounding box*/
		
		return list;
	}

}
