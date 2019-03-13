package features;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

/**
 * 
 * @author saishreyashankar
 *
 */

public class SongLyricSearch extends Feature {
	String songTitle = "";
	private final static String SONG_LYRICS_URL = "http://www.songlyrics.com";
	String artist="";
	
	 
//	   public static void main(String[] args) throws IOException {
//	      System.out.println(LyricsGatherer.getSongLyrics("U2", "With or Without You"));
//	      System.out.println(LyricsGatherer.getSongLyrics("Billy Joel", "Allentown"));
//	      System.out.println(LyricsGatherer.getSongLyrics("Tori Amos", "Winter"));
//	    }

	public SongLyricSearch(String query) {
		super(query);
		parseQuery(query);
	}

	@Override
	public String setResponse() {
		String toReturn= "";
		List<String> lyrics= new ArrayList<>();
		try {
			lyrics = SongLyricSearch.getSongLyrics(songTitle, artist);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i=0; i<lyrics.size();i++) {
			toReturn= toReturn+ "\n" + lyrics.get(i);
		}
			return toReturn;
		
	}

	@Override
	protected void parseQuery(String query) {
		query= query.replaceAll("\\s+", "");
		int toStopSubstring = query.length();
		
		
	    for(int i= 0; i<query.length(); i++) {
	    	if(query.charAt(i)==',') {
	    
	    		artist=query.substring(i+1,query.length());
	    		
	    		toStopSubstring=i;
	    	}
	    }
		
		songTitle = query.substring(0,toStopSubstring);
		
		if(songTitle.equals("")||artist.equals("")) {
			try {
				throw new Exception("No Artist or Song Title given");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static List<String> getSongLyrics( String title, String artist) throws IOException {
	     List<String> saveLyrics= new ArrayList<String>();
	 
	     Document doc = Jsoup.connect(SONG_LYRICS_URL+ "/"+artist.replace(" ", "-").toLowerCase()+"/"+title.replace(" ", "-").toLowerCase()+"-lyrics/").get();
	     String songTitle = doc.title();
	     System.out.println(songTitle);
	     Element p = doc.select("p.songLyricsV14").get(0);
	      for (Node e: p.childNodes()) {
	          if (e instanceof TextNode) {
	            saveLyrics.add(((TextNode)e).getWholeText());
	          }
	      }
	     return saveLyrics;
	   }
	
	
	
	

}
