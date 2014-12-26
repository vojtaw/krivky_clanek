package cz.tul.cc.db.result;

import cz.tul.cc.db.DBFieldsEnum;

/** ************************************************************************
 *
 * @author wojta
 */
public enum ResultDBFieldsEnum implements DBFieldsEnum {
    FIELD_ID("id", false),
    FIELD_CURVE_ID("durveId", true),
    FIELD_CURVATURE_RATIO("curvatureRatio", false),
    FIELD_ARC_LENGTH("arcLength", false),
    FIELD_DISCRETIZATION_PARTS("discretizationParts", false),
    FIELD_LINEAR_LENGTH("linearLength", false),
    FIELD_BEZIER_LENGTH("bezierLength", false);

    private String field;
    private boolean isFK;

    private ResultDBFieldsEnum(String field, boolean isFK) {
        this.field = field;
        this.isFK = isFK;
    }

    private ResultDBFieldsEnum(String field) {
        this(field, false);
    }

    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** Getters and setters ************************************************ */
    /** ******************************************************************** */
    @Override
    public String getField() {
        return field;
    }

    public String getText() {
        return getField();
    }

    @Override
    public boolean isFK() {
        return this.isFK;
    }

}
