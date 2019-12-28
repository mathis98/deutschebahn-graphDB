import dbpro.Graph;
import dbpro.JSONParser;
import dbpro.Station;
import dbpro.dbApiRequest;
import java.util.ArrayList;
import java.util.HashMap;

import dbpro.Stop;
import dbpro.Journey;

public class Main
{
    static String password = "1111";
    static String uri = "bolt://localhost:7687";
    static String user = "neo4j";

    public static void main( String... args ) throws Exception
    {
        // List for storing all the journeys
        HashMap<String, Journey> journeys = new HashMap<>();

        // new dbAPIRequest
        dbApiRequest api = new dbApiRequest();

        // create a new instance of JSONParser
        JSONParser parser = new JSONParser();

        /*
        GET STATION DATA
         */
        String ids = dbApiRequest.getIds();
        ArrayList<Station> stationList = parser.parse(ids);

        // print result
        System.out.println("STATIONS: ");
        stationList.forEach(a -> System.out.println(a.toString()));
        System.out.println("\n");

        // for each station
        for(Station station : stationList) {
            /*
            GET DEPARTURE INFO
             */
            // TODO: this api request only returns 20 stops! loop?
            String departure = api.getDeparture(station.getEvaID(), "2019-12-27");
            ArrayList<Stop> stopList = parser.parseStops(departure, new Station("Berlin Hbf", 8011160, null, null));

            // for each stop
            for(Stop stop : stopList) {
                // if journey doesn't exist in journeys array
                if (!journeys.containsKey(stop.getDetailsId())) {
                    // get journey from /journeyDetails and add as new journey
                }
            }

            // add track if necessary
        }

        /*
        ADD EVERYTHING TO THE GRAPH/ UPDATE GRAPH
         */
       try ( Graph graph = new Graph( uri, user, password ) )
       {
           // add stations to graph
//           stationList.forEach(s -> graph.addStation(s.getName(),s.getEvaID(),s.getLongtitude(),s.getLatitude()));

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

