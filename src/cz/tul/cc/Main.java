package cz.tul.cc;

import cz.tul.cc.discretization.DiscretizationTest;
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
        DiscretizationTest test = new DiscretizationTest(2000);
        test.test();
    }
    
    
}
