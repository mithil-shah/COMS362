package features;

import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import configuration.Response;

/**
 * Retrieves specific number of photos regarding a specific topic
 * 
 * @author Shreya Shankar
 *
 */

public class UnsplashSearch implements Feature {
	/**
	 * The search query
	 */
	private String toSearch;
	/**
	 * The number of images to return
	 */
	private int numLinksToReturn;
	/**
	 * The images that are returned in an arraylist
	 */
	private ArrayList<BufferedImage> results;

	/**
	 * Constructs a new UnsplashSearch object where variables above are initialized,
	 * query is parsed, and then images are retrieved
	 * 
	 * @param query The query provided by the user
	 */
	public UnsplashSearch(String query) {
		toSearch = "";
		numLinksToReturn = -1;
		results = new ArrayList<>();
		parseQuery(query);

		if (!toSearch.equals("") && numLinksToReturn > 0) {
			try {
				getImages(toSearch, numLinksToReturn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Retrieves images based off the topic specified by the user and the number of
	 * images specified by the user
	 * 
	 * @param toSearch     The category of the image
	 * @param numberOfPics Number of images to return
	 * @throws IOException Thrown if API cannot properly process the request
	 */
	public void getImages(String toSearch, int numberOfPics) throws IOException {
		String searchUrl = "https://api.unsplash.com/search/photos?page=1&per_page=" + numberOfPics + "&query="
				+ toSearch + "&client_id=84023d7dd975661fbade252d0a70f9bb401be3af6d272c7913c4cd2c66c13524";

		// Set up the URL connection and get data from it
		URL url = new URL(searchUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		// Get the data from the JSON file that is returned by the URL
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String input;

		// Set the JSON data to a string called sb.
		while ((input = br.readLine()) != null) {
			sb.append(input);
		}

		parseJSON(sb.toString());
	}

	/**
	 * Parses the JSON returned by the Unsplash API
	 * 
	 * @param response The JSON string retrieved in getImages()
	 */
	private void parseJSON(String response) {
		// Make the JSON string into an object using Gson Library
		JsonParser parser = new JsonParser();
		JsonObject data = parser.parse(response).getAsJsonObject();

		// Find the array in the JSON file called "results"
		JsonArray results = data.getAsJsonArray("results");

		// Holds the URLs of the images found
		ArrayList<String> imagePaths = new ArrayList<>();

		// Get and store the URLs of small thumbnails
		for (JsonElement result : results) {
			JsonObject currentResult = result.getAsJsonObject();

			JsonElement urls = currentResult.get("urls");
			JsonElement urlSmall = urls.getAsJsonObject().get("small");
			imagePaths.add(urlSmall.getAsString());
		}

		fetchImages(imagePaths);
	}

	/**
	 * Fetches the images from the URLs
	 * 
	 * @param paths The paths of the images found in parseJSON()
	 */
	private void fetchImages(ArrayList<String> paths) {
		URL url;
		ArrayList<BufferedImage> images = new ArrayList<>();

		// Get the images from the URL and convert them into BufferedImage
		for (String path : paths) {
			try {
				url = new URL(path);
				images.add(ImageIO.read(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		results = images;
	}

	/**
	 * Shows the images found to the user
	 * 
	 * @return Response The images found by the API
	 */
	@Override
	public Response setResponse() {
		return new Response(results);
	}

	/**
	 * Parses the query for the topic to search and number of images to return
	 * 
	 * @param query The query provided by the user
	 */
	@Override
	public void parseQuery(String query) {
		// These are words that are probably not the search term
		String nonKeywords = "search" + "image";

		// The query is split into keywords
		String[] words = query.split(" ");

		// Gets the search term provided by the user by avoiding all words in the
		// nonKeywords string.
		for (int i = 0; i < words.length - 1; i++) {

			if (!nonKeywords.contains(words[i])) {
				toSearch = words[i].substring(0, words[i].length() - 1);
			}
		}

		numLinksToReturn = Integer.parseInt(words[words.length - 1]);
	}
}
