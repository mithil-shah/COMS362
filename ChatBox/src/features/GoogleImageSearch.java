package features;

public class GoogleImageSearch extends Feature
{

	public GoogleImageSearch(String query)
	{
		super(query);
		System.out.print(query);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String setResponse() 
	{
		// TODO Auto-generated method stub
		return "Hi Shreya";
	}

	@Override
	void parseQuery(String query) 
	{
		// TODO Auto-generated method stub
		
	}

}
