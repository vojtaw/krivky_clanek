package cz.tul.cc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public abstract class AbstactDB {

    static Logger logger = Logger.getLogger(AbstactDB.class.getName());
    protected Connection conn = null;
    protected Statement stmt = null;

    protected abstract void createConnection();

    protected abstract void shutdown();
    protected StringBuffer sql = new StringBuffer("");
    private int rowsCount = 0;

    public abstract void load() throws SQLException, Exception;

    public abstract void insert() throws SQLException, Exception;

    public abstract void update() throws SQLException, Exception;

    public abstract void setData(ResultSet rs) throws SQLException, Exception;

    protected boolean whereBuilder(boolean isFirst, String condition) {
        if (!isFirst) {
            sql.append(" AND ");
        } else {
            sql.append(" where ");
        }
        sql.append(condition);
        return false;
    }
    
    protected boolean sortBuilder(boolean isFirst, String condition) {
        if (!isFirst) {
            sql.append(", ");
        } else {
            sql.append(" ORDER BY ");
        }
        sql.append(condition);
        
        return false;
    }

    protected String dbURL() {
        return DBConstants.dbURL+DBConstants.dbPath;
    }
}
