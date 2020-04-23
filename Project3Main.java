/************************************************************************* 
  Student Name:      Ryan Thornton
  File Name:         Project3Main.java
  Assignment number: Project3

  This is the main file of the project. It has the main function that
  will create an object of the HashDatabase type then prompt the user 
  with the menu until they choose to exit. 
**************************************************************************/
import java.util.Scanner;

/**
 * Main class that drives Project3
 *
 */
public class Project3Main 
{
	/**
	 * main function driving project
	 */
	public static void main(String[] args) throws Exception
	{
		HashDatabase movieDb = new HashDatabase(); // create new HashDatabase object (contains hash table)
		Scanner input = new Scanner(System.in); // create new scanner for user input
		int choice = 0;
		
		while (true)
		{
			movieDb.printMenu(); // print the menu
			System.out.print("Your choice: ");
			choice = input.nextInt(); // get the users choice
			input.nextLine(); // consume enter press
			if (choice < 0) { // if choice is -1 exit the menu
				System.out.println("Goodbye...");
				break;
			} else { // else process choice
				movieDb.processChoice(choice, input);
			}
		}
		input.close(); // close the scanner upon exit
	}
}
