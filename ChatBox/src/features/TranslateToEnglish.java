package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import configuration.Response;

public class TranslateToEnglish implements Feature
{
	private String englishString;
	
	public TranslateToEnglish(String query) 
	{
		parseQuery(query);
	}

	@Override
	public Response setResponse() 
	{
		return new Response(englishString);
	}

	@Override
	public void parseQuery(String query)
	{
		for(String word: query.split(" "))
		{
			if(word.contains("\""))
			{
				try 
				{
					getTranslation(word.substring(1, word.length()-1));
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
				break;
			}
		}
	}
	
	private void getTranslation(String toTranslate) throws IOException
	{
		String completeURL = "https://translation.googleapis.com/language/translate/v2?q=" + toTranslate.trim() + "&target=en&key=AIzaSyA9RVTfYhPbrTEzTmy47K3vy4QQe-2Bp7Q";
				
    	//Set up the URL connection and get data from it
		URL url = new URL(completeURL);
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        //Get the data from the JSON file that is returned by the URL
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String input;

        //Set the JSON data to a string called sb.
        while((input = br.readLine()) != null)
        {
            sb.append(input);
        }
        
    	//Creates a JSON element from the provided JSON string from GoogleTranslateAPI 
        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(sb.toString());
        
        if(tree.isJsonObject())
        {
        	JsonObject object = tree.getAsJsonObject();
            JsonElement data = object.get("data");
            JsonObject dataObject = data.getAsJsonObject();
            JsonElement translations = dataObject.get("translations");
            JsonElement info = translations.getAsJsonArray().get(0);
            JsonElement translatedText = info.getAsJsonObject().get("translatedText");
            JsonElement sourceLangAbbr = info.getAsJsonObject().get("detectedSourceLanguage");
            
            Locale loc = new Locale(sourceLangAbbr.getAsString());
            String sourceLangName = loc.getDisplayLanguage(loc);
            
            englishString = "Source Language: " + sourceLangName.toUpperCase().charAt(0) + sourceLangName.substring(1) + "\n" 
            				+ "English Translation: " + translatedText.getAsString();
        }
	}
}
