import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UI
{
    public static void main(String []args) throws IOException
    {
        Scanner scan = new Scanner(System.in);

        while(true)
        {
            System.out.println("Hi there! How may I help you today?");
            String s = scan.nextLine();

            if(s.contains("weather"))
            {
                System.out.println("The weather today in Ames, IA is " + getWeather() + "°F");
            }
            else
            {
                System.out.println("Sorry, I am unable to process your request.");
            }

            System.out.println("\n");
        }
    }

    public static int getWeather() throws IOException
    {
        double longitude = -122.4233;
        double latitude = 37.8267;

        Weather weather = new Weather(longitude, latitude);

        return weather.getTemp('f', weather.getForecast());
    }
}
