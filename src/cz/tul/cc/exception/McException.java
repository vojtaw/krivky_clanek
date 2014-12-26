package cz.tul.cc.exception;

import org.apache.log4j.Logger;

/** ************************************************************************
 *
 * @author wojta
 */
public class McException extends Exception {
    static Logger cat = Logger.getLogger(McException.class.getName());
    /** ******************************************************************** */
    /** Atribut tridy ****************************************************** */
    /** ******************************************************************** */
    /** ********************************************************************
     * Text chyby
     */
    protected String message;
    /** ********************************************************************
     * Text chyby původní vyjímky
     */
    protected String originalMessage;
    /** ********************************************************************
     * Číslo/kód chyby
     */
    protected int errCode;

    /** ******************************************************************** */
    /** Constructors and factory methods *********************************** */
    /** ******************************************************************** */
    public McException(ExceptionEnum ex) {
        super();//ex.getMessage()
        this.errCode = ex.getErrCode();
        this.message = ex.getMessage();
    }
    
    public McException(ExceptionEnum ex, String originalMessage) {
        this(ex);
        this.originalMessage = originalMessage;
    }
    
//    public McException(int errCode, String message) {
//        super(message);
//        this.errCode = errCode;
//    }

    /** ******************************************************************** */
    /** Private and protected methods ************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** Public methods ***************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** Abstract and overriden methods ************************************* */
    /** ******************************************************************** */
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getLocalizedMessage() {
        return this.message;
    }

    /** ******************************************************************** */
    /** Getters and setters ************************************************ */
    /** ******************************************************************** */
}
