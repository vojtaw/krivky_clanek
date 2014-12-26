package cz.tul.cc.db;

/**
 * @author ikopetschke
 */
public enum DBTablesEnum {
        TABLE_RESULTS("results"),
        TABLE_CURVE("curve");
        
        private String table;
        private DBTablesEnum(String table) {
            this.table = table;
        }
        
        /**
         * Vraci string s nazvem tabulky v DB
         * @return jmeno tabulky
         */
        public String getTable() {
            return this.table;
        }
        
    }
