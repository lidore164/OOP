package homework1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {

	
 	//Abstraction Function:
	//let r be real routh and obj be Routh object:
	//gf.geoSegment = obj.geoSegment
	//gf.start = obj.geoSegment[0]
	//gf.startHeading = obj.geoSegment[0].getHeading()
	//gf.end = obj.geoSegment[-1]
	//gf.endHeading = obj.geoSegment[-1].getHeading()
	//all segments with the same name will go into one feature,

	//Rep Invariant:
	//obj.length = sum this.geoSegment[i].getLength
	//this.geoSegment[i] != null
	//this.GeoSegment[i].getP2 == this.GeoSegment[i+1].getP1
	//this.geoSegment[i].getName == this.geoSegment[j].getName for every i != j

	private final ArrayList<GeoSegment> geoSegments;
	private final double length;


  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) {
  		geoSegments = new ArrayList<GeoSegment>();
		  geoSegments.add(gs);
		  length = gs.getLength();
		  checkRep();
  	}

	  private void checkRep() throws RuntimeException{
		  double totalLength = 0;
		  GeoSegment gs;
		  if (geoSegments == null){
			  throw new RuntimeException("geoSegment can't get null argument");
		  }
		  for (int i = 0; i < geoSegments.size(); i++){
			  gs = geoSegments.get(i);
			  if (gs == null) {
				  throw new RuntimeException("all elements of geoSegment must be != null");
			  }
			  //check if endpoint of current segment equals to start point of next segment
			  if ((i > geoSegments.size() -1) && (!gs.getP2().equals(geoSegments.get(i+1).getP1()))){
				  throw new RuntimeException("all segments must have end point equals to start point of follow segment");
			  }

			  totalLength += gs.getLength();
		  }
		  if (this.length != totalLength){
			  throw new RuntimeException("length is not equal to sum of lengths in geoSegment list");
		  }
	  }


    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		checkRep();
		  return this.geoSegments.get(0).getP1();
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		checkRep();
		  return this.geoSegments.get(geoSegments.size()-1).getP2();
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in  0 <= degrees < 360. if degrees = 360 start and end point are the same point.
   	 **/
  	public double getStartHeading() {
  		checkRep();
		  return this.geoSegments.get(0).getHeading();
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in  0 <= degrees < 360. if degrees = 360 start and end point are the same point.
     **/
  	public double getEndHeading() {
  		checkRep();
		  return this.geoSegments.get(geoSegments.size()-1).getHeading();
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		checkRep();
		  return this.length;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
		ArrayList<GeoSegment> newGeoSegment = new ArrayList<GeoSegment>(this.geoSegments);
		newGeoSegment.add(gs);
		checkRep();
		return new Route(newGeoSegment, this.length + gs.getLength());
  	}

	private Route (ArrayList<GeoSegment> geoSegments, double length){
		this.geoSegments = new ArrayList<GeoSegment>(geoSegments);
		this.length = length;
		checkRep();
	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
		  //need to create geoFeature list out of geoSegments.
		//will go over all geoSegment, every series of geoSegment with same name will add to same geoFeature
  		checkRep();
		  ArrayList<GeoFeature> geoFeatures = new ArrayList<GeoFeature>();
		  Iterator<GeoSegment> segmentIter = this.geoSegments.iterator();
		  //set first geoFeature with the first geoSegment
		GeoFeature gf = new GeoFeature(segmentIter.next());
		GeoSegment gs;

		while (segmentIter.hasNext()){
			gs = segmentIter.next();
			if (gs.getName() == gf.getName()){
				//current segment belong to this feature
				gf = gf.addSegment(gs);
			}
			else{
				//end of feature and start new one
				geoFeatures.add(gf);
				gf = new GeoFeature(gs);
			}
		}
		geoFeatures.add(gf); // add last gioFeature to list
		checkRep();
		return geoFeatures.iterator();
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
		  return this.geoSegments.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		if (o != null){
			  if (o instanceof Route) {
				  Iterator<GeoFeature> thisIter = this.getGeoFeatures();
				  Iterator<GeoFeature> objIter = ((Route) o).getGeoFeatures();
				  while (thisIter.hasNext() && objIter.hasNext()) {
					  if (!thisIter.next().equals(objIter.next())) {
						  return false;
					  }
				  }
				  //both Routs don't have same amount of objects
				  return !thisIter.hasNext() && !objIter.hasNext();
			  }
		}
		return false;
  	}


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
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
		//represent Routh by the names of all its geoFeature
		Iterator<GeoFeature> iter = this.getGeoFeatures();
		String str = "[";
		while (iter.hasNext()){
			str += iter.next().toString() + ", ";
		}
		//delete last ','
		str = str.substring(0, str.lastIndexOf(',')) + "]";
		return str;
  	}

}
