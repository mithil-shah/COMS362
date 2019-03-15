package features;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author Shreya Shankar
 *
 */
public class SongLyrics extends Feature {
	/**
	 * The lyrics to the song requested by the user
	 */
	private String response;

	/**
	 * Takes in a user's query and calls parseQuery() to find the name of the artist and song
	 * @param query
	 * 		The query provided by the user
	 */
	public SongLyrics(String query) {
		super(query);
		parseQuery(query);
	}

	/**
	 * 
	 * @return response
	 * 		The lyrics to the song
	 */
	@Override
	public String setResponse() {
		return response;
	}

	/**
	 * Parses the HTML page returned by genius.com to find the lyrics to the song
	 * 
	 * @param baseURL
	 * 		https://genius.com/
	 * @param parameters
	 * 		Include the name of the artist and song in the format of artist-name-song-name
	 * @throws IOException
	 * 		This exception is thrown if the document cannot be parsed or if HTML returns a 404 error
	 */
	private void parseHTML(String baseURL, String parameters) throws IOException {
		
		//Let Jsoup connect to the URL and get an HTML page returned
		Document doc = Jsoup.connect(baseURL + parameters).get();
		
		//Format the HTML elements to get rid of HTML code and just get English
		HtmlToPlainText formatter = new HtmlToPlainText();

		//The lyrics to the song
		String lyrics = "";

		//Find the p element in the HTML page since this is where the song lyrics are located
		//For every row in p, append it to the lyrics string above.
		for (Element row : doc.select("p")) {
			lyrics = formatter.getPlainText(row);
			break;
		}

		//For every new line in the lyrics string, trim it to get rid of whitespaces and to make it look pretty
		for (String s : lyrics.split("\n")) {
			lyrics = lyrics.replace(s, s.trim());

			//Try to get rid of the external links... sometimes doesn't work
			if (s.contains("http")) {
				lyrics = lyrics.replace(s, "");
			}
		}

		//The response is the lyrics
		response = lyrics;
	}

	/**
	 * Parses the query provided by the user for the name of the artist and song.
	 * Then calls the parseHTML() method
	 * 
	 * @param query
	 * 		The query provided by the user. 
	 */
	@Override
	public void parseQuery(String query) {
		//These are words that are probably not the name of the song or artist
		String nonKeywords = "find" + "song" + "lyrics" + "the" + "to" + "get" + "for";
		//The query is split into keywords
		String[] words = query.split(" ");
		
		//Song info
		String title = "";
		String artist = "";

		//The query must contain the artist's name after "by", since the query should be formatted as Song Name by Artist Name
		//Once this data has been parsed, add dashes in between the spaces since this is the formatting of the parameters for genuius's website 
		for (int i = 0; i < words.length; i++) {
			if (nonKeywords.indexOf(words[i].toLowerCase()) < 0 && !words[i].equals("by")) {
				title += words[i] + "-";
			} else if (nonKeywords.indexOf(words[i].toLowerCase()) < 0 && words[i].equals("by")) {
				for (int j = i + 1; j < words.length; j++) {
					artist += words[j] + "-";
				}
				break;
			} 
		}

		//Call parseHTML 
		try {
			parseHTML("https://genius.com/", (artist + title + "lyrics"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
