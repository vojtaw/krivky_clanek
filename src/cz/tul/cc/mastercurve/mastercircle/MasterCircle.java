package cz.tul.cc.mastercurve.mastercircle;

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
public class MasterCircle implements MasterCurveInterface{
    private double lenght;
    private Point centre;
    private double diameter;
    private double alpha;
    private final Point aPoint, bPoint, cPoint;
    
    public MasterCircle(Point bPoint, Point cPoint){
        this.lenght = 0.0;
        this.centre = new Point(0.0, 0.0);
        this.aPoint = new Point(0.0, 0.0);
        this.bPoint = bPoint;
        this.cPoint = cPoint;
        _computeCentre();
        _computeAlpha();
        _computeLength();
    }

    /***********************************************************************
     * Delka oblouku
     * pi * d * alpha/pi = d*alpha; alpha[rad]
     * 
     * @return 
     */
    private void _computeLength(){
        lenght = diameter * alpha;
        
        return;
                
    }
    /***********************************************************************
     * Compute <strong>Center point</strong> of circle
     * @return 
     */
    private void _computeCentre(){
//        Point centre = new Point(0.0, 0.0);
        double k1=0, k2=0, k3=0;
                
        k1 =    cPoint.getX()*cPoint.getX() +
                cPoint.getY()*cPoint.getY() -
                cPoint.getY()*bPoint.getX()*bPoint.getX()/bPoint.getY() -
                cPoint.getY()*bPoint.getY();
        
        k2 =    k1 * bPoint.getY() / (2*cPoint.getX()*bPoint.getY() - 2*cPoint.getY()*bPoint.getX());
        k3 =    (bPoint.getX()*bPoint.getX() - 2*bPoint.getX()*k2 + bPoint.getY()*bPoint.getY()) / (2 * bPoint.getY());
                
        centre = new Point(k2, k3);
        diameter = 2 * Math.sqrt(centre.getX()*centre.getX() + centre.getY()*centre.getY());
        System.out.println("Centre: " + centre.toString() + ", diameter: " + diameter);
        return;
                
    }
    
    /** ********************************************************************
     * Computes alpha angle between lines centre --> [0.0, 0.0] and centre --> cPoint/2 (usualy [1.0, 0.0])
     * to be able to compute arc length
     * sin(alpha/2) =cPoint.x/2 / diameter/2
     * 
     * @retrun alpha [rad]
     */
    private void _computeAlpha(){
       alpha = 2 * Math.asin((cPoint.getX()/2)/(diameter/2));
    }
    @Override
    public double getLength(){
        return lenght;
    }
    
    /**********************************************************************
     * Discretization is calculated via equidistant x change
     * and calculated y coord
     * y2 + 2yy0 + (x2 + 2xx0 + x02 + y02 - r2) = 0, finding root > 0
     * 
     * @param numberOfParts
     * @return discretization Points including start and end Point
     */
    @Override
    public List<Point> getDiscretizationPoints(int numberOfParts){
        List<Point> points = new ArrayList<>();
        points.add(startPoint);
        double previous = 0.0;
        double next = 0.0;
        double y = 0.0;
        double step = 1.0/numberOfParts;
        for(int i=2; i<=numberOfParts; i++){
            next = previous+step;
            y = _calculatePositiveRoot(next);
            points.add(new Point(next, y));
            previous=next;
        }
        points.add(endPoint);
        return points;
    }

    private double _calculatePositiveRoot(double x){
        double a = 1;
        double b = 2*centre.getY();
        double c = x*x + 2*x*centre.getX() + centre.getX()*centre.getX() + centre.getY()*centre.getY() - diameter*diameter/4;
        double root1 = 0.0, root2 = 0.0;
        double discriminant = b*b - 4 * a* c;
        
        if(b==0){
            
        }
        root1 = (-1*b + Math.sqrt(discriminant))/(2 * a);
        root2 = (-1*b - Math.sqrt(discriminant))/(2 * a);
        
        return root1>=0 ? root1 : root2;
    }
    
    @Override
    public String toString() {
        return "MasterCircle{" + "lenght=" + lenght + ", centre=" + centre.toString() + ", diameter=" + diameter + ", alpha=" + (alpha * 180 / Math.PI) + '}';
    }
    
    
}
