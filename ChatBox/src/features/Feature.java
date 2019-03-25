package features;
import configuration.Response;

/**
 * 
 * @author Mithil Shah
 *
 */
public abstract class Feature
{
	/**
	 * The query provided by the user
	 */
    private String query;

    /**
     * Assigns the user's query to the instance above to be used in parseQuery
     * 
     * @param query 
     * 		The query provided by the user
     */
    public Feature(String query)
    {
        this.query = query;
    }

    /**
     * The visited feature.Class returns a response based off of how it parses and interprets the query. 
     * This response is passed on to the CaseController and then the UI, where the UI prints the response to the console 
     * 
     * @return response
     * 		The response to the user's query by features.Class
     */
    abstract public Response setResponse();
    
    /**
     * This method handles the keywords that a specific features.Class might need as a parameter in order to provide a more detailed response
     * 
     * @param query
     * 		The query given by the user
     */
    abstract protected void parseQuery(String query);
}
