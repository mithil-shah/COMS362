package configuration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Mithil Shah & Shreya Shankar
 *
 */

public class UI
{
    public static void main (String [] args) throws Exception
    {
    	//User Input
        Scanner scan = new Scanner(System.in);
        
        //Only show the big script on the first run to give user an idea of how to run ChatBox.
        boolean isInitialQuery = true;
        
        //Read from the keywords.txt file
        BufferedReader reader = new BufferedReader(new FileReader("src/keywords.txt"));
        
        //Store the classes from keywords.txt into this arraylist
        ArrayList<String> classes = new ArrayList<>();
        String line;
        
        //Puts classes from keywords.txt into the arraylist
        while ((line = reader.readLine()) != null)
        {
            String [] keyValuePair = line.split(": ");
            if(!classes.contains(keyValuePair[1]))
            {
                classes.add(keyValuePair[1]);
            }
        }
        
        //Close the buffered reader to prevent leaks
        reader.close();
        
        //Tells the user what they can accomplish with ChatBox
        String info = "I can help you with ";
       
        while(true)
        {
            //Greeting, also shown after query is completed
        	System.out.println("Hello! My name is ChatBox! How may I assist you?");

        	//If this is the first time the ChatBox is used...
        	if(isInitialQuery)
        	{   
        		//Append all classes from the array list to the string
            	for(String className: classes)
            	{
            		info += (className + ", ");
            	}
            	
            	//Get rid of whitespaces in the info string
            	info = info.trim();
            	
            	//Replace the last comma with a period.
            	System.out.println(info.substring(0, info.length()-1) + ".");
            	
            	//Examples
            	System.out.println("    -To ask for the weather type: weather in \"city\" OR temp in \"city\"");
            	System.out.println("        Example: what is the temp in Ames");
            	System.out.println("    -To ask search for pictures Type: search image \"what you want to search\", \\\"number of images\\\"");
    			System.out.println("        Example: search image dogs, 5; this will return 5 different pictures of dogs");
    			System.out.println("    -To convert currency Type: convert \"amount,original currency,intended currency\" with spaces inbetween");
    			System.out.println("        Example: convert 10 USD to GBP; this will return the conversion of 10USD to GBP");
    			
    			//Since the ChatBox has now ran, make isInitialQuery false.
    			isInitialQuery = false;
        	}
        	
        	//Get the user's query
            String query = scan.nextLine();

            //if the user types "bye", exit the program.
            if(query.toLowerCase().contains("bye"))
            {
                break;
            }	
            
            //New instance of CaseController to control which class to go to based on Class keyword parsed from the user's query
            CaseController cc = new CaseController(query);
            
            //if the appropriate class can provide a response, print it
            if(cc.getResponse() != null)
            {
                cc.getResponse();
            }
            //else print friendly error message...
            else
            {
                System.out.println("Sorry, I am unable to accomplish this task right now.");
            }

            System.out.println();
        }
        
        //close Scanner to prevent leaks.
        scan.close();
    }
}
