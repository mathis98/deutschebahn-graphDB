import dbpro.Graph;

public class Main
{

    static String password = "1111";
    static String uri = "bolt://localhost:7687";
    static String user = "neo4j";

    static String stationName = "Rom Hauptbahnhof";

    public static void main( String... args ) throws Exception
    {
        try ( Graph graph = new Graph( uri, user, password ) )
        {
            graph.addStation(stationName);
        }
    }
}