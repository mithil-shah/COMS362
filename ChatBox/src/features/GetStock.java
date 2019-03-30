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
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GetStock implements Feature {
	private URL url;
	String curr1,curr2;
	int money;
    public GetStock(String query)
    {
        parseQuery(query);
    }

	public void parseQuery(String query) {
		String[] words = query.split(" ");
		 curr1 = words[4];
		 curr1 = curr1.toUpperCase();
		
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
//		String [] code = {"AED","ALL","AMD","ANG","AOA","ARS","AUD","AZN","BBD","BDT","BGN","BHD","BRL","BSD","BWP","BYN","CAD","CHF","CLP","CNY",
//				"COP","CZK","DKK","DOP","DZD","EGP","ETB","EUR","FJD","GBP","GEL","GHS","GNF","GTQ","HKD","HNL","HRK","HUF","IDR","ILS",
//				"INR","IQD","IRR","ISK","JMD","JOD","JPY","KES","KHR","KRW","KWD","KZT","LAK","LBP","LKR","MAD","MDL","MKD","MMK","MUR",
//				"MXN","MYR","NAD","NGN","NOK","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON","RSD","RUB","SAR","SCR",
//				"SEK","SGD","THB","TJS","TND","TRY","TTD","TWD","TZS","UAH","USD","UYU","UZS","VEF","VND","XAF","XCD","XOF","XPF","ZAR","ZMW"};
//		if(!useList(code,curr1)) {
//			toReturn = toReturn + "Invalid first currency";
//			return toReturn;
//		}

		// Setting URL
		// Setting URL
		String url_str = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+curr1+"&apikey=0HHOUBQ266WGIU03";

		// Making Request
		try {
			url = new URL(url_str);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
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
			String symbol = jsonobj.getAsJsonObject("Global Quote").get("01. symbol").getAsString();
			String open = jsonobj.getAsJsonObject("Global Quote").get("02. open").getAsString();
			String high = jsonobj.getAsJsonObject("Global Quote").get("03. high").getAsString();
			String low = jsonobj.getAsJsonObject("Global Quote").get("04. low").getAsString();
			String price = jsonobj.getAsJsonObject("Global Quote").get("05. price").getAsString();
			String volume = jsonobj.getAsJsonObject("Global Quote").get("06. volume").getAsString();
			String trading = jsonobj.getAsJsonObject("Global Quote").get("07. latest trading day").getAsString();
			String close = jsonobj.getAsJsonObject("Global Quote").get("08. previous close").getAsString();
			String change = jsonobj.getAsJsonObject("Global Quote").get("09. change").getAsString();
			String changep= jsonobj.getAsJsonObject("Global Quote").get("10. change percent").getAsString();
				toReturn = toReturn + " Stock Symbol: "+symbol+ "\n Open: "+open+ "\n High: "+high+ "\n Low: "+low
						+ "\n Price: "+price+ "\n Volume: "+volume+ "\n Latest Trading Day: "+trading
						+ "\n Previous Close: "+close+ "\n Change: "+change + "\n Change percent: "+changep;
				return new Response(toReturn);
	}
	

}
