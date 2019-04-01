package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import configuration.Response;

/**
 * Gets the top headlines for the day
 * 
 * @author Shreya Shankar
 *
 */

public class TopNewsHeadlines implements Feature
{
	/**
	 * The return string containing the title, author, and URL of top news headlines for the day
	 */
	private String headlines;
	
	/**
	 * Constructs a new TopNewsHeadlines object. Instantiates the headlines string and calls parseQuery()
	 * 
	 * @param query
	 * 		The query provided by the user.
	 */
	public TopNewsHeadlines(String query)
	{
		headlines = "";
		parseQuery(query);
	}

	/**
	 * Returns the top headlines to the user
	 * 
	 * @return Response
	 * 		The top headlines for the day
	 */
	@Override
	public Response setResponse() 
	{
		return new Response(headlines);
	}

	/**
	 * Nothing needs to be parsed, so we call getHeadlines()
	 * 
	 * @param query
	 * 		The query provided by the user
	 */
	@Override
	public void parseQuery(String query)
	{
		try
		{
			getHeadlines();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetches the top headlines in the US from News API.
	 * 
	 * @throws IOException
	 * 		If the API cannot function properly
	 */
	private void getHeadlines() throws IOException
	{
		//NewsAPI
		URL url = new URL("https://newsapi.org/v2/top-headlines?country=us&apiKey=f278c164c5834348885ae1c1d4905eae");
		
		//Go to the URL specified above (News API)
		HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        //Put the JSON object that is returned into a string
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;

        while((input = br.readLine()) != null)
        {
            sb.append(input);
        }

        //Close the BufferedReader to prevent leaks.
        br.close();
        
        //Parse the JSON string we just retrieved and appended
        parseJSONResponse(sb.toString());
	}
	
	/**
	 * Parses the JSON string for title, author, and URLs for the articles returned
	 * 
	 * @param response
	 * 		The JSON string retrieved from getHeadlines()
	 */
	private void parseJSONResponse(String response)
	{
		//Creates a JSON element from the provided JSON string from News API 
        JsonParser parser = new JsonParser();
        JsonObject data = parser.parse(response).getAsJsonObject();
        
        //Gets the articles found in the JSON string
        JsonArray articles = data.getAsJsonArray("articles");
        
        //For each article in the articles array
        for(JsonElement article : articles)
        {
        	//Get the current article as an article object
        	JsonObject currentArticle = article.getAsJsonObject();
        	
        	//From the article object, get these properties of the article
        	//Title contains title and author
        	JsonElement title = currentArticle.get("title");
        	JsonElement url = currentArticle.get("url");
        	        	
        	//Append these properties to the return string
        	headlines += title.getAsString() + "\n" + url.getAsString() + "\n\n";
        }
	}
	
}
