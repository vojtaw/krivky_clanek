package cz.tul.cc.bezier;

import cz.tul.cc.bezier.box.OrdinaryBox;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

//import cz.tul.cc.bezier.intersection.BruteLineIntersection;
import cz.tul.cc.bezier.length.BernsteinPolynom;
import cz.tul.cc.point.Point;
import cz.tul.cc.util.Util;

public abstract class Curve {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Curve.class.getName());

    /** cela B-sline je sestavena ze segmnetu Bezierovy cubiky */
    protected List<CurveSegment> curve = new ArrayList<CurveSegment>();

    /** only 2D bounding box rectangle of this.curve */
    protected Rectangle2D.Double boxRectangle;

    protected Curve() {

    }

    public double freeLength() {
        double len = 0.0;
        for (CurveSegment cs : curve) {
            double sl = cs.getFreeLength();
//            logger.info("Segment length:" + sl);
            len += sl;//cs.segmentFreeLength(new BernsteinPolynom(Constants.NUMBER_OF_STEPS));
        };

        return len;
    }

    public double actualFreeLength() {
        double len = 0.0;
        for (CurveSegment cs : curve) {
            double sl = cs.computeSegmentFreeLength(new BernsteinPolynom(Util.numberOfStepsForBernsteinLinear));
//            logger.info("Segment length:" + sl);
            len += sl;//cs.segmentFreeLength(new BernsteinPolynom(Constants.NUMBER_OF_STEPS));
        }

        return len;
    }

    protected Rectangle2D.Double boundingBoxRectangle() {
        List<Point> list = new ArrayList<Point>();
        list = boundingBox();

        return new Rectangle2D.Double(list.get(0).getX(),
                list.get(0).getY(),
                list.get(1).getX() - list.get(0).getX(),
                list.get(1).getY() - list.get(0).getY());
    }

    private List<Point> boundingBox() {
        List<Point> list = new ArrayList<Point>();

        double[] max = new double[Util.dimension];
        double[] min = new double[Util.dimension];
        for (int i = 0; i < Util.dimension; i++) {
            max[i] = Double.NEGATIVE_INFINITY;
            min[i] = Double.POSITIVE_INFINITY;
        }

        for (CurveSegment cs : curve) {
            for (Point p : cs.segmentBoundingBox(new OrdinaryBox())) {
//				logger.info(p.toString());
                for (int i = 0; i < Util.dimension; i++) {
                    max[i] = p.getCoordinates()[i] >= max[i] ? p.getCoordinates()[i] : max[i];
                    min[i] = p.getCoordinates()[i] <= min[i] ? p.getCoordinates()[i] : min[i];
                }
            }
        }

        switch (Util.dimension) {
            case 2: {
                list.add(new Point(min[0], min[1]));
                /** bottom left point of Bounding box */
                list.add(new Point(max[0], max[1]));
                /** top right point of Bounding box */
            }

            break;
            case 3: {
                list.add(new Point(min[0], min[1], min[2]));
                /** bottom left point of Bounding box */
                list.add(new Point(max[0], max[1], max[2]));
                /** top right point of Bounding box */
            }

            break;

            default:
                break;
        }

        return list;
    }

//    public List<Intersection> intersects(Curve curve) {
//        /** @return List of intersection Point between this.curve and curve if
//         *         List is empty then exists any intersection poit */
//        List<Intersection> intersections = new ArrayList<Intersection>();
////		Intersection intersection = null;
////		Point intersectionPoint;
//
//        if (!intersectsBoundingBox(curve.getBoxRectangle())) {
//            /** neprot�naj� se bounding boxy --> nemuze se protinat ani krivka */
//            return intersections;
//        }
//        /** to ze se prot�naj� bounding boxy, jeste neznamena, ze se protinaji
//         * krivky, proto se pro kazdy segment kontroluje, jestli se s curve neprotina */
//        for (CurveSegment cs : this.curve) {
//            System.out.println("cs:" + cs.intersects(curve, new BruteLineIntersection()).size());
//            List<Point> points = cs.intersects(curve, new BruteLineIntersection());
//            if (points != null && points.size() != 0) {
//                intersections.add(new Intersection(cs.intersects(curve, new BruteLineIntersection()), cs));
//            }
//        }
//        return intersections;
//    }

    public boolean intersectsBoundingBox(Rectangle2D box) {
        return this.boxRectangle.intersects(box);
    }

    /** ******************************************************** */
    /** Overridden Object methods ***************************** */
    /** ******************************************************** */
    public String toString() {
        StringBuffer str = new StringBuffer("");
        int i = 0;
        for (CurveSegment cs : curve) {
            str.append("Segment " + i);
            str.append("{" + cs.toString() + "}");
            str.append("\n");
            i++;
        }

        return str.toString();
    }

    public String dString(double[][] data) {
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < data.length; i++) {
            buf.append("(" + data[i][0] + "," + data[i][1] + ")");
        }
        return buf.toString();
    }

    /** ******************************************************** */
    /** General getters and setters *************************** */
    /** ******************************************************** */
    public CurveSegment getCurveSegment(int position) {
        System.out.println("position: " + position + ", curve.size(): " + curve.size());
        if (position > curve.size()) {
            return null;
        }
        return curve.get(position);
    }

    public Rectangle2D.Double getBoxRectangle() {
        return boxRectangle;
    }

    public List<CurveSegment> getCurve() {
        return curve;
    }

    public Point getStartPoint() {
        return curve.get(0).getStartPoint();
    }

    public Point getEndPoint() {
        return curve.get(curve.size() - 1).getEndPoint();
    }

}
