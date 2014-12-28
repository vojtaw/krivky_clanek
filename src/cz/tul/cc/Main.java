package cz.tul.cc;

import cz.tul.cc.db.AbstactDB;
import cz.tul.cc.db.result.ResultBean;
import cz.tul.cc.db.result.ResultsDBHelper;
import cz.tul.cc.discretization.DiscretizationTest;
import cz.tul.cc.intepolation.BezierInterpolation;
import cz.tul.cc.intepolation.InterpolationInterface;
import cz.tul.cc.intepolation.LinearInterpolation;
import cz.tul.cc.mastercurve.MasterCurveInterface;
import cz.tul.cc.mastercurve.mastercircle.MasterCircle;
import cz.tul.cc.point.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/** ************************************************************************
 *
 * @author wojta
 */
public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Křivky");
        try {
            org.apache.log4j.PropertyConfigurator.configure("C:\\cc\\cfg\\log4j.properties");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        logger.debug("Křivky");
        Krivky krivky = new Krivky();
//        krivky.generateCurves();
        krivky.loadCurves();
        krivky.compute();
//        krivky.showCurves();
        
        
//        MasterCircle c = new MasterCircle(new Point(0.5, 0.5), new Point(2.0, 0.0));
//        System.out.println("c:"+c);
//        MasterCircle c2 = new MasterCircle(new Point(0.5, 0.5), new Point(1.0, 0.0));
//        System.out.println("c2:"+c2);
//        for (Point p : c2.getDiscretizationPoints(3)) {
//            System.out.println(p);
//        }

        /** ****************************************************************
         * DB test
         */
//        ResultsDBHelper helper = new ResultsDBHelper();
//        ResultBean bean = new ResultBean();
//        bean.setCurveId(1);
//        bean.setCurvatureRatio(0.04);
//        bean.setDiscretizationParts(10);
//        bean.setArcLength(1.1111111);
//        bean.setLinearLength(2.222222222222);
//        bean.setBezierLength(3.3333333333333);
//
//        try {
//            helper.insertResult(bean);
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        }

        if (true) {
            return;
        };
        

//        InterpolationInterface linear;
//        InterpolationInterface bezier;
//        double linearLength=0.0, bezierLength=0.0;
//        final int NUMBER_OF_DISCRETIZATION_PARTS = 20;
//        List<Point> discretizationPoints = new ArrayList<>();
//        for (int parts = 4; parts < NUMBER_OF_DISCRETIZATION_PARTS; parts++) {
//            for (MasterCurveInterface curve : curveList) {
//                discretizationPoints = curve.getDiscretizationPoints(parts);
//                linear = new LinearInterpolation(discretizationPoints);
//                linearLength = linear.getLength();
//                System.out.println("parts " + parts + ", length: " + linearLength + ", diff: " + (curve.getLength() - linearLength));
//                bezier = new BezierInterpolation(discretizationPoints);
//                bezierLength = bezier.getLength();
//                System.out.println("parts " + parts + ", length: " + bezierLength + ", diff: " + (curve.getLength() - bezierLength));
//    //            bezier = new BezierInterpolation(discretizationPoints);
//                //            bezier.getLength();
//
//            }
//        }

    }
    

}
