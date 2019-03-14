package features;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ConverCurrency extends Feature {
	private URL url;
	String curr1,curr2;
	int money;
    public ConverCurrency(String query)
    {
        super(query);
        parseQuery(query);
    }

	protected void parseQuery(String query) {
//		String nonKeywords = "find" + "song" + "lyrics" + "the" + "to" + "get" + "for";
		String[] words = query.split(" ");
		 curr1 = words[2];
		 curr2 = words[4];
		 money = Integer.parseInt(words[1]);
		
	}
	
	@Override
	public String setResponse()
	{
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
				double convert = result*money ;
				String finalCurr = Double.toString(convert);
				String toReturn= "";
				toReturn = toReturn + "Conversion from "+ money + " " +curr1 + " to " +curr2 +":" +finalCurr;
				return toReturn;
	}
	

}
