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
    }
}
