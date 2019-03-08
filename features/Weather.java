package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather extends Feature
{
    private URL url;

    public Weather(String query)
    {
        super(query);
        parseQuery();
    }

    void parseQuery()
    {
        //TODO

        double longitude = -122.4233;
        double latitude = 37.8267;

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
        return response;
    }

    public String setResponse()
    {
        try
        {
            return parseWeatherResponseData(getForecast());
        }
        catch (IOException e)
        {
            return "Error - Weather";
        }
    }
}
