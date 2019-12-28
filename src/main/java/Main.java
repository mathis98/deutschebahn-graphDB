import dbpro.Graph;
import dbpro.JSONParser;
import dbpro.Station;
import dbpro.dbApiRequest;

import java.util.*;

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
            int hours = 0;
            int minutes = 0;
            NavigableMap<String, Stop> stopList = new TreeMap<>();
            while(hours < 24) {
                System.out.println("gettin stops for: " + hours + ":" + minutes);
                String departure = api.getDeparture(
                        station.getEvaID(),
                        "2019-12-27T" + String.format("%02d", hours) + ":" + String.format("%02d", minutes));
                stopList.putAll(
                        parser.parseStops(
                                departure,
                                new Station("Berlin Hbf", 8011160, null, null)
                        )
                );
                String timeString = stopList.lastEntry().getValue().getDepartureTime();
                System.out.println("largest time?: " + timeString);
                timeString = timeString.substring(11);
                hours = Integer.parseInt(timeString.substring(0,2));
                minutes = Integer.parseInt(timeString.substring(3));
                minutes += 1;
                if(minutes >= 60) {
                    minutes = 0;
                    hours += 1;
                }
            }

            // for each stop
            for(Map.Entry<String, Stop> stop : stopList.entrySet()) {
                // if journey doesn't exist in journeys array
                if (!journeys.containsKey(stop.getValue().getDetailsId())) {
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

