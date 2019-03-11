import java.util.Scanner;

public class UI
{
    public static void main (String [] args) throws Exception
    {
        Scanner scan = new Scanner(System.in);

        while(true)
        {
            System.out.println("Hi there! How may I help you?");
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
                System.out.println("I dunno");
            }

            System.out.println();
        }
    }
}
