package cz.tul.cc.line;


import math.geom2d.Point2D;
import cz.tul.cc.bezier.Constants;
import cz.tul.cc.point.Point;
import cz.tul.cc.util.Util;

public class Line2D extends Line{
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Line2D.class.getName());

	public Line2D(Point start, Point end) {
		super(start, end);
	}

	public Line2D(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}

	public Line2D(Point start, double dx, double dy){
		super(start, dx, dy);
	}
	
	/**return true if this line intersects to line */
	/** description at: http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/ */
	public boolean intersects(Line line){
		//if(true)return false;
		
		double denom = 	(line.getEnd().getY() - line.getStart().getY()) * (this.getEnd().getX() - this.getStart().getX()) -
						(line.getEnd().getX() - line.getStart().getX()) * (this.getEnd().getY() - this.getStart().getY());
		double ua = 	(line.getEnd().getX() - line.getStart().getX()) * (this.getStart().getY() - line.getStart().getY()) -
						(line.getEnd().getY() - line.getStart().getY()) * (this.getStart().getX() - line.getStart().getX());
		double ub = 	(this.getEnd().getX() - this.getStart().getX()) * (this.getStart().getY() - line.getStart().getY()) -
						(this.getEnd().getY() - this.getStart().getY()) * (this.getStart().getX() - line.getStart().getX());
		
//		logger.info("denom:"+denom+", ua:"+ua+", ub:"+ub);
		if(denom==0 && ua==0 && ub==0){
			/**lines are coincident 
			 * jako prusecit nastavuju (x1,y1)*/
			logger.info("coincident");
			intersectionPoint = new Point(this.getStart().getX(), this.getStart().getY());
			return true;
		}
		
		if(denom==0){
			/**lines are parallel */
			logger.info("parallel");
//			intersectionPoint = new Point(0.0, 0.0);
			return false;
		}
		ua /= denom;
		ub /= denom;

		if(!(ua>=0 && ua<=1 && ub>=0 && ub<=1)){
			/** nemaji prusecik */
//			logger.info("nemaji prusecik");
			return false;
		}
		
		double x = this.getStart().getX() + ua * (this.getEnd().getX() - this.getStart().getX());
		double y = this.getStart().getY() + ua * (this.getEnd().getY() - this.getStart().getY());
		
		logger.info("denom:"+denom+", ua:"+ua+", ub:"+ub+", x:"+x+", y:"+y);
		
		intersectionPoint = new Point(x, y);
		
		return true;
	}
	
	/**@description sets the direction vector for this line */
	protected void setDirectionVector(){
		dx = end.getX() - start.getX();
		dy = end.getY() - start.getY();
	}
	
	/**@description sets the normalized vector for this line */
	@Override
	protected void setNormalizedVector() {
		double length = Math.sqrt(dx*dx + dy*dy);
        
        ex = dx / length;
        ey = dy / length;
		
	}

	/** @description Create a straight line perpendicular to this object, 
	 * and going through the given point.
	 * 
     * Prost� kolmice proch�zej�c� bodem point
     * 
     * @param point the point to go through
     * @return the perpendicular through the point
     */
    public Line2D perpendicular(Point point) {
        return new Line2D(point, -this.ey, this.ex);
    }
    
    /** convert to string*/
    @Override
    public String toString() {
        return "[" + this.start.toString() + ";" + this.end.toString() +"]";
    }
	
    @Override
    public boolean equals(Object o){
   		if(this==o) return true;
   		if(!(o instanceof Line2D)) return false;
//   		double precision = 1E-32;
    	Line2D l = (Line2D) o;
    	//	return abs(x-other.x)<=0.1 && abs(y-other.y)<=0.1; }
    	
    	if ((Math.abs(this.start.getX() - l.start.getX()) >= Util.doublePrecision) ||
    		(Math.abs(this.start.getY() - l.start.getY()) >= Util.doublePrecision) ||
    		(Math.abs(this.end.getX() - l.end.getX()) >= Util.doublePrecision) ||
    		(Math.abs(this.end.getY() - l.end.getY()) >= Util.doublePrecision)
    		){
    		return false;
    	}
    	return true;
    }

	
	
	/***************************************************************/
	
	
}
