/**
 * @author Bernard Ang & Mithil Shah
 */
package features;

import configuration.Response;
import java.util.Random;


public class FlipCoin extends Feature {
	
	int iterations = 1;
	
    public FlipCoin(String query)
    {
        super(query);
        parseQuery(query);
    }

	protected void parseQuery(String query)
	{
		String[] words = query.split(" ");
		
		for(int i = 0; i < words.length; i++)
		{
			try
			{
				iterations = Integer.parseInt(words[i]);
				break;
			}
			catch(ClassCastException | NumberFormatException e)
			{
				continue;
			}
		}
	}

	@Override
	public Response setResponse()
	{
		String toReturn = "";
		Random rand = new Random(System.currentTimeMillis());
		String [] possibilities = {"Heads", "Tails"};
		
		for(int i = 0; i < iterations; i++)
		{
			int random = rand.nextInt(2);
			toReturn += "Attempt " + (i+1) + ": " + possibilities[random] + "\n";			
		}
		
		return new Response(toReturn);
	}
}
