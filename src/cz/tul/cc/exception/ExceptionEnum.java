package cz.tul.cc.exception;

import cz.tul.cc.util.Util;

/** ************************************************************************
 * Výčtový typ pro výjimky do GUI
 * 
 * @author wojta
 */
public enum ExceptionEnum {
    /**UNKNOWN neznámá chyba **/
    UNKNOWN(Util.getText("exception.unknown")),
    /**DB_ERROR Chyba při práci s databází **/
    DB_ERROR(Util.getText("exception.db.error")),
    /**DB_RECORD_NOT_SAVED Záznam se nepodařilo uložit **/
    DB_RECORD_NOT_SAVED(Util.getText("exception.db.notsaved")),
    /**DB_RECORD_NOT_DELETED Záznam se nepodařilo smazat **/
    DB_RECORD_NOT_DELETED(Util.getText("exception.db.notdeleted")),
    /**WRONG_PARAMETERS Chybně inicializované parametry **/
    WRONG_PARAMETERS(Util.getText("exception.db.wrongparameters")),
    /**NO_FILTER_SET Nebyl nastaven žádný filter **/
    NO_FILTER_SET(Util.getText("exception.db.nofilterset")),

    /**
     * Nenalezeny data bagru (pozice a orientace)
     */
    NO_DIGGERS(Util.getText("exception.import.nodiggers")),
    /**
     * Nenalezena výšková data (mesh)
     */
    NO_HEIGHT_MESH(Util.getText("exception.import.noheightmesh")),
    /**
     * Nenalezena GPS výšková data
     */
    NO_HEIGHT_NODES(Util.getText("exception.import.noheightnodes")),
    /**
     * Nenalezena data pater
     */
    NO_LINES(Util.getText("exception.import.nolines")),
    /**
     * Nenalezeny čáry pasovek
     */
    NO_CONVEYOR_LINES(Util.getText("exception.import.noconveyorlines")),
    /**
     * Nenalezeny názvy pasovek
     */
    NO_CONVEYOR_NAMES(Util.getText("exception.import.noconveyornames")),
    
    /** WRONG_USERMILESTONE_TYPE yl zadán milestone nesprávného typu nebo Uživatelský milestone nesprávného typu. */
    WRONG_USERMILESTONE_TYPE(Util.getText("exception.milestone.wrongusermilestonetype"));

   
    private int errCode;
    private String message;

    private ExceptionEnum(String message) {
        this.message = message;
        errCode = 0;
    }
    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** Getters and setters ************************************************ */
    /** ******************************************************************** */
    public int getErrCode(){
        return errCode;
    }
    public String getMessage() {
        return message;
    }
    public String getText() {
        return getMessage();
    }
    
    public static ExceptionEnum[] getExceptionEnum() {
//        ExceptionEnum[] val = new ExceptionEnum[values().length - 1];
//        System.arraycopy(values(), 1, val, 0, values().length - 1);
        return values();
    }
}
