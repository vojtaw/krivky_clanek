package cz.tul.cc.bezier.length;

import java.util.ArrayList;
import java.util.List;

import cz.tul.cc.bezier.Constants;
import cz.tul.cc.point.Point;
import cz.tul.cc.util.Util;

public class BernsteinPolynom implements LengthFunction {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BernsteinPolynom.class.getName());
	private double step;
	private List<Point> bPoints;// = new ArrayList<Point>();
	
	public BernsteinPolynom(double step) {
		this.step = step;
	}
	public BernsteinPolynom(int numberOfSteps) {
		this.step = 1.0/(double)numberOfSteps;
	}

	public BernsteinPolynom(double step, List<Point> bPoints) {
		this.step = step;
		this.bPoints = new ArrayList<Point>(bPoints);
	}
	public BernsteinPolynom(int numberOfSteps, List<Point> bPoints) {
		this.step = 1.0/(double)numberOfSteps;
		this.bPoints = new ArrayList<Point>(bPoints);
	}


	@Override
	/**Compute Bezier Cubic curve length based on Bernstein polynom
	 *  */
	public double length() {
		double length = 0.0;
//		logger.info("length - 0:"+bPoints.get(0)+", 1:"+bPoints.get(1)+", 2:"+bPoints.get(2)+", 3:"+bPoints.get(3));
		Point p1 = this.bPoints.get(0);
		Point p2;
		double[] coord = new double[Util.dimension];
		for(double t=step; t<=1+step; t+=step){
			coord = new double[Util.dimension];
			for(int i=0; i<Util.dimension; i++){
				coord[i] = (bPoints.get(0).getCoordinates()[i]+t*(-bPoints.get(0).getCoordinates()[i]*3+t*(3*bPoints.get(0).getCoordinates()[i]-bPoints.get(0).getCoordinates()[i]*t)))+
				t*(3*bPoints.get(1).getCoordinates()[i]+t*(-6*bPoints.get(1).getCoordinates()[i]+bPoints.get(1).getCoordinates()[i]*3*t))+
				t*t*(bPoints.get(2).getCoordinates()[i]*3-bPoints.get(2).getCoordinates()[i]*3*t)+
				bPoints.get(3).getCoordinates()[i]*t*t*t;
			}
			p2 = new Point(coord);
//			logger.info("p1:"+p1+", p2:"+p2+", p1.distanceTo(p2):"+p1.distanceTo(p2));
			length += p1.distanceTo(p2);
			p1 = new Point(p2);
		}		
//		logger.info("length:"+length);
		return length;
	}
	public void setbPoints(List<Point> bPoints) {
//		logger.info("setbPoints - 0:"+bPoints.get(0)+", 1:"+bPoints.get(1)+", 2:"+bPoints.get(2)+", 3:"+bPoints.get(3));
		this.bPoints = new ArrayList<Point>(bPoints);
	}

}
