package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import configuration.Response;

public class Jokes implements Feature
{
	String keyword= " ";
	String num = "";
	public Jokes(String query)
	{
		parseQuery(query);
	}
	
	@Override
	public void parseQuery(String query)
	{
		String[] words = query.split(" ");
		keyword = words[5];
		num = words[2];
	}
	

	/**
	 * Returns the definition of the word given by the user
	 * 
	 * @return Response
	 * 		The definition of the word given by the user
	 * 
	 */
	@Override
	public Response setResponse()
	{
		String toReturn= "";
		//Jokes API
		URL url = null;
		try {
			url = new URL("https://webknox-jokes.p.rapidapi.com/jokes/search?minRating=7&numJokes="+num+"&keywords="+keyword);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Go to the URL specified above (TheRundown API)
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.setRequestProperty("X-RapidAPI-Host", "webknox-jokes.p.rapidapi.com");
		connection.setRequestProperty("X-RapidAPI-Key", "1023b32db7mshb3a8138b859c8dcp1f0705jsn7188008e7712");
			//Parsing the Large JSON file acquired
			JsonParser jp = new JsonParser();
			JsonElement root = null;
			try {
				root = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
			} catch (JsonIOException | JsonSyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JsonArray jsonarr = root.getAsJsonArray();
			
			for( int i = 0; i < jsonarr.size();i++) {
			JsonObject jsonobj = (JsonObject) jsonarr.get(i);
			JsonElement jokes = jsonobj.get("joke");
			String joke = jokes.toString();
			toReturn = toReturn + "Joke "+(i+1)+": \n" + joke + "\n"; 
			}
			
		   
		
			//Returning the response
		return new Response(toReturn);
	}

	
}

