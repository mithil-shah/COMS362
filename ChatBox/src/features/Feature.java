package features;
import configuration.Response;

/**
 * 
 * @author Mithil Shah
 *
 */
public interface Feature
{
    /**
     * The visited feature.Class returns a response based off of how it parses and interprets the query. 
     * This response is passed on to the CaseController and then the UI, where the UI prints the response to the console 
     * 
     * @return response
     * 		The response to the user's query by features.Class
     */
    public Response setResponse();
    
    /**
     * This method handles the keywords that a specific features.Class might need as a parameter in order to provide a more detailed response
     * 
     * @param query
     * 		The query given by the user
     */
     void parseQuery(String query);
}
