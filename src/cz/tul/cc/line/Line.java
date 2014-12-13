package cz.tul.cc.line;


import math.geom2d.Point2D;
import math.geom2d.line.StraightLine2D;
import cz.tul.cc.point.Point;

public abstract class Line {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Line.class.getName());

	/**begin and end coordinates of line */
	protected Point start, end;
	protected Point intersectionPoint;
	
    /** @description Direction vector of the line. dx and dy should not be both zero. */
    protected double dx, dy, dz;
	
    /** @description  Normalized vector of the line. dx and dy should not be both zero. */
    protected double ex, ey, ez;
    
	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
		intersectionPoint = null;
		
		setDirectionVector();
		setNormalizedVector();
	}

	public Line(double x1, double y1, double x2, double y2) {
		this.start = new Point(x1, y1);
		this.end = new Point(x2, y2);
		intersectionPoint = null;
		
		setDirectionVector();
		setNormalizedVector();
	}
	
	/**@description creates a new line from starting point with direction vector
	 * 
	 * @param start - starting point of new line
	 * @param dx - direction vector
	 * @param dy - direction vector
	 */
	public Line(Point start, double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
		this.start = start;
		this.end = new Point(this.start.getX() + dx, this.start.getY() + dy);
		intersectionPoint = null;

		setNormalizedVector();
	}

	/**@return true if this line intersects to line */
	/** description at: http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/ */
	public abstract boolean intersects(Line line);
	
	/**@description sets the direction vector for this line */
	protected abstract void setDirectionVector();
	
	/**@description sets the normalized vector for this line */
	protected abstract void setNormalizedVector();
	
	/**@description Create a straight line perpendicular to this object,
	 * and going through the given point.
	 * 
     * Prost� kolmice proch�zej�c� bodem point
     * 
     * @param point the point to go through
     * @return the perpendicular through the point
     */
    public abstract Line perpendicular(Point point);
	
	/***************************************************************/
	
	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public Point getIntersectionPoint() {
		return intersectionPoint;
	}

	public double getEx() {
		return ex;
	}

	public double getEy() {
		return ey;
	}

	public double getEz() {
		return ez;
	}
	
	
}
