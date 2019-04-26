/**
 * Returns the definition of a word
 * @author Bernard Ang
 */
package features;
import configuration.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;



public class Definition implements Feature {

	String word = "";
	/**
	 * Constructs a new Definition object
	 * 
	 * @param query
	 * 		The query specified by the user
	 */
	public Definition(String query)
	{
		parseQuery(query);
	}
	/**
	 * Parses the word given by the user 
	 * @param query
	 * 	 The query specified by the user
	 */
	public void parseQuery(String query) {
		String[] words = query.split(" ");
		word = words[1];
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
		//Initializing results and converting word to lowercases as required
		String results = "";
		String word_id = word.toLowerCase(); 
		// Setting URL
		String url_str = "https://od-api.oxforddictionaries.com:443/api/v1/entries/en/"+word_id;
		// initializing API authorization data
		final String app_id = "f3fcf45c";
		final String app_key = "a884fde5a290e522101e6ac435e3ac52";
		
		
			//Making the Request
			URL url = null;
			try {
				url = new URL (url_str);
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
			HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			//Setting the properties for the request
			connection.setRequestProperty("Accept","application/json");
			connection.setRequestProperty("app_id",app_id);
			connection.setRequestProperty("app_key",app_key);
			try {
				connection.setRequestMethod("GET");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection.setDoOutput(true);
			// Convert to JSON
			JsonParser jp = new JsonParser();
			JsonElement root = null;
			try {
				root = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
			} catch (JsonIOException | JsonSyntaxException | IOException e) {
			
				e.printStackTrace();
			}
			//Parsing the Large JSON file acquired
			JsonObject jsonobj = root.getAsJsonObject();
			JsonArray jsonarr = jsonobj.getAsJsonArray("results");
			JsonObject jsonobj2 = (JsonObject) jsonarr.get(0);
			JsonArray jsonarr2 = jsonobj2.getAsJsonArray("lexicalEntries");
			JsonObject jsonobj3 = (JsonObject) jsonarr2.get(0);
			JsonArray jsonarr3 = jsonobj3.getAsJsonArray("entries");
			JsonObject jsonobj4 = (JsonObject) jsonarr3.get(0);
			JsonArray jsonarr4 = jsonobj4.getAsJsonArray("senses");
			JsonObject jsonobj5 = (JsonObject) jsonarr4.get(0);
			JsonArray jsonarr5 = jsonobj5.getAsJsonArray("definitions");
			//Assigning the json element to results
		     results = jsonarr5.get(0).toString();
		
			//Returning the response
		return new Response(results);
	}
}


