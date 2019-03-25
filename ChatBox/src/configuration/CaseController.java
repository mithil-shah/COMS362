package configuration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 
 * @author Mithil Shah
 *
 */

public class CaseController
{
	/**
	 * Response provided to the user after query is processed. Null if it cannot be processed
	 */
    private Response response;

    /**
     * 
     * @param query
     * 		The query imposed by the user in the ChatBox.
     * @throws ClassNotFoundException 
     * 		The class from the features package is not found when parsing the user's query to try to match a keyword with a Class
     * @throws SecurityException 
     * 		Gives an error if package, class, constructor, or method, etc name is prohibited 
     * @throws NoSuchMethodException 
     * 		The method, setResponse(), is not found in the feature.Class class
     * @throws InvocationTargetException 
     * 		Thrown if a method or constructor cannot be invoked (maybe if it's not set to public/invalid parameters)
     * @throws IllegalArgumentException 
     * 		The method, setResponse(), cannot take in any parameters and the constructor must take in a String parameter.
     * @throws IllegalAccessException 
     * 		The method and constructors must be public in order to be accessible 
     * @throws InstantiationException 
     * 		The class from features.Class cannot be instantiated since it's not in the appropriate package or is private, etc.
     */
    public CaseController(String query) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
    	//Holds keywords and their classes from keywords.txt into a key, value pair map
        HashMap<String, String> keywordsMap = new HashMap<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("src/keywords.txt"));

        //Places keywords and their classes from keywords.txt into a key, value pair map
        while ((line = reader.readLine()) != null)
        {
            String [] keyValuePair = line.split(": ");
            keywordsMap.put(keyValuePair[0], keyValuePair[1]);
        }
        
        //If a keyword matches a pair in the HashMap go to that pair. In this case, pair is feature.Class.
        for (String feature : keywordsMap.keySet())
        {
            if (query.toLowerCase().contains(feature.toLowerCase()))
            {
                Class<?> c = Class.forName("features." + keywordsMap.get(feature));
                Constructor<?> cons = c.getConstructor(String.class);
                Object object = cons.newInstance(query);
                Method method = object.getClass().getMethod("setResponse");
                response = (Response) method.invoke(object);
            }
        }
        
        //Close the BufferedReader to prevent leaks
        reader.close();
    }

    /**
     * 
     * @return the response to the user's query given by the visited feature.Class
     */
    public Response getResponse()
    {
        return response;
    }

}
