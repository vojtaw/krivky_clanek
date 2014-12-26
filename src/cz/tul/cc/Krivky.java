package cz.tul.cc;

import cz.tul.cc.db.DBSort;
import cz.tul.cc.db.SortTypeEnum;
import cz.tul.cc.db.curve.CurveBean;
import cz.tul.cc.db.curve.CurveDBFieldsEnum;
import cz.tul.cc.db.curve.CurveDBHelper;
import cz.tul.cc.mastercurve.MasterCurveInterface;
import cz.tul.cc.mastercurve.mastercircle.MasterCircle;
import cz.tul.cc.point.Point;
import java.util.ArrayList;
import java.util.List;

/** ************************************************************************
 *
 * @author wojta
 */
public class Krivky {

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Krivky.class.getName());
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
    /** ****************************************************************
     * Generate test curves and put them into List and DB
     */
    private List<MasterCurveInterface> curveList = new ArrayList<>();

    public void generateCurves() {
        final int NUMBER_OF_TEST_CURVES = 1;
        final double MID_POINT_X_COORD = 0.5;
        final double MID_POINT_Y_MAX = 0.5;
        double midPointStep = MID_POINT_Y_MAX / NUMBER_OF_TEST_CURVES;
        double midPointY = midPointStep;
        for (int i = 0; i < NUMBER_OF_TEST_CURVES; i++) {
            curveList.add(new MasterCircle(new Point(MID_POINT_X_COORD, midPointY)));
            midPointY += midPointStep;
        }

        CurveBean cb = new CurveBean();
        CurveDBHelper helper = new CurveDBHelper();

        for (MasterCurveInterface curve : curveList) {
            cb = new CurveBean();
            cb.setStartX(curve.getStartPoint().getX());
            cb.setStartY(curve.getStartPoint().getY());
            cb.setMidX(curve.getMidPoint().getX());
            cb.setMidY(curve.getMidPoint().getY());
            cb.setEndX(curve.getEndPoint().getX());
            cb.setEndX(curve.getEndPoint().getX());
            cb.setCentreX(curve.getCentrePoint().getX());
            cb.setCentreY(curve.getCentrePoint().getX());
            cb.setAlpha(curve.getAlpha());
            cb.setCurvatureRatio(curve.getCurvatureRatio());
            cb.setArcLength(curve.getLength());
            try {
                helper.insertCurve(cb);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }
    
    public void loadCurves() {
//        CurveBean cb = new CurveBean();
        CurveDBHelper helper = new CurveDBHelper();
        DBSort sort = new DBSort();
        sort.addSort(CurveDBFieldsEnum.FIELD_ID, SortTypeEnum.SORT_ASC);
        helper.setSort(sort);
        List<CurveBean> list = new ArrayList<>();
        try {
            list.addAll(helper.getCurves());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return;
        }
        for (CurveBean cb : list) {
            curveList.add(new MasterCircle(cb));
        }
    }
}
