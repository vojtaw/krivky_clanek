package cz.tul.cc.db.result;

/** ************************************************************************
 *
 * @author wojta
 */
public class ResultBean {

    private int id;
    private int curveId;
    private double curvatureRatio;
    private double arcLength;
    private int discretizationParts;
    private double linearLength;
    private double bezierLength;

    /** ******************************************************************** */
    /** public methods ***************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** private methods **************************************************** */
    /** ******************************************************************** */
    /** ******************************************************************** */
    /** public getters and setters ***************************************** */
    /** ******************************************************************** */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurveId() {
        return curveId;
    }

    public void setCurveId(int curveId) {
        this.curveId = curveId;
    }

    public double getCurvatureRatio() {
        return curvatureRatio;
    }

    public void setCurvatureRatio(double curvatureRatio) {
        this.curvatureRatio = curvatureRatio;
    }

    public double getArcLength() {
        return arcLength;
    }

    public void setArcLength(double arcLength) {
        this.arcLength = arcLength;
    }

    public int getDiscretizationParts() {
        return discretizationParts;
    }

    public void setDiscretizationParts(int discretizationParts) {
        this.discretizationParts = discretizationParts;
    }

    public double getLinearLength() {
        return linearLength;
    }

    public void setLinearLength(double linearLength) {
        this.linearLength = linearLength;
    }

    public double getBezierLength() {
        return bezierLength;
    }

    public void setBezierLength(double bezierLength) {
        this.bezierLength = bezierLength;
    }

}
