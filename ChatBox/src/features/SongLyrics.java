/**
 * @author Shreya Shankar
 */
package features;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SongLyrics extends Feature {
	private String response;

	public SongLyrics(String query) {
		super(query);
		parseQuery(query);
	}

	@Override
	public String setResponse() {
		return response;
	}

	private void parseHTML(String baseURL, String parameters) throws IOException {
		Document doc = Jsoup.connect(baseURL + parameters).get();
		HtmlToPlainText formatter = new HtmlToPlainText();

		String lyrics = "";

		for (Element row : doc.select("p")) {
			lyrics = formatter.getPlainText(row);
			break;
		}

		for (String s : lyrics.split("\n")) {
			lyrics = lyrics.replace(s, s.trim());

			if (s.contains("http")) {
				lyrics = lyrics.replace(s, "");
			}
		}

		response = lyrics;
	}

	@Override
	public void parseQuery(String query) {
		String nonKeywords = "find" + "song" + "lyrics" + "the" + "to" + "get" + "for";
		String[] words = query.split(" ");
		String title = "";
		String artist = "";

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

		try {
			parseHTML("https://genius.com/", (artist + title + "lyrics"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
