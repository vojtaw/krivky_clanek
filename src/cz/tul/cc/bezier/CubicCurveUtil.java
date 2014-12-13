package cz.tul.cc.bezier;
/**@description basic functions for Bezier Cubic Curve */

public class CubicCurveUtil {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CubicCurveUtil.class.getName());

	/**
	 * Get the angular direction indicated by the provided dx/dy ratio,
	 * with the value expressed in radians (not degrees!)
	 */
	public double getDirection(double dx, double dy){
		return Math.atan2(-dy,-dx) - Math.PI;
	}

	/**
	 * compute the value for the cubic bezier function at time=t
	 */
	public double computeCubicBaseValue(double t, double a, double b, double c, double d) {
		double mt = 1-t;

		return mt*mt*mt*a + 3*mt*mt*t*b + 3*mt*t*t*c + t*t*t*d; 
	}

	/**
	 * compute the value for the first derivative of the cubic bezier function at time=t
	 */
	public double[] computeCubicFirstDerivativeRoots(double a, double b, double c, double d) {
		double[] ret = {-1,-1};
		double tl = -a+2*b-c;
		double tr = -Math.sqrt((float)(-a*(c-d) + b*b - b*(c+d) +c*c));
		double dn = -a+3*b-3*c+d;
		if(dn!=0) { 
			ret[0] = (tl+tr)/dn; 
			ret[1] = (tl-tr)/dn; 
		}

		return ret; 
	}

	/**
	 * compute the value for the second derivative of the cubic bezier function at time=t
	 */
	public double computeCubicSecondDerivativeRoot(double a, double b, double c, double d) {
		double ret = -1;
		double tt = a-2*b+c;
		double dn = a-3*b+3*c-d;
		if(dn!=0) { ret = tt/dn; }

		return ret; 
	}
}
