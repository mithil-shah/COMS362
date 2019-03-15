/**
 * 
 * A Java class that creates a to-do list with 3 use cases :a) Display all items in the list
 * 											     b)Add an item
 * 												 c)Clear the list from all items
 * 
 * @author fadelalshammasi
 *
 */
package features;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class ToDoList extends Feature  {
	
	
	/**
	 * A constructor that calls its super constructor then give all of the 3(4 with exiting)options
	 *  so we can use it in our main program in the chat box by parsing the key words "to-do"
	 * @param query
	 */
	public ToDoList(String query)
	{
		super(query);
		int option = options();
		
		while(option !=4) {
			option=options();
		switch(option) {
			case 1:
				displayList();
				break;
			case 2:
				addItem();
				break;
			case 3:
				clearList();
				break;
			
			case 4:
				break;
		}
	}
				
		parseQuery(query);
	}

	
	
	@Override
	
	/**
	 * Showing the final Response after being done with to-do list
	 */
	public String setResponse() {
		return "Bye! Done with to-do list!";
		
		
	}

	@Override
	
	/**
	 * It's not needed since we didn't use it with respect to my to-do list
	 */
	protected void parseQuery(String query) 
	{
		
	}
	
	/**
	 * A method to display all of the tasks that the user can choose from 
	 * @return the choice that the user entered
	 */
	private static int options() {
			
			Scanner scan=new Scanner(System.in);
			System.out.println("\n Options to choose from \n");
			System.out.println("1. Display my toDo list");
			System.out.println("2. Add an item to the list");
			System.out.println("3. Clear the list");
			System.out.println("4. Exit");
			System.out.println();
			System.out.print("Please select an option: ");
			int option= scan.nextInt();
			
			return option;
		}
	
	/**
	 * A method to display the current items/tasks in the list with their numbers from 1 to n each in new line
	 */
	private static void displayList() {
			
			System.out.println("\n My ToDo list:\n");
			try {
				String str;
				FileReader fr=new FileReader("src/todo.txt");
				Scanner scan=new Scanner (fr);
				int option=1;			
				while (scan.hasNextLine()){
					str=scan.nextLine();
					System.out.print(option+ " ");
					System.out.println(str);
					option++;
					
				}
			System.out.println();
			}
			catch (Exception ex) {
				System.out.println("can't read file");
			}
	
		}
	
	/**
	 * A method to add an item/task into the list (one element at a time) by reading what the user has typed in into the consle
	 * 
	 */
		private static  void addItem() {
			try {
				FileWriter fw=new FileWriter("src/todo.txt",true);
				Scanner scan=new Scanner(System.in);
				PrintWriter pw=new PrintWriter(fw);
				System.out.print("Add an item please: ");
				String str=scan.nextLine();
				pw.println(str);
				pw.close();

			}
			catch (Exception ex) {
				System.out.println("Error: Please try again");
			}
			
			
		}	
	
		/**
		 * a method that: 
		 *     a) Display the current items/tasks
		 *     b)delete all the items/tasks in the list so the list is empty
		 *    
		 */
		private static void clearList() {
			displayList();
			ArrayList<String> items=new ArrayList<String>();
			Scanner scan=new Scanner(System.in);

			try {
				
				FileReader fr=new FileReader("src/todo.txt");
				Scanner scan1=new Scanner (fr);
				String str;
				
				while(scan1.hasNextLine()) {
					str=scan1.nextLine();
				    items.remove(str);
	
			}
				FileWriter fw=new FileWriter("src/todo.txt");
				PrintWriter pw=new PrintWriter(fw);
				

			}	
				catch (Exception ex) {
					System.out.println("Error: can't read file");
				}
			
         	}
		}
