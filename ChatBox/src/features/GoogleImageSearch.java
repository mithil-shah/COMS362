package features;


import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author saishreyashankar
 *
 */
public class GoogleImageSearch extends Feature
{   String toSearch = "";
	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	int numLinksToReturn=0;

	public GoogleImageSearch(String query)
	{
		/*This will get the query which is what the user wants to search and the number of top 
		 * results that the user wants returned and then assign each of them to the corresponding
		 * public value to be used in the response method. 
		*/
		super(query);
		parseQuery(query);
	}

	@Override
	public String setResponse()
	{ //gets the google search URL using the query and number from constructor.
		String searchURL = GOOGLE_SEARCH_URL + "?q="+toSearch+"&num="+numLinksToReturn;
		// A user agent must be used to get data
		Document doc=null;
		try {
			doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Adds number of results wanted to "results" and goes through each result and print it out.
		String toReturn= "";
		Elements results = doc.select("h3.r > a");
		for (Element result : results) {
			String linkHref = result.attr("href");
			String linkText = result.text();
			toReturn= toReturn + "\n" + "Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&"));
		}
		
		return toReturn;
	}

	@Override
	protected void parseQuery(String query) 
	{
		query= query.replaceAll("\\s+", "");
		String toInt="";
		int toStopSubstring = query.length();
		
		
	    for(int i= 0; i<query.length(); i++) {
	    	if(query.charAt(i)==',') {
	    
	    		toInt=query.substring(i+1,query.length());
	    		
	    		toStopSubstring=i;
	    	}
	    }
		
		toSearch=query.substring(0,toStopSubstring);
		if(toInt.length()==0) {
			numLinksToReturn= 3;
		}
		else {
			numLinksToReturn= Integer.parseInt(toInt);
		}
		
	}

}
