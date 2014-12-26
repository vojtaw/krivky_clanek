package cz.tul.cc.db;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

public class DB extends AbstactDB {

    static Logger logger = Logger.getLogger(DB.class.getName());
    protected Connection conn = null;
    protected Statement stmt = null;
    protected PreparedStatement pstmt = null;
    protected DBFilter basicFilter;
    protected DBFilter extendedFilter;
    protected DBSort sort;

    /** Costructors ******************************************************** */
    @Deprecated
    public DB() {
        createConnection();
    }

    public DB(DBSort sort, DBFilter basicFilter, DBFilter extendedFilter) {
        createConnection();

        this.sort = sort;
        this.basicFilter = basicFilter;
        this.extendedFilter = extendedFilter;
    }

    /** destructor ********************************************************* */
    protected void finalize() throws Throwable {
//		cat.debug("DB destructor");
//		shutdown();
        closeConnection();
        super.finalize();
    }

    protected void createConnection() {
//		cat.debug("createConnection");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();//ClientDriver
//            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();//ClientDriver
        } catch (ClassNotFoundException e) {
            logger.error("Chyba Class.forName: " + e.getMessage());
        } catch (InstantiationException e) {
            logger.error("Chyba Class.forName: " + e.getMessage());
            logger.error(e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            logger.error("Chyba Class.forName: " + e.getMessage());
            logger.error(e.getLocalizedMessage());
        }
        try {
            // Get a connection
            conn = DriverManager.getConnection(dbURL() + ";create=true");
        } catch (SQLException e) {
            logger.error("Chyba vytvoření connection: " + e.getMessage() + ", URL: " + dbURL());
        } catch (Exception e) {
            logger.error("Chyba vytvoření connection: " + e.getMessage() + ", URL: " + dbURL());
        }
    }

    protected void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("Chyba uzavření connection: " + e.getErrorCode() + ", " + e.getMessage());
        }
    }

    protected void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                DriverManager.getConnection(dbURL() + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException e) {
            logger.error("Chyba uzavření connection: " + e.getErrorCode() + ", " + e.getMessage());
        }

    }

    /**
     * ********************************************************************
     * Nastaví do statementu proměnné podle zadaných filtrů
     *
     * @param pstmt   PreparedStatment do kterého se přidávají proměnné
     * @param filters filtr/y master a případných slaves tabulek
     */
    protected void setStatementFilterValues(PreparedStatement pstmt, DBFilter... filters) throws SQLException {
        int i = 1;
        for (DBFilter filter : filters) {
            if (filter != null && !filter.getFilters().isEmpty()) {
                for (DBFieldsEnum field : filter.getFilters().keySet()) {
                    DBFilter.Filter f = filter.getFilters().get(field);
                    Object value = f.getValue();
                    if (value instanceof Enum) {
                        try {
                            Method m = ((Enum) value).getDeclaringClass().getMethod("getId");
                            value = m.invoke(value);
                        } catch (Exception ex) {
                            logger.error(null, ex);
                        }
                    }
                    if (isIgnoredValue(value)) {
                        continue;
                    }
                    if (value instanceof Integer) {
                        pstmt.setInt(i++, (Integer) value);
                    } else if (value instanceof Double) {
                        pstmt.setDouble(i++, (Double) value);
                    } else if (value instanceof Boolean) {
                        pstmt.setBoolean(i++, (Boolean) value);
                    } else if (value instanceof String) {
                        pstmt.setString(i++, "%" + (String) value + "%");
                    } else if (value instanceof Calendar) {
                        pstmt.setTimestamp(i++, new Timestamp(((Calendar) value).getTime().getTime()));
                    } else if (value instanceof DBBetweenValues) {
                        DBBetweenValues bet = ((DBBetweenValues) value);
                        for (Object o : bet.getValuesList()) {
                            if (o instanceof Calendar) {
                                pstmt.setTimestamp(i++, new Timestamp(((Calendar) o).getTime().getTime()));
                            }
                        }
                    }
                }
            }
        }
    }

    protected String getWhereFilter(DBFilter... filters) {
        List<String> items = new ArrayList<>();
        for (DBFilter filter : filters) {
            if (filter != null && !filter.getFilters().isEmpty()) {
                for (DBFieldsEnum field : filter.getFilters().keySet()) {
                    DBFilter.Filter f = filter.getFilters().get(field);
                    Object value = f.getValue();
                    if (value instanceof Enum) {
                        try {
                            Method m = ((Enum) value).getDeclaringClass().getMethod("getId");
                            value = m.invoke(value);
                        } catch (Exception ex) {
                            logger.error(null, ex);
                        }
                    }
                    if (isIgnoredValue(value)) {
                        continue;
                    }
                    items.add(filter.getTblName().getTable() + "." + field.getField() + _getOperator(f.getOperator()));
                }
            }
        }

        if (items.isEmpty()) {
            return "";
        } else {
            return " WHERE " + StringUtils.join(items, " AND ");
        }
    }

    protected String getSort(DBSort sort) {
        List<String> items = new ArrayList<>();

        if (sort != null && !sort.getSorts().isEmpty()) {
            for (DBFieldsEnum field : sort.getSorts().keySet()) {
                DBSort.Sort sr = sort.getSorts().get(field);
                items.add(field.getField() + _getSortOperator(sr.getSortType()));
            }
        }
        if (items.isEmpty()) {
            return " ";
        } else {
            return " ORDER BY " + StringUtils.join(items, " , ") + " ";
        }
    }

    private boolean isIgnoredValue(Object value) {
        boolean ret = false;
        if (value == null) {
            return true;
        }
        if (value instanceof String && ((String) value).trim().isEmpty()) {
            return true;
        }
        if (value instanceof Integer && ((Integer) value).intValue() == -1) {
            return true;
        }
        return ret;
    }

    private String _getOperator(FilterOperatorEnum oper) {
        switch (oper) {
            case OPER_EQUAL:
                return " = ?";
            case OPER_LIKE:
                return " like ?";
            case OPER_NOT_EQUAL:
                return " <> ?";
            case OPER_LESS:
                return " < ?";
            case OPER_LESS_OR_EQUAL:
                return " <= ?";
            case OPER_GREAT:
                return " > ?";
            case OPER_GREAT_OR_EQUAL:
                return " >= ?";
            case OPER_BETWEEN:
                return "  BETWEEN ? AND ? ";
        }
        throw new UnsupportedOperationException("Neznámý typ filtr operátoru");
    }

    private String _getSortOperator(SortTypeEnum oper) {
        switch (oper) {
            case SORT_ASC:
                return " ASC";
            case SORT_DESC:
                return " DESC";
            case SORT_NONE:
                return " ";
        }
        throw new UnsupportedOperationException("Neznámý typ sort operátoru");
    }

    /** ******************************************************************** */
    /** Abstract and overriden methods ************************************* */
    /** ******************************************************************** */
    @Override
    public void load() throws SQLException, Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void insert() throws SQLException, Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void setData(ResultSet rs) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void update() throws SQLException, Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * ********************************************************************
     */
    /**
     * Getters and setters ************************************************
     */
    /**
     * ********************************************************************
     */
    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public Connection getConn() {
        return conn;
    }

    public void setSort(DBSort sort) {
        this.sort = sort;
    }
    
    
}
