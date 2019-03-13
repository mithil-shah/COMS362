/**
 * @author Mithil Shah
 */
package features;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TopHitsSpotify extends Feature
{
	private String topHits = "";
	
	public TopHitsSpotify(String query)
	{
		super(query);
		
		try 
		{
			parseHTML("https://spotifycharts.com/regional");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public String setResponse() 
	{
		return topHits;
	}
	
	private void parseHTML(String url) throws IOException
	{
		Document doc = Jsoup.connect(url).get();
		
		int rank = 1;
		
		for(Element row: doc.select("table.chart-table tr"))
		{
			String titleByAuthor = row.select("td.chart-table-track").text();
			if(!titleByAuthor.isEmpty())
			{
				topHits += rank++ + ". " + titleByAuthor + "\n";
			}
		}
	}

	@Override
	protected void parseQuery(String query)
	{
		
	}
	
}
