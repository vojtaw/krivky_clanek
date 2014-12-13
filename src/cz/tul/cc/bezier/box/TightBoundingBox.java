package cz.tul.cc.bezier.box;

import java.util.ArrayList;
import java.util.List;

import cz.tul.cc.bezier.Constants;
import cz.tul.cc.bezier.CubicCurveUtil;
import cz.tul.cc.point.Point;

public class TightBoundingBox implements BoundingBoxFunction {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TightBoundingBox.class.getName());
	private List<Point> bPoints;
	
	@Override
	public void setbPoints(List<Point> bPoints) {
		this.bPoints = new ArrayList<Point>(bPoints);

	}

	@Override
	public List<Point> boundingBox() {
		/** @source http://processingjs.nihongoresources.com/bezierinfo/sketchsource.php?sketch=boundsCubicBezier --> computeCubicBoundingBox */
		List<Point> list = new ArrayList<Point>();
		CubicCurveUtil cUtil = new CubicCurveUtil();
		
		double minx = Double.MAX_VALUE; //9999;
		double maxx = -Double.MAX_VALUE; //-9999;
		
		if(bPoints.get(0).getX()<minx) { minx=bPoints.get(0).getX(); }
		if(bPoints.get(0).getX()>maxx) { maxx=bPoints.get(0).getX(); }
		if(bPoints.get(3).getX()<minx) { minx=bPoints.get(3).getX(); }
		if(bPoints.get(3).getX()>maxx) { maxx=bPoints.get(3).getX(); }
		double[] ts = cUtil.computeCubicFirstDerivativeRoots(bPoints.get(0).getX(), bPoints.get(1).getX(), bPoints.get(2).getX(), bPoints.get(3).getX());
		for(int i=0; i<ts.length;i++) {
			double t = ts[i];
			if(t>=0 && t<=1) {
				double x = cUtil.computeCubicBaseValue(t, bPoints.get(0).getX(), bPoints.get(1).getX(), bPoints.get(2).getX(), bPoints.get(3).getX());
				double y = cUtil.computeCubicBaseValue(t, bPoints.get(0).getY(), bPoints.get(1).getY(), bPoints.get(2).getY(), bPoints.get(3).getY());
				if(x<minx) { minx=x; }
				if(x>maxx) { maxx=x; }
			}
		}

		double miny = Double.MAX_VALUE; //9999;
		double maxy = -Double.MAX_VALUE; //-9999;
		if(bPoints.get(0).getY()<miny) { miny=bPoints.get(0).getY(); }
		if(bPoints.get(0).getY()>maxy) { maxy=bPoints.get(0).getY(); }
		if(bPoints.get(3).getY()<miny) { miny=bPoints.get(3).getY(); }
		if(bPoints.get(3).getY()>maxy) { maxy=bPoints.get(3).getY(); }
		
		ts = cUtil.computeCubicFirstDerivativeRoots(bPoints.get(0).getY(), bPoints.get(1).getY(), bPoints.get(2).getY(), bPoints.get(3).getY());
//		System.out.println("ts.length:"+ts.length);
		for(int i=0; i<ts.length;i++) {
			double t = ts[i];
//			System.out.println("t:"+t);
			if(t>=0 && t<=1) {
				double x = cUtil.computeCubicBaseValue(t, bPoints.get(0).getX(), bPoints.get(1).getX(), bPoints.get(2).getX(), bPoints.get(3).getX());
				double y = cUtil.computeCubicBaseValue(t, bPoints.get(0).getY(), bPoints.get(1).getY(), bPoints.get(2).getY(), bPoints.get(3).getY());
				if(y<miny) { miny=y; }
				if(y>maxy) { maxy=y; }
			}
		}
		
		System.out.println("minx:"+minx+", maxx:"+maxx+", miny:"+miny+", maxy:"+maxy);
		list.add(new Point(minx, miny));/**bottom left point of Bounding box*/
		list.add(new Point(maxx, maxy));/**top right point of Bounding box*/

//		list.add(new Point(nbb1.getX(), nbb1.getY()));/**bottom left point of Bounding box*/
//		list.add(new Point(nbb2.getX(), nbb2.getY()));/**top right point of Bounding box*/
//		list.add(new Point(nbb3.getX(), nbb3.getY()));/**top right point of Bounding box*/
//		list.add(new Point(nbb4.getX(), nbb4.getY()));/**top right point of Bounding box*/
		
		return list;
		
		// bounding box corner coordinates
//		double[] bbox = {minx,miny, minx,maxy, maxx,maxy, maxx,miny};
//		return bbox;
		
	}
	
	
	/**tight bounding box n�jak nefunguje */
