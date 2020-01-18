import dbpro.*;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import org.neo4j.unsafe.impl.batchimport.stats.Stat;

public class Main {
    static String password = "1111";
    static String uri = "bolt://localhost:7687";
    static String user = "neo4j";
    public static ArrayList<Station> stationList;

    public static ArrayList<Integer[]> trackOrder = new ArrayList<Integer[]>();
    public static lineInfo start;
    public static lineInfo end;


    public static void main(String... args) throws Exception {
        // List for storing all the journeys
        HashMap<String, Journey> journeys = new HashMap<>();

        // new dbAPIRequest
        dbApiRequest api = new dbApiRequest();

        // openWeatherMap
        OWM owm = new OWM("b633ef2b75898546869eb47513134043");

        // create a new instance of JSONParser
        JSONParser parser = new JSONParser();

        /*
        GET STATION DATA
         */
        for (int i = 0; i <= 100; i++) {

            String line = String.valueOf(i);

            try {
                File file = new File(String.format("src/main/java/dbpro/json/%s.json", line));

                FileReader reader = new FileReader(file);

                stationList = parser.parseJson(reader, line);


            } catch (Exception e) {

            }
        }
        //stationList.stream().forEach(a -> a.writeLineInfo());
        //stationList.stream().forEach(a -> System.out.println("Station: " + a.getEvaID() + " size of infoList: " + a.getLineInfoList().size()));

        //add weather data
        stationList.stream().forEach(a -> {
            try {
                a.setWeather(owm.currentWeatherByCoords(a.getLatitude(), a.getLongitude()).getWeatherList().get(0).getMainInfo());
            } catch (APIException e) {
                e.printStackTrace();
            }
        });

        //Create Stations and tracks
        try (Graph graph = new Graph(uri, user, password)) {

//            for (Station s : stationList) {
//                graph.addStation(s.getName(), s.getEvaID(), s.getLongitude(), s.getLatitude(), true);
//
//                for (Integer t : s.getTrackList()) {
//                    graph.addTrack(t, true, true, s.getEvaID());
//                }
//            }
//
//            //Now create connections between stations and their tracks
//            graph.connectTracksToStations();


            //graph.addConnection(3, 8002549, 10, 8000098);

            //Now create connections between tracks
            for (int i = 0; i <= 100; i++) {
                for (Station s : stationList) {

                    if (s.getLineList().contains(i)) {
                        for (lineInfo info : s.getLineInfoList()) {
                            if (info.getLine() == i) {
                                trackOrder.add(new Integer[]{info.getTrack(), i, info.getOrder()});
                            }
                        }
                    }
                }

                for(int t = 0; t < trackOrder.size(); t++){
                    System.out.println("" + t + "  " + trackOrder.size());
                }
                trackOrder.clear();
            }
            //trackOrder.stream().forEach(a -> System.out.println("line: " + a[1] + " track: " + a[0] + " order: " + a[2]));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}