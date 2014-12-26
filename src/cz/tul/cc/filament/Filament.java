package cz.tul.cc.filament;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import cz.tul.cc.bezier.Curve;
import cz.tul.cc.bezier.CurveSegment;
import cz.tul.cc.bezier.MatrixConstants;
import cz.tul.cc.point.Point;
import cz.tul.cc.util.Util;

public class Filament extends Curve {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Filament.class.getName());
    private double[][] sPoints;

    public Filament() {
        // TODO Auto-generated constructor stub
    }

    /** Vytvori krivku ze segmentu, danych body S */
    public Filament(double[][] sPoints) {
        this.sPoints = new double[sPoints.length][Util.dimension];
        for (int i = 0; i < sPoints.length; i++) {
            for (int d = 0; d < Util.dimension; d++) {
                this.sPoints[i][d] = sPoints[i][d];
            }
        }

        try {
            makeSegmentation();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        boxRectangle = boundingBoxRectangle();
    }

    /** Vytvori krivku ze segmentu, danych body S */
    public Filament(List<Point> sPoints) {
        this.sPoints = new double[sPoints.size()][Util.dimension];
        for (int i = 0; i < sPoints.size(); i++) {
            /** TODO ud�lat to 3D */
            this.sPoints[i][0] = sPoints.get(i).getX();
            this.sPoints[i][1] = sPoints.get(i).getY();
        }

        try {
            makeSegmentation();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        boxRectangle = boundingBoxRectangle();
    }

    /** ******************************************************** */
    /** General public methods ******************************** */
    /** ******************************************************** */
    /** @description normalized direction vector
     * @return normalized direction vector as array of x, y, .. coordinates */
    public double[] getDirectionVector() {
        double[] dv = new double[Util.dimension];
        double[] ndv = new double[Util.dimension];
        double[] sign = new double[Util.dimension];
        /** @description start point of filament is the start point of first segment */
        Point start = new Point(curve.get(0).getStartPoint());
        /** @description end point of filament is the end point of last segment */
        Point end = new Point(curve.get(curve.size() - 1).getEndPoint());

        double square = 0.0;

        for (int i = 0; i < Util.dimension; i++) {
            dv[i] = end.getCoordinates()[i] - start.getCoordinates()[i];
            sign[i] = dv[i] >= 0.0 ? 1 : -1;
            square += dv[i] * dv[i];
        }
        for (int i = 0; i < Util.dimension; i++) {
            ndv[i] = Math.sqrt((dv[i] * dv[i]) / square) * sign[i];
        }

        return ndv;
    }

    /** ************************************************************ */
    /** private supplementary methods ***************************** */
    /** ************************************************************ */
    private void makeSegmentation() throws Exception {
        int len = sPoints.length;
//		logger.info("len:"+len);
        if (len < 4) {
            throw new Exception("M�n ne� 4 S pointy");
        } else if (len == 4) {
            makeSegment4();
        } else if (len == 5) {
            makeSegment5(sPoints);
        } else {
            int sec = len - 5;
            double[][] segmentSPoints = new double[5][Util.dimension];
            for (int i = 0; i < sec; i++) {
                segmentSPoints = new double[5][Util.dimension];
                segmentSPoints = getSegmentSPoints(i);
                makeSegment(segmentSPoints);
            }
            segmentSPoints = new double[5][Util.dimension];
            segmentSPoints = getSegmentSPoints(sec);
            makeSegment5(segmentSPoints);
        }

    }

    private void makeSegment(double[][] sPoints) {
        /** vytvori 1. segment pro peti bodovou krivku */
        /** kdyz mame jen 4 body, delaji se segmenty vsechny najednou
         * krivka ma 3 segmenty S0-S1, S1-S2, S2-S3;
         * S0=B0, S3=B3 */
        double[][] cMatrixData = createCMatrix(sPoints);

        RealMatrix cMatrix = new Array2DRowRealMatrix(cMatrixData);
        RealMatrix bMatrix = MatrixConstants.M3.multiply(cMatrix);

        double[][] bPoints = bMatrix.scalarMultiply(MatrixConstants.DM3).getData();

        makeFirstSegment(bPoints, sPoints);

    }

    private void makeSegment4() {
        /** kdyz mame jen 4 body, delaji se segmenty vsechny najednou
         * krivka ma 3 segmenty S0-S1, S1-S2, S2-S3;
         * S0=B0, S3=B3 */
//		logger.info("makeSegment4");
        double[][] cMatrixData = createCMatrix(sPoints);

        RealMatrix cMatrix = new Array2DRowRealMatrix(cMatrixData);
        RealMatrix bMatrix = MatrixConstants.M2.multiply(cMatrix);

        double[][] bPoints = bMatrix.scalarMultiply(MatrixConstants.DM2).getData();

        double[][] curveBPoints = new double[4][Util.dimension];

        makeFirstSegment(bPoints, sPoints);

        for (int i = 1; i < 2; i++) {
            /** B0=S0 */
            curveBPoints[0] = copyCoordinates(sPoints[i]);

            /** B1=2/3B0 + 1/3B3 */
            for (int j = 0; j < Util.dimension; j++) {
                curveBPoints[1][j] = (2.0 / 3.0) * bPoints[i - 1][j] + (1.0 / 3.0) * bPoints[i][j];
            }

            /** B2=1/3B0 + 2/3B3 */
            for (int j = 0; j < Util.dimension; j++) {
                curveBPoints[2][j] = (1.0 / 3.0) * bPoints[i - 1][j] + (2.0 / 3.0) * bPoints[i][j];
            }

            /** B3=S1 */
            curveBPoints[3] = copyCoordinates(sPoints[i + 1]);

            try {
                CurveSegment cs = new CurveSegment(curveBPoints);
                curve.add(cs);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        makeLastSegment(bPoints, sPoints);
    }

    private void makeSegment5(double[][] sPoints) {
        /** kdyz mame 5 body, delaji se segmenty vsechny najednou
         * krivka ma 4 segmenty 1:S0-S1, 2:S1-S2, 3:S2-S3, 4:S3-S4;
         * S0=B0, S4=B4
         * maticove: B = M-1 C 
         * Pro i-tou krivku jsou ridici body:
         * Si-1, 2/3Bi-1 + 1/3Bi, 1/3Bi-1 + 2/3Bi, Si
         */

//		logger.info("makeSegment5");
        double[][] cMatrixData = createCMatrix(sPoints);

        RealMatrix cMatrix = new Array2DRowRealMatrix(cMatrixData);
        RealMatrix bMatrix = MatrixConstants.M3.multiply(cMatrix);

        double[][] bPoints = bMatrix.scalarMultiply(MatrixConstants.DM3).getData();

        double[][] curveBPoints = new double[4][Util.dimension];

        /**prvni segment 1:S0-S1 */
        makeFirstSegment(bPoints, sPoints);

//		logger.info("First segment made");
        /**vnitrni segmenty 2:S1-S2 a 3:S2-S3 
         * 2: S1, 2/3B0 + 1/3B1, 1/3B0 + 2/3B1, S2
         * 3: S2, 2/3B1 + 1/3B2, 1/3B1 + 2/3B2, S3
         */
        for (int i = 2; i <= 3; i++) {
            /** B0=S0 */
            curveBPoints[0] = copyCoordinates(sPoints[i-1]);

            /** B1=2/3B0 + 1/3B3 */
            for (int j = 0; j < Util.dimension; j++) {
                curveBPoints[1][j] = (2.0 / 3.0) * bPoints[i-2][j] + (1.0 / 3.0) * bPoints[i-1][j];
            }

            /** B2=1/3B0 + 2/3B3 */
            for (int j = 0; j < Util.dimension; j++) {
                curveBPoints[2][j] = (1.0 / 3.0) * bPoints[i-2][j] + (2.0 / 3.0) * bPoints[i-1][j];
            }

            /** B3=S1 */
            curveBPoints[3] = copyCoordinates(sPoints[i]);

            try {
                CurveSegment cs = new CurveSegment(curveBPoints);
                curve.add(cs);
//				logger.info("Vevnit� segment made");
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        makeLastSegment(bPoints, sPoints);
//		logger.info("Last segment made");
    }

    private void makeFirstSegment(double[][] bPoints, double[][] sPoints) {
        /** ******************************************************************* */
        /** make prvni segment  
         * S0==B0
         S0, 2/3S0 + 1/3B0, 1/3S0 + 2/3B0, S1*/
        double[][] curveBPoints = new double[4][Util.dimension];

        /** B0=S0 */
        curveBPoints[0] = copyCoordinates(sPoints[0]);

        /** B1=2/3B0 + 1/3B3 */
        for (int j = 0; j < Util.dimension; j++) {
            curveBPoints[1][j] = (2.0 / 3.0) * sPoints[0][j] + (1.0 / 3.0) * bPoints[0][j];
        }

        /** B2=1/3B0 + 2/3B3 */
        for (int j = 0; j < Util.dimension; j++) {
            curveBPoints[2][j] = (1.0 / 3.0) * sPoints[0][j] + (2.0 / 3.0) * bPoints[0][j];
        }

        /** B3=S1 */
        curveBPoints[3] = copyCoordinates(sPoints[1]);

        try {
            CurveSegment cs = new CurveSegment(curveBPoints);
            curve.add(cs);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    private void makeLastSegment(double[][] bPoints, double[][] sPoints) {
        /** ******************************************************************* */
        /** make posledni (4) segment   
         * S4==B4
         S3, 2/3B2 + 1/3S4, 1/3B2 + 2/3S4, S4*/

//		logger.info("makeLastSegment");
        double[][] curveBPoints = new double[4][Util.dimension];

        /** B0=S0 */
        curveBPoints[0] = copyCoordinates(sPoints[sPoints.length - 2]);

        /** B1=2/3B0 + 1/3B3 */
        for (int j = 0; j < Util.dimension; j++) {
            curveBPoints[1][j] = (2.0 / 3.0) * bPoints[bPoints.length-1][j] + (1.0 / 3.0) * sPoints[sPoints.length - 1][j];
        }

        /** B2=1/3B0 + 2/3B3 */
        for (int j = 0; j < Util.dimension; j++) {
            curveBPoints[2][j] = (1.0 / 3.0) * bPoints[bPoints.length - 1][j] + (2.0 / 3.0) * sPoints[sPoints.length - 1][j];
        }

        /** B3=S1 */
//		logger.info("sPoints.length:"+sPoints.length);
        curveBPoints[3] = copyCoordinates(sPoints[sPoints.length - 1]);

        try {
            CurveSegment cs = new CurveSegment(curveBPoints);
            curve.add(cs);
//			logger.info("makeLastSegment p�id�no?");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    private double[][] getSegmentSPoints(int from) {
        double[][] sPoints = new double[5][Util.dimension];
        for (int i = 0; i < 5; i++) {
            sPoints[i] = copyCoordinates(this.sPoints[i + from]);
        }

        return sPoints;
    }

    private double[] copyCoordinates(double[] from) {
        double[] kam = new double[Util.dimension];
        for (int i = 0; i < Util.dimension; i++) {
            kam[i] = from[i];
        };

        return kam;
    }

    

    private double[][] createCMatrix(double[][] sPoints) {
        int rows = sPoints.length - 1;
        int cols = sPoints[0].length;
        /** could be always 2 */
        double[][] c = new double[rows - 1][cols];
//		logger.info("rows:"+rows+", cols:"+cols);

        /** 6*S1-S0 */
        c[0][0] = 6 * sPoints[1][0] - sPoints[0][0];
        c[0][1] = 6 * sPoints[1][1] - sPoints[0][1];

        /** vsechno mezi je 6*Sn-2 */
        for (int i = 1; i < rows - 2; i++) {
            c[i][0] = 6 * sPoints[i + 1][0];
            c[i][1] = 6 * sPoints[i + 1][1];
        }

        /** (6*Sn-1)-(Sn) */
        c[rows - 2][0] = 6 * sPoints[rows - 1][0] - sPoints[rows][0];
        c[rows - 2][1] = 6 * sPoints[rows - 1][1] - sPoints[rows][1];

        return c;
    }

    /** ******************************************************** */
    /** General getters and setters *************************** */
    /** ******************************************************** */
    public double[][] getSPoints() {
        return sPoints;
    }

}