//	@Override
//	public List<Point> boundingBox() {
//		/** @source http://processingjs.nihongoresources.com/bezierinfo/ */
//		List<Point> list = new ArrayList<Point>();
//		CubicCurveUtil cUtil = new CubicCurveUtil();
//		
//		// translate to 0,0
//		Point np2 = new Point(bPoints.get(1).getX() - bPoints.get(0).getX(), bPoints.get(1).getY() - bPoints.get(0).getY());
//		Point np3 = new Point(bPoints.get(2).getX() - bPoints.get(0).getX(), bPoints.get(2).getY() - bPoints.get(0).getY());
//		Point np4 = new Point(bPoints.get(3).getX() - bPoints.get(0).getX(), bPoints.get(3).getY() - bPoints.get(0).getY());
//
//		// get angle for rotation
//		float angle = (float) cUtil.getDirection(np4.getX(), np4.getY());
//		
//		// rotate everything counter-angle so that it's aligned with the x-axis
//		Point rnp2 = new Point(np2.getX() * Math.cos(-angle) - np2.getY() * Math.sin(-angle), np2.getX() * Math.sin(-angle) + np2.getY() * Math.cos(-angle));
//		Point rnp3 = new Point(np3.getX() * Math.cos(-angle) - np3.getY() * Math.sin(-angle), np3.getX() * Math.sin(-angle) + np3.getY() * Math.cos(-angle));
//		Point rnp4 = new Point(np4.getX() * Math.cos(-angle) - np4.getY() * Math.sin(-angle), np4.getX() * Math.sin(-angle) + np4.getY() * Math.cos(-angle));
//
//		// find the zero point for x and y in the derivatives
//		double minx = Double.MAX_VALUE; //9999;
//		double maxx = -Double.MAX_VALUE; //-9999;
//		if(0<minx) { minx=0; }
//		if(0>maxx) { maxx=0; }
//		if(rnp4.getX()<minx) { minx=rnp4.getX(); }
//		if(rnp4.getX()>maxx) { maxx=rnp4.getX(); }
//		double[] ts = cUtil.computeCubicFirstDerivativeRoots(0, rnp2.getX(), rnp3.getX(), rnp4.getX());
////		stroke(255,0,0);
//		for(int i=0; i<ts.length;i++) {
//			double t = ts[i];
//			if(t>=0 && t<=1) {
//				int x = (int)cUtil.computeCubicBaseValue(t, 0, rnp2.getX(), rnp3.getX(), rnp4.getX());
//				if(x<minx) { minx=x; }
//				if(x>maxx) { maxx=x; }}}
//
//		float miny = Float.MAX_VALUE; //9999;
//		float maxy = -Float.MAX_VALUE; //-9999;
//		if(0<miny) { miny=0; }
//		if(0>maxy) { maxy=0; }
//		ts = cUtil.computeCubicFirstDerivativeRoots(0, rnp2.getY(), rnp3.getY(), 0);
////		stroke(255,0,255);
//		for(int i=0; i<ts.length;i++) {
//			double t = ts[i];
//			if(t>=0 && t<=1) {
//				int y = (int)cUtil.computeCubicBaseValue(t, 0, rnp2.getY(), rnp3.getY(), 0);
//				if(y<miny) { miny=y; }
//				if(y>maxy) { maxy=y; }}}
//
//		// bounding box corner coordinates
//		Point bb1 = new Point(minx,miny);
//		Point bb2 = new Point(minx,maxy);
//		Point bb3 = new Point(maxx,maxy);
//		Point bb4 = new Point(maxx,miny);
//		
//		// rotate back!
//		Point nbb1 = new Point(bb1.getX() * Math.cos(angle) - bb1.getY() * Math.sin(angle), bb1.getX() * Math.sin(angle) + bb1.getY() * Math.cos(angle));
//		Point nbb2 = new Point(bb2.getX() * Math.cos(angle) - bb2.getY() * Math.sin(angle), bb2.getX() * Math.sin(angle) + bb2.getY() * Math.cos(angle));
//		Point nbb3 = new Point(bb3.getX() * Math.cos(angle) - bb3.getY() * Math.sin(angle), bb3.getX() * Math.sin(angle) + bb3.getY() * Math.cos(angle));
//		Point nbb4 = new Point(bb4.getX() * Math.cos(angle) - bb4.getY() * Math.sin(angle), bb4.getX() * Math.sin(angle) + bb4.getY() * Math.cos(angle));
//		
//		// move back!
////		nbb1.x += bPoints.get(0).getX();	nbb1.y += bPoints.get(0).getY();
////		nbb2.x += bPoints.get(0).getX();	nbb2.y += bPoints.get(0).getY();
////		nbb3.x += bPoints.get(0).getX();	nbb3.y += bPoints.get(0).getY();
////		nbb4.x += bPoints.get(0).getX();	nbb4.y += bPoints.get(0).getY();
//		
//		nbb1.move(new double[]{bPoints.get(0).getX(), bPoints.get(0).getY()});
//		nbb2.move(new double[]{bPoints.get(0).getX(), bPoints.get(0).getY()});
//		nbb3.move(new double[]{bPoints.get(0).getX(), bPoints.get(0).getY()});
//		nbb4.move(new double[]{bPoints.get(0).getX(), bPoints.get(0).getY()});
//		
//		
////		double[] bbox = {nbb1.getX(), nbb1.getY(), nbb2.getX(), nbb2.getY(), nbb3.getX(), nbb3.getY(), nbb4.getX(), nbb4.getY()};
////		
////		return bbox;
//		
////		for(int i=0; i<Util.dimension; i++){
//		list.add(new Point(nbb1.getX(), nbb1.getY()));/**bottom left point of Bounding box*/
//		list.add(new Point(nbb2.getX(), nbb2.getY()));/**top right point of Bounding box*/
//		list.add(new Point(nbb3.getX(), nbb3.getY()));/**top right point of Bounding box*/
//		list.add(new Point(nbb4.getX(), nbb4.getY()));/**top right point of Bounding box*/
//		
//		return list;
//	}

}
