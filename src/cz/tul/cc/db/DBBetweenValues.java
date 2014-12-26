package cz.tul.cc.db;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ikopetschke
 */
public class DBBetweenValues<T> {
    private List<T> values;
    private T type;

    public DBBetweenValues() {
        values = new ArrayList();
    }

    public DBBetweenValues(T... items) {
        this();
        for (T o : items) {
            values.add(o);
        }
    }
    
    public List<T> getValuesList() {
        return this.values;
    }
    
    public Class getClassType() {
        return type.getClass();
    }
    
    
}
