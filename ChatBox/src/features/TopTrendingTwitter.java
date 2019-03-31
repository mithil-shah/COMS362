package features;

import configuration.Response;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Gets the top trends from Twitter
 * 
 * @author Mithil Shah
 *
 */

public class TopTrendingTwitter implements Feature
{
	/**
	 * The return string containing the top trends in the United States
	 */
	private String trending;
	
	/**
	 * Constructs a new TopTrendingTwitter object
	 * 
	 * @param query
	 * 		The query specified by the user
	 */
	public TopTrendingTwitter(String query)
	{
		//Instantiate the string
		trending = "";
		
		//Parse the query for a number
		parseQuery(query);
	}
	
	/**
	 * Returns the the top number of trends requested by the user
	 * 
	 * @return Response
	 * 		The top number of trends requested by the user (10 by default if not specified)
	 * 
	 */
	@Override
	public Response setResponse()
	{
		return new Response(trending);
	}

	/**
	 * Parses the query for a number to retrieve specified amount of trending tweets/topics. (10 by default)
	 * Then uses Twitter4J API to retrieve the current top # of tweets/topics in the US 
	 * 
	 * @param query
	 * 	 The query specified by the user
	 */
	@Override
	public void parseQuery(String query) 
	{
		//Number of times for loop iterates to get trends
		int iterations = 10;
		
		//Look for a number in the query
		for(String number : query.split(" "))
		{
			try
			{
				iterations = Integer.parseInt(number);
			}
			catch(NumberFormatException nfe)
			{
				continue;
			}
		}
		
		//Construct Twitter4J API object
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		//Fill in Twitter API credentials 
		cb.setDebugEnabled(true).setOAuthConsumerKey("Zsb6wBxZyPTmzpmf1rfEm3f1Q").setOAuthConsumerSecret("n5XMuKko2byvPViMmFqmvNcw4qSnkhQVbVgKTpgOqjGc1HZIqF").setOAuthAccessToken("799005294-rp8QVlFPTTjXTBQx1KWmUAsleq8iCNMNl4Cj8HuL").setOAuthAccessTokenSecret("hqiExWVWQrbzxcWbjIWd0N3yJdUyQFswhYZkc85z8aWFP");
		
		//Make a Twitter Object
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		
		//Append the topics to the response string.
		try 
		{
			//23424977 = WOEID for US 
			Trends trends = twitter.getPlaceTrends(23424977);
			
			for (int i = 0; i < iterations; i++) 
			{
			    trending += trends.getTrends()[i].getName() + "\n";
			}
		}
		catch (TwitterException te)
		{
			te.printStackTrace();
		}
	}

}
