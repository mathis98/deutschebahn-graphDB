import dbpro.Graph;
import dbpro.dbApiRequest;
import java.util.ArrayList;

public class Main
{

    static String password = "1111";
    static String uri = "bolt://localhost:7687";
    static String user = "neo4j";

    static String stationName = "Rom Hauptbahnhof";

    public static void main( String... args ) throws Exception
    {
        dbApiRequest api = new dbApiRequest();
        String ids = api.getIds();

        System.out.println(ids);

        try ( Graph graph = new Graph( uri, user, password ) )
        {
            graph.addStation("Von");
            graph.addStation("Nach");
            graph.addConnection("Von", "Nach");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}