package cz.tul.cc.bezier;

import cz.tul.cc.bezier.box.BoundingBoxFunction;
import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;
import java.util.List;

import cz.tul.cc.bezier.length.BernsteinPolynom;
import cz.tul.cc.bezier.length.LengthFunction;
import cz.tul.cc.line.Line;
import cz.tul.cc.line.Line2D;
import cz.tul.cc.point.Point;
import cz.tul.cc.util.Util;

public class CurveSegment extends AbstractCurve {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CurveSegment.class.getName());

    /**
     * ********************************************************************
     * Constructs CurveSegment from 4 control points
     *
     * @param bPoints List<Point> of 4 control points
     * @throws Exception when 4 control points are not
     */
    public CurveSegment(List<Point> bPoints) throws Exception {
        if (bPoints.size() != 4) {
            throw new Exception("4 controll points required");
        }
        B = new ArrayList<Point>();
        for (Point p : bPoints) {
            B.add(p);
        }

        lines = new ArrayList<Line>();
        lines = lineList();

        setFreeLength();
    }

    public CurveSegment(double[][] bData) throws Exception {
        if (bData.length != 4) {
            throw new Exception("4 controll points required");
        }
        B = new ArrayList<Point>();
        for (int i = 0; i < bData.length; i++) {
            Point p = new Point(bData[i]);
            B.add(p);
        }

        lines = new ArrayList<Line>();
        lines = lineList();
    }

    /**
     * @description calculates free length for this segment, because free length
     * is the same for whole computation - its the length of filament between
     * two gravures
     */
    @Override
    public final void setFreeLength() {
        if (this.freeLength == 0.0) {
            /**
             * free lenght can be setup only once, after filaments are
             * calcualted
             */
            this.freeLength = computeSegmentFreeLength(new BernsteinPolynom(Util.numberOfStepsForBernsteinLinear));
        }
    }

    @Override
    protected double computeSegmentFreeLength(LengthFunction function) {
        function.setbPoints(B);

        return function.length();
    }

    @Override
    public List<Point> segmentBoundingBox(BoundingBoxFunction function) {
        function.setbPoints(B);

        return function.boundingBox();
    }

    /**
     * ********************************************************************
     * Returns true if bounding box of this CurveSegment intersects with
     * bounding box <bl, tr>
     * * uses awt.geom CubicCurve2D
     *
     * @param bl - bottom left corner of rectangle
     * @param tr - top right corner of rectangle
     *
     * @return true if boundig boxes intersects
     */
    public boolean intersectsBoundingBox(Point bl, Point tr) {
        java.awt.geom.CubicCurve2D.Double cc = new CubicCurve2D.Double(B.get(0).getX(), B.get(0).getY(),
                B.get(1).getX(), B.get(1).getY(),
                B.get(2).getX(), B.get(2).getY(),
                B.get(3).getX(), B.get(3).getY());

        return cc.intersects(bl.getX(), bl.getY(), tr.getX() - bl.getX(), tr.getY() - bl.getY());
    }

