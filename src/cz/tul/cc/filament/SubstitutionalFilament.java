package cz.tul.cc.filament;

import cz.tul.cc.point.Point;
//import cz.tul.cc.sscurve.SSCurve;
import cz.tul.cc.util.Util;

/** @description je to jak�si abstraktn� filament u kter�ho zn�me:
 * po��te�n� a koncov� bod,
 * d�lku
 * a do kter�ch gravur je p�ipojen */
public class SubstitutionalFilament {

    @SuppressWarnings("unused")
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SubstitutionalFilament.class.getName());

    public SubstitutionalFilament(int startGravureId, int endGravureId,
            double length, Point startPoint, Point endPoint) {
        super();
        this.startGravureId = startGravureId;
        this.endGravureId = endGravureId;
        this.length = length;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /** @description id's of joined gravures
     * id's are from Pegas List<Gravur> gravures */
    private int startGravureId, endGravureId;

    /** @description length of filament */
    private double length;

    /** @description start and end Point of filament,
     * these Points are changing its position during computation */
    private Point startPoint, endPoint;

    /** ************************************************************ */
    /** General public methods ************************************ */
    /** ************************************************************ */
    public double[] getDirectionVectorFromStart() {
        return _getDirectionVector();
    }

    public double[] getDirectionVectorFromEnd() {
        return _inverseVector(_getDirectionVector());
    }

    /** ************************************************************ */
    /** private supplementary methods ***************************** */
    /** ************************************************************ */
    /** @description normalized direction vector
     * @return normalized direction vector as array of x, y, .. coordinates */
    private double[] _getDirectionVector() {
        double[] dv = new double[Util.dimension];
        double[] ndv = new double[Util.dimension];
        double[] sign = new double[Util.dimension];

        double square = 0.0;

        for (int i = 0; i < Util.dimension; i++) {
            dv[i] = endPoint.getCoordinates()[i] - startPoint.getCoordinates()[i];
            sign[i] = dv[i] >= 0.0 ? 1 : -1;
            square += dv[i] * dv[i];
        }
        for (int i = 0; i < Util.dimension; i++) {
            ndv[i] = Math.sqrt((dv[i] * dv[i]) / square) * sign[i];
        }

        return ndv;
    }

    /** @description inverse vector
     * multiply each coordinate by -1 */
    private double[] _inverseVector(double[] vec) {
        double[] iv = new double[vec.length];
        for (int i = 0; i < vec.length; i++) {
            iv[i] = -1 * vec[i];
        }
        return iv;
    }

    /** ************************************************************ */
    /** General getters and setters ******************************* */
    /** ************************************************************ */
    public int getStartGravureId() {
        return startGravureId;
    }

    public int getEndGravureId() {
        return endGravureId;
    }

    public double getLength() {
        return length;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

}
