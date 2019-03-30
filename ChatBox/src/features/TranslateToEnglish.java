package features;

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
		return new Response(" ");
	}

	@Override
	public void parseQuery(String query)
	{
		for(String word: query.split(" "))
		{
			if(word.contains("\""))
			{
				getTranslation(word.substring(1, word.length()-1));
				break;
			}
		}
	}
	
	private void getTranslation(String toTranslate)
	{
		
	}
}
