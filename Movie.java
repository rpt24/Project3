/************************************************************************* 
  Student Name:      Ryan Thornton
  File Name:         Movie.java
  Assignment number: Project3

  Movie class. This is the object that is stored in the DataList objects
  in the hash table.
**************************************************************************/

/**
 * Movie class
 *
 */
public class Movie
{
	String title; // title of movie
	String lead;  // lead actor/actress
	String desc;  // description of movie
	int year;     // year movie was released
	Movie next;	  // give the movie a next object for linking it in the DataList
	
	/**
	 * Movie Constructor
	 * @param movieTitle - title of movie from input
	 * @param movieLead - lead of movie from input
	 * @param movieDesc - description of movie from input
	 * @param movieYear - year of movie from input
	 */
	public Movie(String movieTitle, String movieLead, String movieDesc, int movieYear)
	{
		title = movieTitle;
		lead = movieLead;
		desc = movieDesc;
		year = movieYear;
		next = null; // this is initially null until it's linked in a DataList
	}
	
	/**
	 * This is used for printing the movie data.
	 */
	public void imageMovie()
	{
		System.out.format("Title: %s\n", title);
		System.out.format("Lead Actor/Actress: %s\n", lead);
		System.out.format("Description: %s\n", desc);
		System.out.format("Year release: %d\n\n", year);
	}
}