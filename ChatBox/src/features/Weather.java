package features;
import configuration.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.*;

/**
 * 
 * @author Mithil Shah
 *
 */

public class Weather extends Feature
{
	/**
	 * The URL of the weather API
	 */
    private URL url;
    /**
     * The location of the user found by Google Maps API
     */
    private String foundLocation;

    /**
     * Calls parseQuery() to parse the query provided by the user
     * 
     * @param query
     * 		The query provided by the user.
     */
    public Weather(String query)
    {
        super(query);
        parseQuery(query);
    }

    /**
     * Parses the query to find the location of the user
     * @param query
     * 		The query provided by the user
     * @throws IOException
     * 		Thrown if the website cannot be reached (Error 404 code from HTML) or if the website cannot be read correctly
     */
    protected void parseQuery(String query)
    {		
    	//These are words that are probably not the location that the user wants to find weather from
        String nonKeywords = "where" + "is" + "of" + "temp" + "weather" + "at" + "in" + "city" + "location" + "the" + "what" + "get" + "fetch";
        
        //The query is split into keywords
        String [] words = query.split(" ");
        
        //The location provided by the user in the query... in the format of City+Name,+State+Name
        String location = "";
        
        //Gets the location provided by the user by avoiding all words in the nonKeywords string.
        for(int i = 0; i < words.length; i++)
        {
        	if(!nonKeywords.contains(words[i]))
        	{
        		location += words[i] + "+";
        	}
        }

		try
		{
			//if the user provided a location
			if(!location.equals(""))
			{
				//find the coordinates of the city, state provided by the user
				url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyAwDcz884IY6ztK2-Ifrtjyj-3jc_T3xzw");
			}
			else
			{
				//find the coordinates of Mountain View, California
				url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,%20+Mountain+View,+CA&key=AIzaSyAwDcz884IY6ztK2-Ifrtjyj-3jc_T3xzw");
			}
			
			//Go to the URL specified above (Google Maps API)
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

            //Parse the JSON string to get the coordinates from the user's provided city and state
            parseLocation(sb.toString());
		} 
		catch (IOException e) 
		{

		}
        
    }

    /**
     * 
     * @param locationData
     * 		The JSON string returned from Google Maps API
     * @throws MalformedURLException
     * 		Thrown if the URL cannot be reached (Bad URL, Error 404)
     */
    void parseLocation(String locationData)
    {
    	//Make the JSON string into an object using Gson Library
        JsonParser parser = new JsonParser();
        JsonObject data = parser.parse(locationData).getAsJsonObject();
        
        //Find the array in the JSON file called "results"
        JsonArray results = data.getAsJsonArray("results");
        //Get the first element in that array
        JsonElement firstInstance = results.get(0);
        
        //Get the address sub-array, address_components, in the results array
        JsonArray addressComponent = firstInstance.getAsJsonObject().get("address_components").getAsJsonArray();
        //Get the city's name the sub-array, address_components and set it to foundCity
        foundLocation = addressComponent.get(0).getAsJsonObject().get("long_name").getAsString();
        
        //Get an element called, "geometry", from the "results" array
        JsonElement geometry = firstInstance.getAsJsonObject().get("geometry");
        //Within this geometry element, get another element called "location"
        JsonElement location = geometry.getAsJsonObject().get("location");

        //Get the longitude and latitude from this location object
        double latitude = location.getAsJsonObject().get("lat").getAsDouble();
        double longitude = location.getAsJsonObject().get("lng").getAsDouble();

        //Call the DarkSky API to fetch weather data in the form of a JSON file
        try
        {
            setConnection(longitude, latitude);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param longitude
     * 		The longitude of the city
     * @param latitude
     * 		The latitude of the city
     * @throws MalformedURLException
     * 		Thrown if the URL cannot be reached (Bad URL, Error 404)
     */
    private void setConnection(double longitude, double latitude) throws MalformedURLException
    {
    	//API Key
        final String secretKey = "ea38b2970a021faaf32ce8d3373c7f68";
        //Base URL
        final String baseURL = "https://api.darksky.net/forecast/";
        //Append coordinates and API Key to the base URL
        url =  new URL(baseURL + secretKey + "/" + latitude + "," + longitude);
    }

    /**
     * 
     * @return forecastString
     * 		The forecast in the form of a JSON file
     * @throws IOException
     * 		The data cannot be retrieved due to a bad URL or Error 404
     */
    private String getForecast() throws IOException
    {
    	//Set up the URL connection and get data from it
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        //Get the data from the JSON file that is returned by the URL
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String input;

        //Set the JSON data to a string called sb.
        while((input = br.readLine()) != null)
        {
            sb.append(input);
        }

        //Close the BufferedReader to avoid any leaks
        br.close();

        return sb.toString();
    }

    /**
     * Parses the JSON file from the DarkSky API for a summary and temperature in °F for the given city
     * 
     * @param response
     * 		The weather data given in JSON format from DarkSky API
     * @return weatherString
     * 		The weather data given in the format of Summary: # °F @ City Name, State/Country 
     * 		
     */
    private String parseWeatherResponseData(String response)
    {
    	//Creates a JSON element from the provided JSON string from DarkSky 
        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(response);

        //If the element is not null
        if(tree.isJsonObject())
        {
        	//Get an element called currently from the JSON file. (Fetched current weather info)
            JsonObject object = tree.getAsJsonObject();
            JsonElement currently = object.get("currently");

            //If the object is not null
            if(currently.isJsonObject())
            {
            	//Get the summary and temperature from the object
                JsonObject currentlyObject = currently.getAsJsonObject();
                JsonElement summary = currentlyObject.get("summary");
                JsonElement tempF = currentlyObject.get("temperature");
                
                //Return the response string back to CaseController and UI
                return summary.getAsString() + ": " + tempF.getAsInt() + "°F";
            }
        }

        //If the data cannot be parsed correctly
        return "Error fetching weather";
    }

    /**
     * @return response
     * 		The response provided to the user based off of what was given in their query
     * 		Summary: # °F @ City Name, State/Country 
     * @throws IOException
     * 		Thrown if the website cannot be reached.
     */
    public Response setResponse()
    {
        try
        {
            String response = parseWeatherResponseData(getForecast()) + " @ " + foundLocation;
            return new Response(response);
        }
        catch (IOException e)
        {
            return new Response("Error parsing weather");
        }
    }
}
