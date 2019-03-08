package features;

public abstract class Feature
{
    private String query;

    public Feature(String query)
    {
        this.query = query;
    }

    abstract String setResponse();
    abstract void parseQuery();
}
