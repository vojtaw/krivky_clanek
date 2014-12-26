package cz.tul.cc.mastercurve;

import cz.tul.cc.point.Point;
import java.util.ArrayList;
import java.util.List;

/** ************************************************************************
 * Class generating master curve
 *
 * @author wojta
 */
public class MasterCurve implements MasterCurveInterface{
    private double length;
    private double koefA;
    
    public MasterCurve(double koefA){
        this.length = 0.0;
        this.koefA = koefA;
        _computeLength();
    }

    /***********************************************************************
     * Delka se spocita jako urcity integral z odmocniny 1 + f'(x)2
     * @return 
     */
    private void _computeLength(){
        
        return;
                
    }
    @Override
    public double getLength(){
        return length;
    }

    @Override
    public double getCurvatureRatio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**********************************************************************
     * Discretization is calculated via equidistant x change
     * and calculated y coord
     * y = -ax2+ax = a(-x2+x)
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
            y = koefA*next - koefA*next*next;
            points.add(new Point(next, y));
            previous=next;
        }
        points.add(endPoint);
        return points;
    }
    
    @Override
    public Point getStartPoint() {
        return startPoint;
    }

    @Override
    public Point getMidPoint() {
        return null;
    }

    @Override
    public Point getEndPoint() {
        return endPoint;
    }
    @Override
    public double getAlpha() {
        return 0;
    }

    @Override
    public Point getCentrePoint() {
        return null;
    }
    
}
