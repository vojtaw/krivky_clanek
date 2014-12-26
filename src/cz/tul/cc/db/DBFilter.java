package cz.tul.cc.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Trida reprezentujici obecny filtr pro databazi
 * Priklad pouziti:
 * DBFilter df = new DBFilter(FilterOperatorEnum.TABLE_MILESTONE);
 * df.addParam( MilestoneDBFieldsEnum.FIELD_STATUS, filtrActive.getValue(), FilterOperatorEnum.OPER_EQUAL );
 * 
 * ziskani hodnoty z filtru
 * df.getFilterValue( MilestoneDBFieldsEnum.FIELD_STATUS);
 * @author ikopetschke
 */
public class DBFilter {
    /**
     * vnintrni trida reprezentujici jednu polozku filtru
     */
    public class Filter {
        private DBFieldsEnum field;
        private Object value;
        private FilterOperatorEnum operator;
        
        private Filter(DBFieldsEnum field, Object value, FilterOperatorEnum operator) {
            this.field = field;
            this.value = value;
            this.operator = operator;
        }

        public DBFieldsEnum getField() {
            return field;
        }

        public void setField(DBFieldsEnum field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public FilterOperatorEnum getOperator() {
            return operator;
        }

        public void setOperator(FilterOperatorEnum operator) {
            this.operator = operator;
        }
    }
    
    private DBTablesEnum tblName;
    private Map<DBFieldsEnum,Filter> filters;
    
    public DBFilter() {
        this.filters = new HashMap<>();
    }
    
    public DBFilter(DBTablesEnum tblName) {
        this();
        this.tblName = tblName;
    }
    
    public Filter getFilterInstance(DBFieldsEnum field, Object value, FilterOperatorEnum operator) {
        return new Filter(field, value, operator);
    }

    public DBTablesEnum getTblName() {
        return this.tblName;
    }

    public void setTblName(DBTablesEnum tblName) {
        this.tblName = tblName;
    }

    public Map<DBFieldsEnum,Filter> getFilters() {
        return this.filters;
    }
    
    public Object getFilterValue(DBFieldsEnum field) {
        Filter f = this.filters.get(field);
        if (f != null && f instanceof Filter) {
            return f.value;
        } else {
            return null;
        }
    }

    public void setFilters(Map<DBFieldsEnum,Filter> filters) {
        this.filters = filters;
    }
    
    public void addFilter(DBFieldsEnum field, Object value, FilterOperatorEnum operator) {
        addFilter( new Filter(field, value, operator));
    }
    
    public void addFilter(Filter filter) {
        this.filters.put(filter.getField(), filter);
    }
    
}
