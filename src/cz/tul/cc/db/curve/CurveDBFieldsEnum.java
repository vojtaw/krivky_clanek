package cz.tul.cc.db.curve;

import cz.tul.cc.db.DBFieldsEnum;

/** ************************************************************************
 *
 * @author wojta
 */
public enum CurveDBFieldsEnum implements DBFieldsEnum {
    FIELD_ID("id", false),
    FIELD_START_X("startX", false),
    FIELD_START_Y("startY", false),
    FIELD_MID_X("midX", false),
    FIELD_MID_Y("midY", false),
    FIELD_END_X("endX", false),
    FIELD_END_Y("endY", false),
    FIELD_CENTRE_X("centreX", false),
    FIELD_CENTRE_Y("centreY", false),
    FIELD_CURVATURE_RATIO("curvatureRatio", false),
    FIELD_ALPHA("alpha", false),
    FIELD_ARC_LENGTH("arcLength", false);

    private String field;
    private boolean isFK;

    private CurveDBFieldsEnum(String field, boolean isFK) {
        this.field = field;
        this.isFK = isFK;
    }

    private CurveDBFieldsEnum(String field) {
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
