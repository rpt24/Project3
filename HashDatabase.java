/************************************************************************* 
  Student Name:      Ryan Thornton
  File Name:         HashDatabase.java
  Assignment number: Project3

  This is the HashDatabase class file. It makes a user defined hash table
  and processes the commands from user input to add/delete/search movies
  in the hash table. 
**************************************************************************/
import java.io.*;
import java.util.Scanner;

/**
 * HashDatabase class
 * User defined hashtable lives here. It can
 * add/delete/search movies from its "database"
 * and performs hash table functions. The table is 
 * rehashed/resized when the load factor is >= 0.5
 * Initial hash table size is 13.
 *
 */
public class HashDatabase 
{
	private int size = 13; // size of hash table 
	private float numItems = 0; // number of items in hash table
	private DataList[] hashTable = new DataList[size]; // hash table that holds objects of DataList type, initially size of 13
	
	/**
	 * Constructor - doesn't do anything but construct
	 */
	public HashDatabase()
	{

	}
	
	/**
	 * Prints the menu that the user makes choices from
	 */
	public void printMenu()
	{
		System.out.println("-------- Make a choice: ----------");
		System.out.println("Read database file (1)");
		System.out.println("Add a new movie (2)");
		System.out.println("Search for a movie by title (3)");
		System.out.println("Delete a movie (4)");
		System.out.println("Display all movies (5)");
		System.out.println("Exit (-1)");
		System.out.println("-------------------------------");
	}
	
	/**
	 * Processes the user's input choice and performs the 
	 * appropriate function
	 * 
	 * @param choice - choice of the user
	 * @param scanner - scanner from Project3Main used for gathering user input
	 * @throws Exception - file open exception when user prompts to open an input file
	 */
	public void processChoice(int choice, Scanner scanner) throws Exception
	{
		System.out.print("\n"); // print a new line for formatting aesthetics
		
		/* Based on the user choice we will perform a hash table function if the choice is valid */
		switch(choice) 
		{
		case 1:		// choice 1 we read an input file
			readInputFile(scanner);
			break;
		case 2:		// choice 2 we make a movie on the command line from user input
			hashTableInsert(makeInputtedMovie(scanner));
			System.out.println("Movie inserted successfully...\n");
			break;
		case 3:		// choice 3 we search for a movie in the hash table
			findMovie(scanner);
			break;
		case 4:		// choice 4 we delete a movie from the hash table if it exists
			deleteMovie(scanner);
			break;
		case 5:		// choice 5 we print the contents of the hash table
			printHashTable();
			break;
		default:	// default choice - user input not a menu option
			System.out.format("Input choice %d not a valid menu option...\n\n",choice);
			break;
		}
	}
	
	/**
	 * This function inserts a Movie class object into the hash table
	 * @param newMovie - the movie object to be inserted
	 */
	public void hashTableInsert(Movie newMovie)
	{
		int key = getHashKey(newMovie.title); // get the appropriate key
		if (hashTable[key] == null) { // if that spot is null we make a new DataList object and insert the movie
			hashTable[key] = new DataList();
			hashTable[key].insertMovie(newMovie);
		} else { // else we just insert the movie if !null
			hashTable[key].insertMovie(newMovie); 
		}
		
		numItems = numItems + 1; // increase number of items by 1
		if (calculateLoadFactor()) { // see if the hash table needs to be resized/rehashed
			increaseHashTableSize();
		}
	}
	
	/**
	 * This makes a Movie object based on user's input from the command line
	 * 
	 * @param scanner - scanner from main to get user input
	 * @return - returns the made movie object
	 */
	public Movie makeInputtedMovie(Scanner scanner)
	{
		Movie newMovie; // movie object to insert
		/* below are the variables needed to make the movie object */
		String movieTitle;
		String movieLead;
		String movieDesc;
		int movieYear;
		
		/* force user to give a valid string for title for the hash key */
		while (true)
		{
			System.out.print("Enter movie's title: ");
			movieTitle = scanner.nextLine();
			if (movieTitle.equals("")) {
				System.out.print("\nERROR - Enter a string for the title please...\n");
			} else {
				break;
			}
		}
		/* Gather the rest of the input from the user */
		System.out.print("Enter movie's lead actor/actress: ");
		movieLead = scanner.nextLine();
		System.out.print("Enter movie's description: ");
		movieDesc = scanner.nextLine();
		System.out.print("Enter movie's release year: ");
		movieYear = scanner.nextInt();
		newMovie = new Movie(movieTitle, movieLead, movieDesc, movieYear);
		
		return newMovie;
	}
	
