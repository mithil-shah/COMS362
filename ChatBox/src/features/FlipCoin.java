/**
 * @author Bernard Ang
 */
package features;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class FlipCoin extends Feature {
	
	int number;
    public FlipCoin(String query)
    {
        super(query);
        parseQuery(query);
    }

	protected void parseQuery(String query) {
		String[] words = query.split(" ");
		number = Integer.parseInt(words[2]);
		
	}

	@Override
	public String setResponse()
	{
		String toReturn= "";
		String [] arr = {"Heads","Tail"};
		Random r = new Random();
		int count = 0;
		while(count < number) {
		String random =  arr[r.nextInt(arr.length)];
		count++;
		toReturn = toReturn +"Attempt " + count +" : " + random + "\n";
	}
		return toReturn;

	}
}
