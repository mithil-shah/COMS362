import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weather
{
    private double longitude;
    private double latitude;
    private URL url;

    public Weather(double longitude, double latitude) throws MalformedURLException
    {
        final String secretKey = "ea38b2970a021faaf32ce8d3373c7f68";
        final String baseURL = "https://api.darksky.net/forecast/";

        this.longitude = longitude;
        this.latitude = latitude;
        url =  new URL(baseURL + secretKey + "/" + latitude + "," + longitude);
    }

    //unit can be either c, f, or k
    public String getForecast() throws IOException
    {
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String input;

        while((input = br.readLine()) != null)
        {
            sb.append(input);
        }

        br.close();

        return sb.toString();
    }

    //unit can = k, c, or f
    public int getTemp(char unit, String data)
    {
        int temp = Integer.MIN_VALUE;

        Pattern tempPattern = Pattern.compile("\"currently\"\\s*:\\s*\"([^,]*)\",");
        Matcher matcher = tempPattern.matcher(data);

        if(matcher.find())
        {
            System.out.println(matcher.group(0));
        }

        if (unit == 'k')
        {

        }
        else if (unit == 'c')
        {

        }
        else
        {

        }

        return temp;
    }
}
