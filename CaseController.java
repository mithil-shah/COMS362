import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CaseController
{
    private String response;

    public CaseController(String query) throws Exception
    {
        HashMap<String, String> keywordsMap = new HashMap<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("keywords.txt"));

        while ((line = reader.readLine()) != null)
        {
            keywordsMap.put(line.split(": ")[0], line.split(": ")[1]);
        }

        for (String feature : keywordsMap.keySet())
        {
            if (query.toLowerCase().contains(feature.toLowerCase()))
            {
                Class<?> c = Class.forName("features." + keywordsMap.get(feature));
                Constructor<?> cons = c.getConstructor(String.class);
                Object object = cons.newInstance(query);
                Method method = object.getClass().getMethod("setResponse");
                response = (String) method.invoke(object);
            }
        }
    }

    public String getResponse()
    {
        return response;
    }

}
