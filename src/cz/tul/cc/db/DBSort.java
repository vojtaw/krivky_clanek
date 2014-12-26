package cz.tul.cc.db;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author ikopetschke
 */
public class DBSort {
    /**
     * vnintrni trida reprezentujici jednu polozku sortu
     */
    public class Sort {
        private DBFieldsEnum field;
        private SortTypeEnum sortType;
        
        private Sort(DBFieldsEnum field, SortTypeEnum sortType) {
            this.field = field;
            this.sortType = sortType;
        }
        
        public DBFieldsEnum getField() {
            return field;
        }

        public void setField(DBFieldsEnum field) {
            this.field = field;
        }

        public SortTypeEnum getSortType() {
            return sortType;
        }

        public void setSortType(SortTypeEnum sortType) {
            this.sortType = sortType;
        }
    }
    
    private Map<DBFieldsEnum, Sort> sorts;

    public DBSort() {
        sorts = new LinkedHashMap<>();
    }
    
    public Map<DBFieldsEnum, Sort> getSorts() {
        return this.sorts;
    }
    
    public Sort getSort(DBFieldsEnum field) {
        return this.sorts.get(field);
    }
    
    public void addSort(DBFieldsEnum field, SortTypeEnum sortType) {
        addSort( new Sort(field, sortType));
    }
    
    public void addSort(Sort sort) {
        this.sorts.put(sort.getField(), sort);
    }
}
