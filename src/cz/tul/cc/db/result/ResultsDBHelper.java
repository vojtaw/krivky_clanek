package cz.tul.cc.db.result;

import cz.tul.cc.Main;
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
public class ResultsDBHelper extends DB {
    static Logger logger = Logger.getLogger(ResultsDBHelper.class.getName());
    private ResultBean bean;

    /** ********************************************************************* */
    /** Constructors ******************************************************** */
    /** ********************************************************************* */
    public ResultsDBHelper(DBSort sort, DBFilter basicFilter, DBFilter extendedFilter) {
        super(sort, basicFilter, extendedFilter);
    }
    public ResultsDBHelper() {
        super(new DBSort(), new DBFilter(), new DBFilter());
    }
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    protected List<ResultBean> getResults() throws Exception {
        List<ResultBean> results = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(_getSqlResults());
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

    public int insertResult(ResultBean bean) throws Exception {
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
            pstmt.setInt(i++, bean.getCurveId());
            pstmt.setDouble(i++, bean.getCurvatureRatio());
            pstmt.setDouble(i++, bean.getArcLength());
            pstmt.setInt(i++, bean.getDiscretizationParts());
            pstmt.setDouble(i++, bean.getLinearLength());
            pstmt.setDouble(i++, bean.getBezierLength());
            pstmt.executeUpdate();
//            ResultSet rs = pstmt.getGeneratedKeys();
//            if (rs.next()) {
//                ret = rs.getInt(1);
//            }
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
    private ResultBean getBeanFromResultSet(ResultSet rs) throws SQLException {
        ResultBean bean = new ResultBean();
        bean.setId(rs.getInt(ResultDBFieldsEnum.FIELD_ID.getField()));
        bean.setCurveId(rs.getInt(ResultDBFieldsEnum.FIELD_CURVE_ID.getField()));
        bean.setCurvatureRatio(rs.getDouble(ResultDBFieldsEnum.FIELD_CURVATURE_RATIO.getField()));
        bean.setArcLength(rs.getInt(ResultDBFieldsEnum.FIELD_ARC_LENGTH.getField())); //TODO
        bean.setLinearLength(rs.getInt(ResultDBFieldsEnum.FIELD_LINEAR_LENGTH.getField()));
        bean.setBezierLength(rs.getInt(ResultDBFieldsEnum.FIELD_BEZIER_LENGTH.getField()));
        return bean;
    }

    private String _getSelect() {
        StringBuilder ret = new StringBuilder("SELECT ");
        List<String> items = new ArrayList<>();
        for (ResultDBFieldsEnum field : ResultDBFieldsEnum.values()) {
            items.add(DBTablesEnum.TABLE_RESULTS.getTable() + "." + field.getField());
        }
        ret.append(StringUtils.join(items, " , "));
        ret.append(" FROM ").append(DBTablesEnum.TABLE_RESULTS.getTable()).append(" ");
        return ret.toString();
    }

    private String _getSqlResults() {
        sql = new StringBuffer("");
        sql.append(_getSelect());
//        sql.append("select diggerId, diggerTypeId, ");
//        sql.append("diggerName, defaultFloorId, status, ");
//        sql.append("upperCut, lowerCut, machineHours ");
//        sql.append("from Digger");
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
        sql.append("insert into " + DBTablesEnum.TABLE_RESULTS.getTable() + " (");
//        if (bean.getDiggerId() != DiggerConstants.DIGGER_ID_ALL) {
//            sql.append("diggerId, ");
//        }
        sql.append("curveId, curvatureRatio, arcLength, discretizationParts, linearLength, bezierLength) ");
        sql.append(" values (");

//        if (diggerBean.getDiggerId() != DiggerConstants.DIGGER_ID_ALL) {
//            sql.append("?,");
//        }
        sql.append("?,?,?,?,?,?)");
        return sql.toString();
    }

    private String _getSqlUpdate() {
        sql = new StringBuffer("");
        sql.append("update " + DBTablesEnum.TABLE_RESULTS.getTable() + " set ");
        sql.append("curveId=?, curvatureRatio=?, arcLength=?, discretizationParts=?, linearLength=?, bezierLength=? ");

        sql.append(" where id=?");

        return sql.toString();
    }

    private String _getSqlDelete() {
        sql = new StringBuffer("");
        sql.append("delete from " + DBTablesEnum.TABLE_RESULTS.getTable() + " ");
        sql.append("where id=?");

        return sql.toString();
    }

    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
}
