/**
 * @author Bernard Ang
 */
package features;

import configuration.Response;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ConvertCurrency implements Feature {
	private URL url;
	String curr1,curr2;
	int money;
	
    public ConvertCurrency(String query)
    {
        parseQuery(query);
    }

	public void parseQuery(String query) {
		String[] words = query.split(" ");
		 curr1 = words[2];
		 curr1 = curr1.toUpperCase();
		 curr2 = words[4];
		 curr2 = curr2.toUpperCase();
		 money = Integer.parseInt(words[1]);
		
	}
	public static boolean useList(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	@Override
	public Response setResponse()
	{
		String toReturn= "";
		String [] code = {"AED","ALL","AMD","ANG","AOA","ARS","AUD","AZN","BBD","BDT","BGN","BHD","BRL","BSD","BWP","BYN","CAD","CHF","CLP","CNY",
				"COP","CZK","DKK","DOP","DZD","EGP","ETB","EUR","FJD","GBP","GEL","GHS","GNF","GTQ","HKD","HNL","HRK","HUF","IDR","ILS",
				"INR","IQD","IRR","ISK","JMD","JOD","JPY","KES","KHR","KRW","KWD","KZT","LAK","LBP","LKR","MAD","MDL","MKD","MMK","MUR",
				"MXN","MYR","NAD","NGN","NOK","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON","RSD","RUB","SAR","SCR",
				"SEK","SGD","THB","TJS","TND","TRY","TTD","TWD","TZS","UAH","USD","UYU","UZS","VEF","VND","XAF","XCD","XOF","XPF","ZAR","ZMW"};
		if(!useList(code,curr1)) {
			toReturn = toReturn + "Invalid first currency";
			return new Response(toReturn);
		}
		if(!useList(code,curr2)) {
			toReturn = toReturn + "Invalid second currency";
 			return new Response(toReturn);
		}

		// Setting URL
				String url_str = "https://v3.exchangerate-api.com/pair/0740a52e087ae0a03ac5bcd5/"+curr1 +"/" +curr2;

				// Making Request
			    try {
					url = new URL(url_str);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				HttpURLConnection request = null;
				try {
					request = (HttpURLConnection) url.openConnection();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					request.connect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Convert to JSON
				JsonParser jp = new JsonParser();
				JsonElement root = null;
				try {
					root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
				} catch (JsonIOException | JsonSyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JsonObject jsonobj = root.getAsJsonObject();

				// Accessing object
				String rate = jsonobj.get("rate").getAsString();
				double result = Double.parseDouble(rate);			
				double convert = round(result*money,2) ;
				String finalCurr = Double.toString(convert);
				toReturn = toReturn + "Conversion from "+ money + " " +curr1 + " to " +curr2 +": " +finalCurr;
				return new Response(toReturn);
	}
	

}
