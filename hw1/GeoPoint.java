package homework1;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv center is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The 
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv center, use:
 * <tt>GeoPoint zivCenter = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint {

	/** Minimum value the latitude field can have in this class. **/
	public static final int MIN_LATITUDE  =  -90 * 1000000;
	    
	/** Maximum value the latitude field can have in this class. **/
	public static final int MAX_LATITUDE  =   90 * 1000000;
	    
	/** Minimum value the longitude field can have in this class. **/
	public static final int MIN_LONGITUDE = -180 * 1000000;
	    
	/** Maximum value the longitude field can have in this class. **/
	public static final int MAX_LONGITUDE =  180 * 1000000;

  	/**
   	 * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LATITUDE = 110.901;

  	/**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LONGITUDE = 93.681;

	  private final int latitude;
	  private final int longitude;

  	
  	//Abstraction Function:
	//GeoPoint representing point on earth in millionths degrees.
	//if a representing latitude and b representing longitude on earth
	//GeoPoint of this point will be (a/1000000, b/1000000)

	//Rep Invariant:
	//MIN_LATITUDE <= GeoPoint.latitude <= MAX_LATITUDE
	//MIN_LONGITUDE <= GeoPoint.longitude <= MAX_LONGITUDE
  	
  	
  	/**
  	 * Constructs GeoPoint from a latitude and longitude.
     * @requires the point given by (latitude, longitude) in millionths
   	 *           of a degree is valid such that:
   	 *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * 	 		 (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
   	 * @effects constructs a GeoPoint from a latitude and longitude
     *          given in millionths of degrees.
   	 **/
  	public GeoPoint(int latitude, int longitude) {
  		this.latitude = latitude;
		  this.longitude = longitude;

		  checkRep();
  	}

	  private void checkRep() throws RuntimeException{
		  if (MIN_LATITUDE > this.latitude || this.latitude > MAX_LATITUDE){
			  throw new RuntimeException("Latitude is out of range");
		  }
		  if(MIN_LONGITUDE > this.longitude || this.longitude > MAX_LONGITUDE){
			  throw new RuntimeException("Longitude is out of range");
		  }
	  }

  	 
  	/**
     * Returns the latitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLatitude() {
		  checkRep();
  		return this.latitude;
  	}


  	/**
     * Returns the longitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLongitude() {
  		checkRep();
		  return this.longitude;
  	}


  	/**
     * Computes the distance between GeoPoints.
     * @requires gp != null
     * @return the distance from this to gp, using the flat-surface, near
     *         the Technion approximation.
     **/
  	public double distanceTo(GeoPoint gp) {
		  checkRep();
  		double latitudeDist, longitudeDist, dist;
		  latitudeDist = (this.latitude - gp.latitude) * KM_PER_DEGREE_LATITUDE / 1000000;
		  longitudeDist = (this.longitude - gp.longitude) * KM_PER_DEGREE_LONGITUDE / 1000000;
		  dist = Math.sqrt((latitudeDist * latitudeDist) + (longitudeDist * longitudeDist));
		  checkRep();
		  return dist;
  	}



  	/**
     * Computes the compass heading between GeoPoints.
     * @requires gp != null && !this.equals(gp)
     * @return the compass heading h from this to gp, in degrees, using the
     *         flat-surface, near the Technion approximation, such that
     *         0 <= h < 360. In compass headings, north = 0, east = 90,
     *         south = 180, and west = 270.
     **/
  	public double headingTo(GeoPoint gp) {

		double latitudeDist, longitudeDist;
		checkRep();
		latitudeDist = (gp.latitude - this.latitude) * KM_PER_DEGREE_LATITUDE / 1000000;
		longitudeDist = (gp.longitude - this.longitude) * KM_PER_DEGREE_LONGITUDE / 1000000;
		double heading = Math.toDegrees(Math.atan2(longitudeDist, latitudeDist));
		if (heading < 0){
			heading += 360;
		}
		checkRep();
		return heading;
  	}


  	/**
     * Compares the specified Object with this GeoPoint for equality.
     * @return gp != null && (gp instanceof GeoPoint) &&
     * 		   gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
  	public boolean equals(Object gp) {
  		checkRep();
		  GeoPoint point;
		  if (gp != null){
			  if (gp instanceof GeoPoint){
				  point = (GeoPoint) gp;
				  return (this.latitude == point.latitude) && (this.longitude == point.longitude);
			  }
		  }
		  return false;
  	}


  	/**
     * Returns a hash code value for this GeoPoint.
     * @return a hash code value for this GeoPoint.
   	 **/
  	public int hashCode() {
    	// This implementation will work, but you may want to modify it
    	// for improved performance.

    	return 1;
  	}


  	/**
     * Returns a string representation of this GeoPoint.
     * @return a string representation of this GeoPoint.
     **/
  	public String toString() {
  		return ("(" + this.latitude + ", " + this.longitude + ")");
  	}

}
