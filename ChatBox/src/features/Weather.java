/**
 * @author Mithil Shah
 */

package features;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.*;

public class Weather extends Feature
{
    private URL url;
    private String foundLocation;

    public Weather(String query)
    {
        super(query);
        parseQuery(query);
    }

    protected void parseQuery(String query)
    {
        String nonKeywords = "where" + "is" + "of" + "temp" + "weather" + "at" + "in" + "city" + "location" + "the" + "what" + "get" + "fetch";
        String [] words = query.split(" ");
        
        String location = "";
        
        for(int i = 0; i < words.length; i++)
        {
        	if(!nonKeywords.contains(words[i]))
        	{
        		location += words[i] + "+";
        	}
        }

		try
		{
			if(!location.equals(""))
			{
				url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyAwDcz884IY6ztK2-Ifrtjyj-3jc_T3xzw");
			}
			else
			{
				url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,%20+Mountain+View,+CA&key=AIzaSyAwDcz884IY6ztK2-Ifrtjyj-3jc_T3xzw");
			}
			
			HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;

            while((input = br.readLine()) != null)
            {
                sb.append(input);
            }

            br.close();

            parseLocation(sb.toString());
		} 
		catch (IOException e) 
		{

		}
        
    }

    void parseLocation(String locationData)
    {
        JsonParser parser = new JsonParser();
        JsonObject data = parser.parse(locationData).getAsJsonObject();
        JsonArray results = data.getAsJsonArray("results");
        JsonElement firstInstance = results.get(0);
        
        JsonArray addressComponent = firstInstance.getAsJsonObject().get("address_components").getAsJsonArray();
        foundLocation = addressComponent.get(0).getAsJsonObject().get("long_name").getAsString();
        
        JsonElement geometry = firstInstance.getAsJsonObject().get("geometry");
        JsonElement location = geometry.getAsJsonObject().get("location");

        double latitude = location.getAsJsonObject().get("lat").getAsDouble();
        double longitude = location.getAsJsonObject().get("lng").getAsDouble();

        try
        {
            setConnection(longitude, latitude);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    private void setConnection(double longitude, double latitude) throws MalformedURLException
    {
        final String secretKey = "ea38b2970a021faaf32ce8d3373c7f68";
        final String baseURL = "https://api.darksky.net/forecast/";
        url =  new URL(baseURL + secretKey + "/" + latitude + "," + longitude);
    }

    private String getForecast() throws IOException
    {
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String input;

        while((input = br.readLine()) != null)
        {
            sb.append(input);
        }

        br.close();

        return sb.toString();
    }

    private String parseWeatherResponseData(String response)
    {
        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(response);

        if(tree.isJsonObject())
        {
            JsonObject object = tree.getAsJsonObject();
            JsonElement currently = object.get("currently");

            if(currently.isJsonObject())
            {
                JsonObject currentlyObject = currently.getAsJsonObject();
                JsonElement summary = currentlyObject.get("summary");
                JsonElement tempF = currentlyObject.get("temperature");
                return summary.getAsString() + ": " + tempF.getAsInt() + "°F";
            }
        }

        return "Error fetching weather";
    }

    public String setResponse()
    {
        try
        {
            return parseWeatherResponseData(getForecast()) + " @ " + foundLocation;
        }
        catch (IOException e)
        {
            return "Error parsing weather";
        }
    }
}
