package cz.tul.cc.db;

import cz.tul.cc.exception.McException;
import java.util.List;
import java.util.Map;

/** ************************************************************************
 *
 * @author wojta
 */
public interface DBHelperInterface<T> {
    /** ******************************************************************** */
    /** Atribut tridy ****************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** Public methods ***************************************************** */
    /** ******************************************************************** */
    public T getBean() throws McException;

    public Map<Integer, T> getBeansById() throws McException;
    
    public List<T> getBeans()  throws McException;

    public void insert(T bean) throws McException;

    public void update(T bean) throws McException;

    public void delete(int id) throws McException;

//    public boolean isDeletable(int id) throws McException;
    
    /** ********************************************************************
     * Metoda nastaví filtr pro tabulky, podle hodnot filtru se pak vracejí odpovídající data v getBean...
     *
     * @param basicFilter filtr pro master tabulku
     * @param extendedFilter filtr pro slave tabulku
     */
    public void setFilter(DBFilter basicFilter, DBFilter extendedFilter);
    
    /** ********************************************************************
     * Metoda nastaví třídění pro master tabulku, podle nastavení třídění se pak vracejí setříděná data v getBean...
     *
     * @param DBSort sloupec a asc/desc
     */
    public void setSort(DBSort sort);
    
}
