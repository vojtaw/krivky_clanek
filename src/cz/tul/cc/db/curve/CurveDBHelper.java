package cz.tul.cc.db.curve;

import cz.tul.cc.db.DB;
import cz.tul.cc.db.DBFilter;
import cz.tul.cc.db.DBSort;
import cz.tul.cc.db.DBTablesEnum;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/** ************************************************************************
 *
 * @author wojta
 */
public class CurveDBHelper extends DB {
    static Logger logger = Logger.getLogger(CurveDBHelper.class.getName());
    private CurveBean bean;

    /** ********************************************************************* */
    /** Constructors ******************************************************** */
    /** ********************************************************************* */
    public CurveDBHelper(DBSort sort, DBFilter basicFilter, DBFilter extendedFilter) {
        super(sort, basicFilter, extendedFilter);
    }
    public CurveDBHelper() {
        super(new DBSort(), new DBFilter(), new DBFilter());
    }
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    public List<CurveBean> getCurves() throws Exception {
        List<CurveBean> results = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(_getSqlCurve());
            setStatementFilterValues(pstmt, basicFilter, extendedFilter);
//            diggerBean.setDiggerId(DiggerConstants.DIGGER_ID_ALL);
//            int i = 1;
//            if (status != DiggerConstants.DIGGER_STATUS_ALL) {
//                pstmt.setInt(i++, status);
//            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(getBeanFromResultSet(rs));
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
//            throw new McException(ExceptionEnum.DB_ERROR);
        }
        return results;
    }

    public int insertCurve(CurveBean bean) throws Exception {
        int ret = 0;
        try {
            logger.debug("getSqlInsert():" + _getSqlInsert());
            pstmt = conn.prepareStatement(_getSqlInsert());
            int i = 1;
//            if (diggerBean.getDiggerId() != DiggerConstants.DIGGER_ID_ALL) {
//                pstmt.setInt(i++, bean.getDigger().getId());
//            }
//            if (basicFilter.getFilterValue(DiggerDBFieldsEnum.FIELD_ID) != DiggerConstants.DIGGER_ID_ALL) {
//                pstmt.setInt(i++, bean.getDiggerId());
//            }
//            pstmt.setInt(i++, bean.getId());
            pstmt.setDouble(i++, bean.getStartX());
            pstmt.setDouble(i++, bean.getStartY());
            pstmt.setDouble(i++, bean.getMidX());
            pstmt.setDouble(i++, bean.getMidY());
            pstmt.setDouble(i++, bean.getEndX());
            pstmt.setDouble(i++, bean.getEndY());
            pstmt.setDouble(i++, bean.getCentreX());
            pstmt.setDouble(i++, bean.getCentreY());
            pstmt.setDouble(i++, bean.getAlpha());
            pstmt.setDouble(i++, bean.getCurvatureRatio());
            pstmt.setDouble(i++, bean.getArcLength());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }
        return ret;
    }
    
    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    private CurveBean getBeanFromResultSet(ResultSet rs) throws SQLException {
        CurveBean bean = new CurveBean();
        bean.setId(rs.getInt(CurveDBFieldsEnum.FIELD_ID.getField()));
        bean.setStartX(rs.getDouble(CurveDBFieldsEnum.FIELD_START_X.getField()));
        bean.setStartY(rs.getDouble(CurveDBFieldsEnum.FIELD_START_Y.getField()));
        bean.setMidX(rs.getDouble(CurveDBFieldsEnum.FIELD_MID_X.getField()));
        bean.setMidY(rs.getDouble(CurveDBFieldsEnum.FIELD_MID_Y.getField()));
        bean.setEndX(rs.getDouble(CurveDBFieldsEnum.FIELD_END_X.getField()));
        bean.setEndY(rs.getDouble(CurveDBFieldsEnum.FIELD_END_Y.getField()));
        bean.setCentreX(rs.getDouble(CurveDBFieldsEnum.FIELD_CENTRE_X.getField()));
        bean.setCentreY(rs.getDouble(CurveDBFieldsEnum.FIELD_CENTRE_Y.getField()));
        bean.setAlpha(rs.getDouble(CurveDBFieldsEnum.FIELD_ALPHA.getField()));
        bean.setCurvatureRatio(rs.getDouble(CurveDBFieldsEnum.FIELD_CURVATURE_RATIO.getField()));
        bean.setArcLength(rs.getInt(CurveDBFieldsEnum.FIELD_ARC_LENGTH.getField())); //TODO
        return bean;
    }

    private String _getSelect() {
        StringBuilder ret = new StringBuilder("SELECT ");
        List<String> items = new ArrayList<>();
        for (CurveDBFieldsEnum field : CurveDBFieldsEnum.values()) {
            items.add(DBTablesEnum.TABLE_CURVE.getTable() + "." + field.getField());
        }
        ret.append(StringUtils.join(items, " , "));
        ret.append(" FROM ").append(DBTablesEnum.TABLE_CURVE.getTable()).append(" ");
        return ret.toString();
    }

    private String _getSqlCurve() {
        sql = new StringBuffer("");
        sql.append(_getSelect());

        sql.append(getWhereFilter(basicFilter, extendedFilter));
        sql.append(getSort(sort));
        System.out.println(sql.toString());
//        boolean isFirst = true;
//        if (diggerBean.getDiggerId() != DiggerConstants.DIGGER_ID_ALL) {
//            isFirst = whereBuilder(isFirst, "diggerId=?");
//        }
//        if (status != DiggerConstants.DIGGER_STATUS_ALL) {
//            isFirst = whereBuilder(isFirst, "status=?");
//        }
        return sql.toString();
    }

    private String _getSqlInsert() {
        sql = new StringBuffer("");
        sql.append("insert into " + DBTablesEnum.TABLE_CURVE.getTable() + " (");
//        if (bean.getDiggerId() != DiggerConstants.DIGGER_ID_ALL) {
//            sql.append("diggerId, ");
//        }
        sql.append("startX, startY, midX, midY, endX, endY, centreX, centreY, alpha, curvatureRatio, arcLength) ");
        sql.append(" values (");

//        if (diggerBean.getDiggerId() != DiggerConstants.DIGGER_ID_ALL) {
//            sql.append("?,");
//        }
        sql.append("?,?,?,?,?,?,?,?,?,?,?)");
        return sql.toString();
    }

    private String _getSqlUpdate() {
        sql = new StringBuffer("");
        sql.append("update " + DBTablesEnum.TABLE_CURVE.getTable() + " set ");
        sql.append("startX=?, startY=?, midX=?, midY=?, endX=?, endY=?, centreX=?, centreY=?, alpha=?, curvatureRatio=?, arcLength=? ");

        sql.append(" where id=?");

        return sql.toString();
    }

    private String _getSqlDelete() {
        sql = new StringBuffer("");
        sql.append("delete from " + DBTablesEnum.TABLE_CURVE.getTable() + " ");
        sql.append("where id=?");

        return sql.toString();
    }

    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
}