	/**
	 * This gets the hash key using a multiplicative hashing function
	 * 
	 * @param key - the title is used as the key
	 * @return - returns the key
	 */
	public int getHashKey(String key)
	{
		int hashKey = 5381; // set key to initial value of 5381
		int multiplier = 33; // multiplier
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			hashKey = (hashKey * multiplier) + c;
		}
		hashKey = hashKey % size; // modulus the size of the table (key % N)
		
		/* return the absolute value so key will be non-negative */
		return Math.abs(hashKey);
	}
	
	/**
	 * Calculates the load factor of the hash table
	 * @return - returns true if resizing needed, else false for no resizing needed
	 */
	public boolean calculateLoadFactor()
	{
		boolean resize = false;
		/* create floats for decimal place in division */
		float itemNum = numItems;
		float tableSize = size;
		float loadFactor = itemNum / tableSize; // calculate load factor using floats
		if (loadFactor >= 0.5) {
			System.out.format("\nLoad factor of %.2f equals or exceeds 0.5\n",loadFactor);
			resize = true; // return true that we need a resizing
		}
		
		return resize;
	}
	
	/**
	 * Increases the hash table size and rehashes elements into 
	 * new hash table of double size
	 */
	public void increaseHashTableSize()
	{
		System.out.println("Increasing Hash Table Size and Rehashing...");
		// double the size
		size = size * 2;
		// temp arr of double size to store data
		DataList[] temp = new DataList[size];
		
		// copy elements from hashArr into a temp of double size at a new hashed bucket
		for (int i = 0; i < size/2; i++) {
			if (hashTable[i] != null) {
				temp[getHashKey(hashTable[i].head.title)] = hashTable[i]; 
			}
		}
		// set class hash table = to the temp
		hashTable = temp;
		System.out.format("Hash table resized... new size = %d\n\n", size);
	}
	
	/**
	 * Prints the contents of the hash table
	 */
	public void printHashTable()
	{
		for (int i = 0; i < size; i++) {
			if (hashTable[i] != null) { // if !null there's content to print
				hashTable[i].printList();	// call DataList's print list function
			}
		}
	}
	
	/**
	 * Finds a movie in the hash table for choice 3 searching for a movie
	 * 
	 * @param scanner - scanner from main to get user input
	 */
	public void findMovie(Scanner scanner)
	{
		System.out.print("Enter movie's title: "); // prompt user for title
		String searchTitle = scanner.nextLine();
		int key = getHashKey(searchTitle); // get hash key from title
		if (hashTable[key] != null) { // if !null we search for the Movie in the DataList
			hashTable[key].findListMovie(searchTitle);
		} else {
			System.out.println("Movie not found!\n");
		}
	}
	
	/**
	 * Deletes a movie from the hash table
	 * @param scanner - scanner from main to get user input
	 */
	public void deleteMovie(Scanner scanner)
	{
		System.out.print("Enter movie to delete's title: "); // prompt user for title
		String deleteTitle = scanner.nextLine();
		int key = getHashKey(deleteTitle); // get hash key from title
		if (hashTable[key] != null) { // if !null search DataList for Movie
			if (hashTable[key].deleteListMovie(deleteTitle)) { // if movie was deleted successfully decrement item num in hash table
				numItems = numItems - 1;
			}
		} else {
			System.out.println("Movie to delete not found!\n");
		}
	}
	
	/**
	 * Reads input file of user's choice and adds movies
	 * @param scanner - scanner for user input
	 * @throws Exception - exception for file operations
	 */
	public void readInputFile(Scanner scanner) throws Exception
	{
		System.out.print("Enter filename: "); // prompt user for filename
		String fileName = scanner.nextLine();
		File file = new File(fileName); // open a file
		if (file.exists()) { // if the file exists we parse it
			System.out.println("File loaded successfully.\n");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while((st = br.readLine()) != null) // while string isn't null we parse the file and data
			{
				String[] movieData = st.trim().split("\\s*,\\s*"); // split string at comma, remove whitespace
				Movie newMovie = new Movie(movieData[0], movieData[1], movieData[2], Integer.parseInt(movieData[3])); // make a new movie object
				hashTableInsert(newMovie); // insert new Movie into hash table
			}
			br.close(); // close reader
		} else {
			System.out.format("File %s not found!\n\n",fileName);
		}
		
	}
}