//    public List<Point> intersects(CurveSegment cs, IntersectionFunction func) {
//        List<Point> intersectionPoints = new ArrayList<Point>();
//        intersectionPoints = func.intersection(cs);
//
//        return intersectionPoints;
//    }
//
//    public List<Point> intersects(Curve c, IntersectionFunction func) {
//        List<Point> intersectionPoints = new ArrayList<Point>();
//        intersectionPoints = func.intersection(this, c);
//
//        return intersectionPoints;
//    }
    public List<Line> lineList() {
        List<Line> lines = new ArrayList<Line>();
        double step = 1.0 / (double) Util.numberOfStepsForBernsteinLinear;
        Point p1 = this.B.get(0);
        Point p2;
        double[] coord = new double[Util.dimension];
        for (double t = step; t <= 1 + step; t += step) {
            coord = new double[Util.dimension];
            for (int i = 0; i < Util.dimension; i++) {
                coord[i] = (B.get(0).getCoordinates()[i] + t * (-B.get(0).getCoordinates()[i] * 3 + t * (3 * B.get(0).getCoordinates()[i] - B.get(0).getCoordinates()[i] * t)))
                        + t * (3 * B.get(1).getCoordinates()[i] + t * (-6 * B.get(1).getCoordinates()[i] + B.get(1).getCoordinates()[i] * 3 * t))
                        + t * t * (B.get(2).getCoordinates()[i] * 3 - B.get(2).getCoordinates()[i] * 3 * t)
                        + B.get(3).getCoordinates()[i] * t * t * t;
            }
            p2 = new Point(coord);
            lines.add(new Line2D(p1, p2));
//			logger.info("p1:"+p1+", p2:"+p2+", p1.distanceTo(p2):"+p1.distanceTo(p2));
            p1 = new Point(p2);
        }

        return lines;
    }

    /**
     * ********************************************************************
     * @description split a cubic curve into two curves at time=t stolen from
     * http://processingjs.nihongoresources.com/bezierinfo/sketchsource.php?sketch=CubicDeCasteljau
     * Segment[] splitCubicCurve(double t, double xa, double ya, double xb,
     * double yb, double xc, double yc, double xd, double yd) {
     * @param t time for splitting curve
     *
     * @return two CurveSegments from this splitted at t
     *
     * @throws Exception when creation of first or second segment fails
     */
    public CurveSegment[] splitSegment(double t) {
        CurveSegment[] curves = new CurveSegment[2];
        // interpolate from 4 to 3 points
        Point p5 = new Point((1 - t) * B.get(0).getX() + t * B.get(1).getX(), (1 - t) * B.get(0).getX() + t * B.get(1).getY()); //((1-t)*xa + t*xb, (1-t)*ya + t*yb);
        Point p6 = new Point((1 - t) * B.get(1).getX() + t * B.get(2).getX(), (1 - t) * B.get(1).getY() + t * B.get(2).getY()); //(1-t)*xb + t*xc, (1-t)*yb + t*yc
        Point p7 = new Point((1 - t) * B.get(2).getX() + t * B.get(3).getX(), (1 - t) * B.get(2).getY() + t * B.get(3).getY()); //(1-t)*xc + t*xd, (1-t)*yc + t*yd
        // interpolate from 3 to 2 points
        Point p8 = new Point((1 - t) * p5.getX() + t * p6.getX(), (1 - t) * p5.getY() + t * p6.getY());
        Point p9 = new Point((1 - t) * p6.getX() + t * p7.getX(), (1 - t) * p6.getY() + t * p7.getY());
        // interpolate from 2 points to 1 point
        Point p10 = new Point((1 - t) * p8.getX() + t * p9.getX(), (1 - t) * p8.getY() + t * p9.getY());
        // we now have all the values we need to build the subcurves
//		curves[0] = new CurveSegment(B.get(0).getX(),B.get(0).getY(), p5.getX(), p5.getY(), p8.getX(), p8.getY(), p10.getX(), p10.getY());
//		curves[1] = new CurveSegment(p10.getX(), p10.getY(), p9.getX(), p9.getY(), p7.getX(), p7.getY(), B.get(3).getX(), B.get(3).getY());
        try {
            curves[0] = new CurveSegment(new double[][]{{B.get(0).getX(), B.get(0).getY()},
            {p5.getX(), p5.getY()},
            {p8.getX(), p8.getY()},
            {p10.getX(), p10.getY()}
            });
        } catch (Exception e) {
            logger.error("Error creating first segment: " + e.getLocalizedMessage());
        }
        try {
            curves[1] = new CurveSegment(new double[][]{{p10.getX(), p10.getY()},
            {p9.getX(), p9.getY()},
            {p7.getX(), p7.getY()},
            {B.get(3).getX(), B.get(3).getY()}
            });
        } catch (Exception e) {
            logger.error("Error creating second segment: " + e.getLocalizedMessage());
        }
        // return full intermediate value set, in case they are needed for something else
//		Point[] points = {p5, p6, p7, p8, p9, p10};
//		curves[2] = new PointSegment(points);

        return curves;
    }

    /**
     * ********************************************************************
     * @description returns tangent of curve segment at specific time t general
     * bezier cubic x(t)=(1-t)^3x1+3(1-t)^2tx2+3(1-t)t^2x3+t^3x4
     * y(t)=(1-t)^3y1+3(1-t)^2ty2+3(1-t)t^2y3+t^3y4
     *
     * first derivative /dt x(t)' = -3(t-1)^2x1 + 3(3t^2-4t+1)x2 + 3(2-3t)tx3 +
     * 3t^2x4 y(t)' = -3(t-1)^2y1 + 3(3t^2-4t+1)y2 + 3(2-3t)ty3 + 3t^2y4
     *
     * @param t - bezier time t
     *
     * @return tangent Line
     *
     *
     */
    @Override
    public Line2D tangent(double t) {
        /**
         * http://tutorial.math.lamar.edu/Classes/CalcII/ParaTangent.aspx
         */
//		System.out.println("B:"+B.get(0).toString()+B.get(1).toString()+B.get(2).toString()+B.get(3).toString());
        double dx, dy, xt, yt;
        dx = -3 * (t - 1) * (t - 1) * B.get(0).getX()
                + 3 * (3 * t * t - 4 * t + 1) * B.get(1).getX()
                + 3 * (2 - 3 * t) * t * B.get(2).getX()
                + 3 * t * t * B.get(3).getX();
        dy = -3 * (t - 1) * (t - 1) * B.get(0).getY()
                + 3 * (3 * t * t - 4 * t + 1) * B.get(1).getY()
                + 3 * (2 - 3 * t) * t * B.get(2).getY()
                + 3 * t * t * B.get(3).getY();
        xt = (B.get(0).getX() + t * (-B.get(0).getX() * 3 + t * (3 * B.get(0).getX() - B.get(0).getX() * t)))
                + t * (3 * B.get(1).getX() + t * (-6 * B.get(1).getX() + B.get(1).getX() * 3 * t))
                + t * t * (B.get(2).getX() * 3 - B.get(2).getX() * 3 * t)
                + B.get(3).getX() * t * t * t;
        yt = (B.get(0).getY() + t * (-B.get(0).getY() * 3 + t * (3 * B.get(0).getY() - B.get(0).getY() * t)))
                + t * (3 * B.get(1).getY() + t * (-6 * B.get(1).getY() + B.get(1).getY() * 3 * t))
                + t * t * (B.get(2).getY() * 3 - B.get(2).getY() * 3 * t)
                + B.get(3).getY() * t * t * t;

        /**
         * rovnice tangenty je y = yt + x*(dy/dx);
         *
         * horizontal tangent: dy/dt = 0, dx/dt != 0
         *
         * vertical tangent: dx/dt = 0, dy/dt != 0
         */
        System.out.println("tangent at: (" + xt + "," + yt + "), dy: " + dy + ", dx: " + dx + ", dy/dx: " + dy / dx);

        if (dy == 0 && dx != 0) { //horizontal tangent
            return new Line2D(xt, yt, xt + 1.0, yt);
        } else if (dx == 0 && dy != 0) {//vertical tangent
            return new Line2D(xt, yt, xt, yt + 1.0);
        } else {
            return new Line2D(new Point(xt, yt), 1.0, yt + dy / dx);
        }

//		return null;
    }

    /**
     * ************************************************************
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        int i = 0;
        for (Point p : B) {
            str.append("(").append(p.getX()).append(", ").append(p.getY()).append(")");
            if (i < B.size() - 1) {
                str.append(",");
            }
            i++;
        }

        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurveSegment)) {
            return false;
        }
//	   		double precision = 1E-32;
        CurveSegment cs = (CurveSegment) o;

//	    	if(this.B.size() != cs.g)
        for (int i = 0; i < this.B.size(); i++) {
            if (!this.getBPoint(i).equals(cs.getBPoint(i))) {
                return false;
            }
        }
        return true;
    }

    public Point getBPoint(int position) {
        if (position > 4) {
            return null;
        }
        return B.get(position);
    }

}
