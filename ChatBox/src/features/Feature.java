/**
 * @author Mithil Shah
 */
package features;

public abstract class Feature
{
    private String query;

    public Feature(String query)
    {
        this.query = query;
    }

    abstract public String setResponse();
    abstract protected void parseQuery(String query);
}
