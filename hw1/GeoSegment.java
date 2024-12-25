package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments 
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Khanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Khanita", i, j), and ("Khanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {

	private final String name;
	private final GeoPoint p1;
	private final GeoPoint p2;

	//Abstraction Function:
	//for straight line on earth (e) and GeoSegment (g),
	//p1 represent start point and p2 represent end point of the line:
	//e.name = g.name, e.p1 = g.p1, e.p2 = g.p2

	//Rep Invariant:
	//0 <= heading < 360
	//length >= 0
	//p1, p2, name != null
	
  	/**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
  	public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
  		this.name = name;
		  this.p1 = p1;
		  this.p2 = p2;
		  checkRep();
  	}

	/**
	 * make sure that all the variables in the class get values in acceptable range.
	 * throws RuntimeException
	 */
	public void checkRep() throws RuntimeException{
		  if (this.name == null){
			  throw new RuntimeException("name can't get null argument");
		  }
		  if (this.p1 == null){
			  throw new RuntimeException("p1 can't get null argument");
		  }
		  if (this.p2 == null){
			  throw new RuntimeException("p2 can't get null argument");
		  }
		  if (this.p1.distanceTo(this.p2) < 0){
			  throw new RuntimeException("length must be >=0");
		  }
		  if ((this.p1.headingTo(this.p2) < 0) || this.p1.headingTo(this.p2)>=360){
			  throw new RuntimeException("heading must be between 0 and 360");
		  }
	  }
  	/**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * @return a new GeoSegment gs such that gs.name = this.name
     *         && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
  	public GeoSegment reverse() {
  		checkRep();
		  return new GeoSegment(this.name, this.p2, this.p1);
  	}


  	/**
  	 * Returns the name of this GeoSegment.
     * @return the name of this GeoSegment.
     */
  	public String getName() {
  		checkRep();
		  return this.name;
  	}


  	/**
  	 * Returns first endpoint of the segment.
     * @return first endpoint of the segment.
     */
  	public GeoPoint getP1() {
  		checkRep();
		  return this.p1;
  	}


  	/**
  	 * Returns second endpoint of the segment.
     * @return second endpoint of the segment.
     */
  	public GeoPoint getP2() {
  		checkRep();
		  return this.p2;
  	}


  	/**
  	 * Returns the length of the segment.
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
  	public double getLength() {
  		checkRep();
		  return p1.distanceTo(p2);
  	}


  	/**
  	 * Returns the compass heading from p1 to p2.
     * @requires nothing
     * @return the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation. if p1.equals(p2)
	 *         (e.g. start and end point are the same point and length = 0), method will return 360
     **/
  	public double getHeading() {
  		checkRep();
		  if (this.getLength() == 0){
			  return 360;
		  }
		  return p1.headingTo(p2);
  	}


  	/**
     * Compares the specified Object with this GeoSegment for equality.
     * @return gs != null && (gs instanceof GeoSegment)
     *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
   	 **/
  	public boolean equals(Object gs) {
  		if (gs != null){
			  if (gs instanceof GeoSegment segment){
				  return (this.name.equals(segment.name) && this.p1.equals(segment.p1) && this.p2.equals(segment.p2));
			  }
		}
		  return false;
  	}


  	/**
  	 * Returns a hash code value for this.
     * @return a hash code value for this.
     **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it 
    	// for improved performance. 

    	return 1;
  	}


  	/**
  	 * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		return "[" + this.name + ", " + this.p1.toString() + ", " + this.p2.toString() + "]";
  	}

}

