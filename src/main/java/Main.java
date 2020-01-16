import dbpro.Graph;
import dbpro.JSONParser;
import dbpro.Station;
import dbpro.dbApiRequest;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import dbpro.Stop;
import dbpro.Journey;

public class Main {
    static String password = "1111";
    static String uri = "bolt://localhost:7687";
    static String user = "neo4j";


    public static ArrayList<Station> stationList;


    public static void main(String... args) throws Exception {
        // List for storing all the journeys
        HashMap<String, Journey> journeys = new HashMap<>();

        // new dbAPIRequest
        dbApiRequest api = new dbApiRequest();

        // create a new instance of JSONParser
        JSONParser parser = new JSONParser();

        /*
        GET STATION DATA
         */
        for (int i = 0; i <= 100; i++) {

            String line = String.valueOf(i);

            try {
                File file = new File(String.format("D:\\Dev\\Java\\DBPRO\\src\\main\\java\\dbpro\\json\\%s.json", line));

                FileReader reader = new FileReader(file);

                stationList = parser.parseJson(reader, line);


            } catch (Exception e) {

            }
        }
        stationList.stream().forEach(a -> a.writeLineInfo());
         stationList.stream().forEach(a -> System.out.println("Station: " + a.getEvaID() + " size of infoList: " + a.getLineInfoList().size()));
    }
}









//        String ids = dbApiRequest.getIds();
//        System.out.println(ids);
//        ArrayList<Station> stationList = parser.parse(ids);
//        System.out.println("length: " + stationList.size());
//
////         print result
//        System.out.println("STATIONS: ");
//        stationList.forEach(a -> System.out.println(a.toString()));
//        System.out.println("\n");

    // for each station
//        for(Station station : stationList) {
//            System.out.println("Departures from " + station.getName());
//            /*
//            GET DEPARTURE INFO
//             */
//            // TODO: this api request only returns 20 stops! loop?
//            int hours = 0;
//            int minutes = 0;
//            ArrayList<Stop> stopList = new ArrayList<>();
//            int oldHours = -1;
//            int oldMinutes = -1;
//            while(hours < 24) {
//                String departure = api.getDeparture(
//                        station.getEvaID(),
//                        "2019-12-27T" + String.format("%02d", hours) + ":" + String.format("%02d", minutes));
//                stopList.addAll(
//                        parser.parseStops(
//                                departure,
//                                new Station("Berlin Hbf", 8011160, null, null)
//                        )
//                );
//                if(stopList.size() == 0) break;
//                Collections.sort(stopList);
//                String timeString = stopList.get(stopList.size() - 1).getDepartureTime();
//                timeString = timeString.substring(11);
//                hours = Integer.parseInt(timeString.substring(0,2));
//                minutes = Integer.parseInt(timeString.substring(3));
//                minutes += 1;
//                if(minutes >= 60) {
//                    minutes = 0;
//                    hours += 1;
//                }
//                if(oldHours == hours && oldMinutes == minutes) break;
//                oldHours = hours;
//                oldMinutes = minutes;
//            }
//
//            System.out.println("got " + stopList.size() + " stops");
//            if(stopList.size() == 0) {
//                System.out.println("so we can skip this station");
//                continue;
//            }
//            System.out.println("");
//
//            // for each stop
//            for(Stop stop: stopList) {
//                // if journey doesn't exist in journeys array
//                if (!journeys.containsKey(stop.getDetailsId())) {
//                    // get journey from /journeyDetails and add as new journey
//                }
//            }
//
//            // add track if necessary
//        }
//
//        /*
//        ADD EVERYTHING TO THE GRAPH/ UPDATE GRAPH
//         */
//       try ( Graph graph = new Graph( uri, user, password ) )
//       {
//           // add stations to graph
////           stationList.forEach(s -> graph.addStation(s.getName(),s.getEvaID(),s.getLongtitude(),s.getLatitude()));
//
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }


