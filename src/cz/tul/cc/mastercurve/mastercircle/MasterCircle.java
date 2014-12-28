package cz.tul.cc.mastercurve.mastercircle;

import cz.tul.cc.db.curve.CurveBean;
import cz.tul.cc.mastercurve.*;
import cz.tul.cc.point.Point;
import java.util.ArrayList;
import java.util.List;

/** ************************************************************************
 * Class generating master circle
 * for circle terminology visit http://en.wikipedia.org/wiki/Circle#Terminology
 *
 * @author wojta
 */
public class MasterCircle implements MasterCurveInterface {

    private int curveId;
    private double lenght;
    private Point centre;
    private double diameter;
    /**uhel ramen rovnorameneho tojuhelnika s vrcholem ve stredu kruznice a  ramena do krajnich bodu */
    private double alpha;
    private final Point aPoint, bPoint, cPoint;
    private double curvatureRatio;
    
    public MasterCircle(Point bPoint, Point cPoint) {
        this.curveId = 0;
        this.lenght = 0.0;
        this.centre = new Point(0.0, 0.0);
        this.aPoint = startPoint;
        this.bPoint = bPoint;
        this.cPoint = cPoint;
        _computeCurvatureRatio();
        _computeCentre();
        _computeAlpha();
        _computeLength();
    }

    public MasterCircle(Point bPoint) {
        this.curveId = 0;
        this.lenght = 0.0;
        this.centre = new Point(0.0, 0.0);
        this.aPoint = startPoint;
        this.bPoint = bPoint;
        this.cPoint = endPoint;
        _computeCurvatureRatio();
        _computeCentre();
        _computeAlpha();
        _computeLength();
        System.out.println(toString());
    }
    
    public MasterCircle(CurveBean cb) {
        this.curveId = cb.getId();
        this.lenght = cb.getArcLength();
        this.centre = new Point(0.0, 0.0);
        this.aPoint = new Point(cb.getStartX(), cb.getStartY());
        this.bPoint = new Point(cb.getMidX(), cb.getMidY());
        this.cPoint = new Point(cb.getEndX(), cb.getEndY());
        this.centre = new Point(cb.getCentreX(), cb.getCentreY());
        this.alpha = cb.getAlpha();
        this.curvatureRatio = cb.getCurvatureRatio();
        _computeDiameter();
    }

    /** *********************************************************************
     * Delka oblouku
     * pi * d * alpha/pi = d*alpha; alpha[rad]
     *
     * @return
     */
    private final void _computeLength() {
        lenght = diameter / 2 * alpha;

        return;

    }

    private final void _computeCurvatureRatio(){
        curvatureRatio = bPoint.getY() / (cPoint.getX() - aPoint.getX());
    }
    
    /** *********************************************************************
     * Compute <strong>Center point</strong> of circle
     *
     * @return
     */
    private void _computeCentre() {
//        Point centre = new Point(0.0, 0.0);
        double k1 = 0, k2 = 0, k3 = 0;

        k1 = cPoint.getX() * cPoint.getX()
                + cPoint.getY() * cPoint.getY()
                - cPoint.getY() * bPoint.getX() * bPoint.getX() / bPoint.getY()
                - cPoint.getY() * bPoint.getY();

        k2 = k1 * bPoint.getY() / (2 * cPoint.getX() * bPoint.getY() - 2 * cPoint.getY() * bPoint.getX());
        k3 = (bPoint.getX() * bPoint.getX() - 2 * bPoint.getX() * k2 + bPoint.getY() * bPoint.getY()) / (2 * bPoint.getY());

        centre = new Point(k2, k3);
        _computeDiameter();
//        System.out.println("Centre: " + centre.toString() + ", diameter: " + diameter);
        return;

    }
    
    /** *********************************************************************
     * Compute <strong>diameter</strong> of circle
     * circle goes through pont[0,0], so diameter is 2*sqrt(cx2+cy2) 
     *
     * @return
     */
    private void _computeDiameter() {
        diameter = 2 * Math.sqrt(centre.getX() * centre.getX() + centre.getY() * centre.getY());
        return;

    }

    /** ********************************************************************
     * Computes alpha angle between lines centre --> [0.0, 0.0] and centre --> cPoint/2 (usualy [1.0, 0.0])
     * to be able to compute arc length
     * sin(alpha/2) =cPoint.x/2 / diameter/2
     *
     * @retrun alpha [rad]
     */
    private void _computeAlpha() {
        alpha = 2 * Math.asin((cPoint.getX() / 2) / (diameter / 2));
    }

    @Override
    public double getLength() {
        return lenght;
    }

    @Override
    public double getCurvatureRatio() {
        return curvatureRatio;
    }
//    /**********************************************************************
//     * Discretization is calculated via equidistant x change
//     * and calculated y coord
//     * y2 + 2yy0 + (x2 + 2xx0 + x02 + y02 - r2) = 0, finding root > 0
//     * 
//     * @param numberOfParts
//     * @return discretization Points including start and end Point
//     */
//    @Override
//    public List<Point> getDiscretizationPoints(int numberOfParts){
//        List<Point> points = new ArrayList<>();
//        points.add(startPoint);
//        double previous = 0.0;
//        double next = 0.0;
//        double y = 0.0;
//        double step = 1.0/numberOfParts;
//        for(int i=2; i<=numberOfParts; i++){
//            next = previous+step;
//            y = _calculatePositiveRoot(next);
//            points.add(new Point(next, y));
//            previous=next;
//        }
//        points.add(endPoint);
//        return points;
//    }
    /** ********************************************************************
     * Discretization is calculated via equidistant alpha change
     *
     * @param numberOfParts
     * @return discretization Points including start and end Point
     */
    @Override
    public List<Point> getDiscretizationPoints(int numberOfParts) {
        List<Point> points = new ArrayList<>();
        points.add(startPoint);
        double previousAlpha = 0.0;
        double nextAlpha = 0.0;
        Point point;
        double alphaStep = alpha / numberOfParts;
        for (int i = 2; i <= numberOfParts; i++) {
            nextAlpha = previousAlpha + alphaStep;
            point = _calculatePointCoord(nextAlpha);
            points.add(point);
            previousAlpha = nextAlpha;
        }
        points.add(endPoint);
        return points;
    }

    private Point _calculatePointCoord(double alpha) {
        double delkaOd0DoBodu = diameter * Math.sin(alpha/2); //2 * diameter/2 * Math.sin(alpha/2)
//        System.out.println("alpha:"+alpha*180/Math.PI+", delkaOd0DoBodu:"+delkaOd0DoBodu);
        double k1 =  delkaOd0DoBodu*delkaOd0DoBodu - diameter*diameter/4 + centre.getX()*centre.getX() + centre.getY()*centre.getY();
        double a=0, b=0, c=0;
        double discriminant = 0;
        double x = 0;
        
        if(centre.getY()==0){
//            System.out.println("centre.getY()==0");
            x = k1 / (2*centre.getX());
            double y = Math.sqrt(delkaOd0DoBodu*delkaOd0DoBodu - x*x);
//            System.out.println("x:"+x+", y:"+y);
            return new Point(x, y);
        }
        a = 1 + (centre.getX()*centre.getX()/ (centre.getY()*centre.getY()));
        b = -1 * k1 * centre.getX() / (centre.getY()*centre.getY());
        c = k1*k1 / (4*centre.getY()*centre.getY()) - delkaOd0DoBodu*delkaOd0DoBodu;
        discriminant = b*b - 4*a*c;
//        System.out.println("discriminant:"+discriminant);
        if(discriminant<0){
            return null;
        }else if(discriminant==0){
            x = (-1 * b) / (2 * a);
        }else{
            double rootX1 = (-1 * b + Math.sqrt(discriminant)) / (2 * a);
            double rootX2 = (-1 * b - Math.sqrt(discriminant)) / (2 * a);

            x = rootX1 >= 0 ? rootX1 : rootX2;
        }
        
        double y = (k1 - 2 * x * centre.getX()) / (2 * centre.getY());
        
//        System.out.println("x:"+x+", y:"+y);
        return new Point(x, y);
    }
    
    private double _calculatePositiveRoot(double x) {
        double a = 1;
        double b = 2 * centre.getY();
        double c = x * x + 2 * x * centre.getX() + centre.getX() * centre.getX() + centre.getY() * centre.getY() - diameter * diameter / 4;
        double root1 = 0.0, root2 = 0.0;
        double discriminant = b * b - 4 * a * c;

        if (b == 0) {

        }
        root1 = (-1 * b + Math.sqrt(discriminant)) / (2 * a);
        root2 = (-1 * b - Math.sqrt(discriminant)) / (2 * a);

        return root1 >= 0 ? root1 : root2;
    }

    @Override
    public int getCurveId() {
        return curveId;
    }
    
    @Override
    public Point getStartPoint() {
        return startPoint;
    }

    @Override
    public Point getMidPoint() {
        return bPoint;
    }

    @Override
    public Point getEndPoint() {
        return endPoint;
    }

    @Override
    public double getAlpha() {
        return alpha;
    }

    @Override
    public Point getCentrePoint() {
        return centre;
    }

    @Override
    public String toString() {
        return "MasterCircle{" + "lenght=" + lenght + ", centre=" + centre.toString() + ", diameter=" + diameter + ", alpha=" + (alpha * 180 / Math.PI) + '}';
    }

}
