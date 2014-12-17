package cz.tul.cc.discretization;

import cz.tul.cc.point.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** ************************************************************************ 
 *
 * @author wojta
 */
public class DiscretizationTest {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DiscretizationTest.class.getName());
    private int numberOfSteps;
    /** ********************************************************************
     * Testing line starting at[0.0; 0.0] and ending at [1.0; 1.0]
     * so lenght of line is exactly 1
     */
    private final Point startPoint = new Point(0.0, 0.0);
    private final Point endPoint = new Point(1.0, 1.0);
    /** ********************************************************************
     * Map storing result of discretization test
     * Map<steps, length>
     */
    private Map<Integer, Double> result = new HashMap<>();
    
    /** ********************************************************************
     * List of discretization Points including starting and ending Point
     */
    private List<Point> discretizationPoints = new ArrayList<>();

    public DiscretizationTest(int numberOfSteps) {
        if (numberOfSteps <= 1) {
            throw new IllegalArgumentException("Number of discretization steps has to be bigger than 1");
        }
        this.numberOfSteps = numberOfSteps;
    }

    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    public void test(){
        for (int i=1; i<=numberOfSteps; i++){
            result.put(i, _getLength(i));
            
//            System.out.println("Difference for " + i + " discretization: " + (_getLength(i)-Math.sqrt(2)));
//            for (Point p : discretizationPoints) {
//                System.out.println(p.getX());
//            }
        }
        for (Integer i : result.keySet()) {
                System.out.println(result.get(i)-Math.sqrt(2));
            }
    }
    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    private Double _getLength(int steps){
//        Double length = 0.0;
        discretizationPoints = new ArrayList<>();
        _discretizeLine(steps);
//        _calculateLength();
        return _calculateLength();
    }
    
    private void _discretizeLine(int parts){
        discretizationPoints = new ArrayList<>();
        discretizationPoints.add(startPoint);
        double previous = 0.0;
        double next = 0.0;
        double step = 1.0/parts;
        for(int i=2; i<=parts; i++){
            next = previous+step;
            discretizationPoints.add(new Point(next, next));
            previous=next;
        }
        discretizationPoints.add(endPoint);
    }
    
    private double _calculateLength(){
        double length = 0.0;
        Point prev = discretizationPoints.get(0);
        double dx=0, dy=0;
        for(int i=1; i<discretizationPoints.size(); i++){
            dx=0; dy=0;
            dx=discretizationPoints.get(i).getX() - prev.getX();
            dy=discretizationPoints.get(i).getY() - prev.getY();
            length += Math.sqrt(dx*dx + dy*dy);
            prev = discretizationPoints.get(i);
        }
        return length;
    }
    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
    public Map<Integer, Double> getResultsMap(){
        return result;
    }
}
