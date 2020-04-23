/************************************************************************* 
  Student Name:      Ryan Thornton
  File Name:         DataList.java
  Assignment number: Project3

  This is the DataList class. The hash table stores objects of DataList
  in each bucket. The DataList contains object of type Movie 
**************************************************************************/

/**
 * DataList class
 *
 */
public class DataList 
{
	Movie head; // first object in list
	
	/**
	 * Constructor - initializes head to null
	 */
	public DataList()
	{
		head = null;
	}
	
	/**
	 * Insert the movie into the DataList
	 * 
	 * @param m - movie to be inserted
	 */
	public void insertMovie(Movie m)
	{
		if (head == null) { // if head is null we make this Movie the first object
			head = m;
		} else { // else we find the next null space 
			Movie temp = head; // temp object to traverse with
			while (temp.next != null)
			{
				temp = temp.next;
			}
			temp.next = m; // insert new movie into the end of the list
		}
	}
	
	/**
	 * Prints the contents of the list
	 */
	public void printList()
	{
		if (head == null) { // if head is null, nothing to print
			return;
		} else { // else we traverse list until we find the null (last) spot and print each item
			Movie temp = head; // temp object to traverse with
			while (temp != null)
			{
				temp.imageMovie(); // this calls the Movie function imageMovie which prints this Movie objects data
				temp = temp.next;
			}
		}
	}
	
	/**
	 * Search for a movie in this DataList object
	 * 
	 * @param searchTitle - title of movie we want to find
	 */
	public void findListMovie(String searchTitle)
	{
		boolean movieFound = false; // initially movie is not found
		
		if (head == null) { // if head is null then nothing to find at this bucket in the hash table
			System.out.println("Movie not found!\n");
		} else { // else traverse the list looking for the Movie
			Movie temp = head;
			while (temp != null) // while we have Movie object we check if it's the one we want
			{
				if (temp.title.equals(searchTitle)) { // if the title matches, print the data and break from loop
					System.out.println("Movie Found: ");
					temp.imageMovie();
					movieFound = true; // movie is found
					break;
				} else {
					temp = temp.next;
				}
			}
			/* this is for if we traverse the DataList and still didn't find the movie */
			if (movieFound == false) {
				System.out.println("Movie not found!\n");
			}
		}
	}
	
	/**
	 * Deletes a movie from the DataList
	 * 
	 * @param deleteTitle - title of Movie we want to delete
	 */
	public boolean deleteListMovie(String deleteTitle)
	{
		boolean movieDeleted = false; // initially movie is not deleted yet
		
		if (head == null) { // if head is null movie is not found at this bucket in the hash table
			System.out.println("Movie to delete not found!\n");
		} else {
			Movie temp = head; // temp Movie object to traverse with
			if (temp.title.equals(deleteTitle)) { // if the head is the movie delete it
				head = temp.next; // set the head to the next object in the array
				temp.next = null; // disconnect the former head objects
				temp = null;
				System.out.format("Movie %s deleted successfully\n\n", deleteTitle);
				movieDeleted = true; // movie was deleted
			} else { // if the movie isn't the head, we have to traverse to find it
				Movie deleteMovie = temp; // temp object to traverse with, we stop at the object before the movie we want
				while (temp.next != null)
				{
					deleteMovie = temp.next;
					if (deleteMovie.title.equals(deleteTitle)) { // if movie title is found delete it
						temp.next = deleteMovie.next; // sets node before deleted node next to node after deleted node
						deleteMovie.next = null; // disconnect from list
						deleteMovie = null; // null object
						movieDeleted = true; // movie was deleted
						System.out.format("Movie %s deleted successfully\n\n", deleteTitle);
						break;
					} else {
						temp = temp.next; // traverse to next if title not found yet
					}
				}
				/* if after traversing the movie isn't found print this */
				if (movieDeleted == false) {
					System.out.println("Movie to delete not found!\n");
				}
			}
		}
		return movieDeleted;
	}
}
