package features;

import java.io.IOException;
import configuration.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author Mithil Shah
 *
 */

public class TopHitsSpotify extends Feature
{
	/**
	 * Holds the top 200 hits from, Spotify based off a user's region
	 */
	private String topHits = "";
	
	/**
	 * Calls the parseHTML() method right away since no more query needs to be parsed for additional data
	 * 
	 * @param query
	 * 		The query provided by the user
	 * @throws IOException
	 * 		Thrown if HTML page cannot be returned (Error 404)
	 */
	public TopHitsSpotify(String query)
	{
		super(query);
		
		//Call the parseHTML() method to get data regarding the top 200 songs from Spotify (regional)
		try 
		{
			parseHTML("https://spotifycharts.com/regional");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return topHits
	 * 		Returns the top hits of the user's region given in the format of Rank (#). Song Name by Artist Name
	 */
	@Override
	public Response setResponse() 
	{
		return new Response(topHits);
	}
	
	/**
	 * Parses the HTML page, spotifycharts.com/regional, for the song and artist name.
	 * Then it appends each song to the topHits string.
	 * 
	 * @param url
	 * 		https://spotifycharts.com/regional
	 * @throws IOException
	 * 		Thrown if the HTML returns an Error 404 code or if the HTML cannot be fetched correctly
	 */
	private void parseHTML(String url) throws IOException
	{
		//Let Jsoup connect to the URL and get an HTML page returned
		Document doc = Jsoup.connect(url).get();
		
		//Every song is given in order of its rank, so we can do less parsing to make the code more efficient by just incrementing the rank after each song is fetched/parsed 
		int rank = 1;
		
		//This table element is where all the top 200 songs are.
		for(Element row: doc.select("table.chart-table tr"))
		{
			//Each row in the table contains a rank, song name, and artist, but were just looking for the Song Name by Artist Name parameter. 
			String titleByAuthor = row.select("td.chart-table-track").text();
			
			//Make sure empty lines are not appended to the topHits string
			if(!titleByAuthor.isEmpty())
			{
				topHits += rank++ + ". " + titleByAuthor + "\n";
			}
		}
	}

	/**
	 * @param query 
	 * 		The query provided by the user that does not need parsing for this scenario
	 */
	@Override
	protected void parseQuery(String query)
	{
		
	}
	
}
