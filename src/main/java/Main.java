import dbpro.Graph;
import dbpro.Station;
import dbpro.dbApiRequest;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

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

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(ids);
        JSONArray stationData = (JSONArray) json.get("result");

        for(int i=0; i<stationData.size(); i++) {
            JSONObject dataObj = (JSONObject) stationData.get(i);
            String stationName = (String) dataObj.get("name");
            System.out.println(stationName);


            JSONArray stationEva = (JSONArray) dataObj.get("evaNumbers");
            JSONObject dataObj1 = (JSONObject) stationEva.get(0);
            long stationEvaNumber = (Long) dataObj1.get("number");
            System.out.println(stationEvaNumber);


            JSONObject dataObj2 = (JSONObject) dataObj1.get("geographicCoordinates");
            JSONArray coordinates = (JSONArray) dataObj2.get("coordinates");
            String longtitude = (String) coordinates.get(0).toString();
            String latitude = (String) coordinates.get(1).toString();

            //System.out.println(coordinates.get(0));

            System.out.println("long: " + longtitude + " lat: " + latitude);

        }

       // System.out.println(ids);

      /*  try ( Graph graph = new Graph( uri, user, password ) )
        {
            graph.addStation("Von", 1);
            graph.addStation("Nach", 2);
            graph.addConnection("Von", "Nach");
            graph.addStation("BahnhofA", 3);
            graph.addStation("BahnhofB", 4);
            graph.addConnection(3, 4);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
}