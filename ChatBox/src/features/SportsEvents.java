package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import configuration.Response;

public class SportsEvents implements Feature
{
	private String response = "";
	
	public SportsEvents(String query)
	{
		parseQuery(query);
	}
	
	@Override
	public Response setResponse()
	{
		return new Response(response);
	}

	@Override
	public void parseQuery(String query)
	{
		ArrayList<Integer> sportIDs = new ArrayList<>();
		
		for(String word: query.split(" "))
		{
			switch(word)
			{
				case "NCAAF" : sportIDs.add(1); break;
				case "NFL" : sportIDs.add(2); break;
				case "MLB" : sportIDs.add(3); break;
				case "NBA" : sportIDs.add(4); break;
				case "NCAAB" : sportIDs.add(5); break;
				case "NHL" : sportIDs.add(6); break;
			}
		}
		
		try
		{
			getEventsForSports(sportIDs);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void getEventsForSports(ArrayList<Integer> sportIDs) throws IOException
	{
		for(int id: sportIDs)
		{
			//TheRundown API
			URL url = new URL("https://therundown-therundown-v1.p.rapidapi.com/sports/" + id + "/events?include=all_periods%2C+scores%2C+and%2For+teams");

			// Go to the URL specified above (TheRundown API)
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("X-RapidAPI-Host", "therundown-therundown-v1.p.rapidapi.com");
			connection.setRequestProperty("X-RapidAPI-Key", "7BXrFaxmuJmsh4ol2zPl7QYsyOmOp15OlSsjsnNvfuB3IEEcn2");

			// Put the JSON object that is returned into a string
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String input;

			while ((input = br.readLine()) != null) {
				sb.append(input);
			}

			// Close the BufferedReader to prevent leaks.
			br.close();
			
			try
			{
				parseJSONResponse(sb.toString());
			}
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void parseJSONResponse(String response) throws ParseException 
	{
		// Creates a JSON element from the provided JSON string from News API
		JsonParser parser = new JsonParser();
		JsonObject data = parser.parse(response).getAsJsonObject();
		
		// Gets the events found in the JSON string
		JsonArray events = data.getAsJsonArray("events");
		
		//For all the events
		for(JsonElement event: events)
		{
			//Find the date of the event
			String dateFound = event.getAsJsonObject().get("event_date").getAsString();
			String dateParsed = dateFound.split("T")[0];
			
			//Parse the date
			SimpleDateFormat inDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat outDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String date = outDateFormat.format(inDateFormat.parse(dateParsed));
			
			String timeFound = dateFound.split("T")[1];
					
			//Find the teams in this event
			JsonArray teams = event.getAsJsonObject().getAsJsonArray("teams");
			String team1 = teams.get(0).getAsJsonObject().get("name").getAsString();
			String team2 = teams.get(1).getAsJsonObject().get("name").getAsString();
		
			//Format (example) = 04/22/2019: Philadelphia Phillies vs. New York Mets @ 23:10:00 UTC
			this.response += (date + ": " + team1 + " vs. " + team2 + " @ " + timeFound.substring(0, timeFound.length()-1) + " UTC") + "\n";
		}
		this.response += "\n";
	}
}
