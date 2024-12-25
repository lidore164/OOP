package homework1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
	
	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	
	private final ArrayList<GeoSegment> geoSegments;
	private final double length;

	//Abstraction Function:
	//let gf be geographic feature and obj be GeoFeature object:
	//gf.geoSegment = obj.geoSegment
	//gf.start = obj.geoSegment[0]
	//gf.startHeading = obj.geoSegment[0].getHeading()
	//gf.end = obj.geoSegment[-1]
	//gf.endHeading = obj.geoSegment[-1].getHeading()

	//Rep Invariant:
	//obj.length = sum this.geoSegment[i].getLength
	//this.geoSegment[i] != null
	//this.GeoSegment[i].getP2 == this.GeoSegment[i+1].getP1
	//this.geoSegment[i].getName == this.geoSegment[j].getName for every i != j

	
	/**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs) {
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
		  String name = geoSegments.get(0).getName(); // all elements in geoSegment must have the same name
		  for (int i = 0; i < geoSegments.size(); i++){
			  gs = geoSegments.get(i);
			  if (gs == null){
				  throw new RuntimeException("all elements of geoSegment must be != null");
			  }
			  if (!gs.getName().equals(name)){
				  throw new RuntimeException("all geoSegment elements must have the same name");
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
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
  		checkRep();
		  return this.geoSegments.get(0).getName();
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
  		checkRep();
		  return this.geoSegments.get(0).getP1();
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
  		checkRep();
		  return this.geoSegments.get(geoSegments.size()-1).getP2();
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in   0 <= degrees < 360. if degrees = 360 start and end point are the same point
	 *         and the direction will not change.
     */
  	public double getStartHeading() {
  		checkRep();
		  return this.geoSegments.get(0).getHeading();
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in  0 <= degrees < 360. if degrees = 360 start and end point are the same point.
     */
  	public double getEndHeading() {
  		checkRep();
		  return this.geoSegments.get(geoSegments.size()-1).getHeading();
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
  		checkRep();
		  return this.length;
  	}


  	/**
   	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) {
  		ArrayList<GeoSegment> newGeoSegment = new ArrayList<GeoSegment>(this.geoSegments);
		  newGeoSegment.add(gs);
		  checkRep();
		  return new GeoFeature(newGeoSegment, this.length + gs.getLength());
  	}

	  //
	  private GeoFeature (ArrayList<GeoSegment> geoSegments, double length){
		  this.geoSegments = new ArrayList<GeoSegment>(geoSegments);
		  this.length = length;
		  checkRep();
	  }

  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
  	public Iterator<GeoSegment> getGeoSegments() {
  		checkRep();
		  return this.geoSegments.iterator();
  	}


  	/**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o) {
  		if (o != null){
			  if (o instanceof GeoFeature){
				  Iterator<GeoSegment> thisIter = this.getGeoSegments();
				  Iterator<GeoSegment> objIter = ((GeoFeature)o).getGeoSegments();
				  while (thisIter.hasNext() && objIter.hasNext()){
					  if (!thisIter.next().equals(objIter.next())){
						  return false;
					  }
				  }
				  //make sure none of the lists has more elements
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
    	// improved performance.
    	
    	return 1;
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
  		return this.getName();
  	}
}
