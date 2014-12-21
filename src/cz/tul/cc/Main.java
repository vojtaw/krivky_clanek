package cz.tul.cc;

import cz.tul.cc.discretization.DiscretizationTest;
import cz.tul.cc.mastercurve.mastercircle.MasterCircle;
import cz.tul.cc.point.Point;

/** ************************************************************************
 *
 * @author wojta
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Křivky");
//        MasterCircle c = new MasterCircle(new Point(0.5, 0.5), new Point(2.0, 0.0));
//        System.out.println("c:"+c);
        MasterCircle c2 = new MasterCircle(new Point(0.5, 0.5), new Point(1.0, 0.0));
        System.out.println("c2:"+c2);
        for (Point p : c2.getDiscretizationPoints(2)) {
            System.out.println(p);
        }
//        DiscretizationTest test = new DiscretizationTest(2000);
//        test.test();
    }
    
    
}
