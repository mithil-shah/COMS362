/**
 * @author Mithil Shah & Shreya Shankar
 */
import java.util.Scanner;

public class UI
{
    public static void main (String [] args) throws Exception
    {
        Scanner scan = new Scanner(System.in);

        while(true)
        {   System.out.println("Hello! My name is ChatBox!");
        	System.out.println("    -To ask for the weather type: weather in \"city\" OR temp in \"city\"");
        	System.out.println("        Example: what is the temp in Ames");
        	System.out.println("    -To ask search for pictures Type: search image \"what you want to search\", \\\"number of images\\\"");
			System.out.println("        Example: search image dogs, 5; this will return 5 different pictures of dogs");
			System.out.println("    -To convert currency Type: convert \"amount,original currency,intended currency\" with spaces inbetween");
			System.out.println("        Example: convert 10 USD to EUR; this will return the conversion of 10USD to EUR");
//			System.out.println("    -To convert currency Type: flip coin \"amount of times you want to flip\" times");
//			System.out.println("        Example: flip coin 10 times; this will return a heads/tail for 10 times");
			System.out.println("    -To get stocks info Type: get stocks info for \"stock symbol\"");
			System.out.println("        Example: get stocks info for MSFT; returns stock details for MSFT");
			System.out.println("    -To get my to-do list \"to-do\"");
			System.out.println("        Example: to-do");

            String query = scan.nextLine();

            if(query.toLowerCase().contains("bye"))
            {
                break;
            }

            CaseController cc = new CaseController(query);

            if(cc.getResponse() != null)
            {
                System.out.println(cc.getResponse());
            }
            else
            {
                System.out.println("Sorry, I am unable to accomplish this task right now.");
            }

            System.out.println();
        }
        
        scan.close();
    }
}
