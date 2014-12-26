package cz.tul.cc.db.curve;

/** ************************************************************************
 *
 * @author wojta
 */
public class CurveBean {

    private int id;
    private double startX;
    private double startY;
    private double midX;
    private double midY;
    private double endX;
    private double endY;
    private double centreX;
    private double centreY;
    private double alpha;
    private double curvatureRatio;
    private double arcLength;

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

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getMidX() {
        return midX;
    }

    public void setMidX(double midX) {
        this.midX = midX;
    }

    public double getMidY() {
        return midY;
    }

    public void setMidY(double midY) {
        this.midY = midY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
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

    public double getCentreX() {
        return centreX;
    }

    public void setCentreX(double centreX) {
        this.centreX = centreX;
    }

    public double getCentreY() {
        return centreY;
    }

    public void setCentreY(double centreY) {
        this.centreY = centreY;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public String toString() {
        return "CurveBean{" + "id=" + id + ", startX=" + startX + ", startY=" + startY + ", midX=" + midX + ", midY=" + midY + ", endX=" + endX + ", endY=" + endY + ", centreX=" + centreX + ", centreY=" + centreY + ", alpha=" + alpha + ", curvatureRatio=" + curvatureRatio + ", arcLength=" + arcLength + '}';
    }

    
    
}
