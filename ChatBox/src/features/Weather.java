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

    public Weather(String query)
    {
        super(query);
        parseQuery(query);
    }

    void parseQuery(String query)
    {
        String [] nonKeywords = {"where", "is", "of", "temp", "weather", "at", "in", "city", "location", "the"};
        int indexOfCity = -1;
        
        for(String word: query.split(" "))
        {
            boolean isNotCity = false;

            for(String nonKeyword: nonKeywords)
            {
                if(word.toLowerCase().equals(nonKeyword))
                {
                    isNotCity = true;
                    break;
                }
            }

            if(!isNotCity)
            {
                if(word.contains(","))
                {
                    word = word.substring(0, word.length()-1);
                    System.out.println(word + " " );
                }

                try
                {
                    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + word + "&key=AIzaSyAwDcz884IY6ztK2-Ifrtjyj-3jc_T3xzw");
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
                    e.printStackTrace();
                }
            }
        }
    }

    void parseLocation(String locationData)
    {
        JsonParser parser = new JsonParser();
        JsonObject data = parser.parse(locationData).getAsJsonObject();
        JsonArray results = data.getAsJsonArray("results");
        JsonElement firstInstance = results.get(0);
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
            return parseWeatherResponseData(getForecast());
        }
        catch (IOException e)
        {
            return "Error parsing weather";
        }
    }
}
