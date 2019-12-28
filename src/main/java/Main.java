import dbpro.Graph;
import dbpro.JSONParser;
import dbpro.Station;
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
        String departure = api.getDeparture("8011160", "2019-12-27");

//      create a new instance of JSONParser
        JSONParser parser = new JSONParser();

        //args: JSON String
        //return List of Station classes
        ArrayList<Station> stationList = parser.parse(ids);

        stationList.stream().forEach(a -> System.out.println(a.getName()));
        System.out.println(departure);

       try ( Graph graph = new Graph( uri, user, password ) )
        {
//            stationList.stream().forEach(s -> graph.addStation(s.getName(),s.getEvaID(),s.getLongtitude(),s.getLatitude()));
           // graph.addStation("Von", 1);
           // graph.addStation("Nach", 2);
           // graph.addConnection("Von", "Nach");
           // graph.addStation("BahnhofA", 3);
           // graph.addStation("BahnhofB", 4);
           // graph.addConnection(3, 4);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

