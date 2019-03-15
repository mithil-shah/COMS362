
/**
 * @author Fadel Alshammasi
 */

package features;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList extends Feature  {
	
	int num;

	
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
	public String setResponse() {
		return "Bye! Done with to-do list!";
		
		
	}

	@Override
	protected void parseQuery(String query) 
	{
		
	}
	

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
			//scan.close();
			
			return option;
		}
		
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
			//scan.close();
			}
			catch (Exception ex) {
				System.out.println("can't read file");
			}
	
		}
		private static  void addItem() {
			try {
				FileWriter fw=new FileWriter("src/todo.txt",true);
				Scanner scan=new Scanner(System.in);
				PrintWriter pw=new PrintWriter(fw);
				System.out.print("Add an item please: ");
				String str=scan.nextLine();
				pw.println(str);
				pw.close();
				//scan.close();

			}
			catch (Exception ex) {
				System.out.println("Error: Please try again");
			}
			
			
		}	
		
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
