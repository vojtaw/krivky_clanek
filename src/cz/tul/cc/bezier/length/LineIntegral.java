package cz.tul.cc.bezier.length;

import cz.tul.cc.point.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************************
 * Length count via line integral
 *
 * @author wojta
 */
public class LineIntegral implements LengthFunction {

    private double step;
    private List<Point> bPoints;

    public LineIntegral(double step) {
        this.step = step;
    }

    public LineIntegral(int numberOfSteps) {
        this.step = 1.0 / (double) numberOfSteps;
    }

    public LineIntegral(double step, List<Point> bPoints) {
        this.step = step;
        this.bPoints = new ArrayList<Point>(bPoints);
    }

    public LineIntegral(int numberOfSteps, List<Point> bPoints) {
        this.step = 1.0 / (double) numberOfSteps;
        this.bPoints = new ArrayList<Point>(bPoints);
    }

    /** ********************************************************************
     * Výpočet délky křivky určitým integrálem
     * Integrál od alpha(od 0) do beta (do 1) z Odmocniny derivace fi 2 (x) + derivace psi2 (y)
     * fi, psi je bernsteinův polynom a jeho derivace je:
     * fi je tedy rovno psi, jen se zadají jendou x a podruhý y
     * 
     * B0' = -3(t-1)2
     * B1' = 3(3t2-4t+1)
     * B2' = 3(2-3t)t
     * B3' = 3t2
     * 
     * @return 
     */
    @Override
    public double length() {
        double length = 0.0;
        
        
        return length;
    }

    double B0tP(double t, double P){
        return -3*t*t*P + 3*t*P -3;
    }
    double B1tP(double t, double P){
        return 9*t*t*P - 12*t*P + 3;
    }
    double B2tP(double t, double P){
        return 6*t*P - 9*t*t*P;
    }
    double B3tP(double t, double P){
        return 3*t*t*P;
    }
    
    
    @Override
    public void setbPoints(List<Point> bPoints) {
        this.bPoints = new ArrayList<Point>(bPoints);
    }

    
    
}
