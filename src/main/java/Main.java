import dbpro.*;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;

public class Main {
    static String password = "1111";
    static String uri = "bolt://localhost:7687";
    static String user = "neo4j";
    public static ArrayList<Station> stationList;

    public static ArrayList<Integer[]> trackOrder = new ArrayList<>();

    public static Random random = new Random();

    public static boolean weather = true;
    public static boolean elevator = true;
    static boolean difficulties = true;
    public static int changeWeight = difficulties ? 10 : 5;

    public static int startID = 8000250;
    public static int endID = 8010159;

    public static void main(String... args) {

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
            } catch (Exception ignored) {

            }
        }

        // FOR DEBUGGING::
        //stationList.stream().forEach(a -> a.writeLineInfo());
        //stationList.stream().forEach(a -> System.out.println("Station: " + a.getEvaID() + " size of infoList: " + a.getLineInfoList().size()));

        //add weather data
        stationList.forEach(a -> {
            try {
                a.setWeather(owm.currentWeatherByCoords(a.getLatitude(), a.getLongitude()).getWeatherList().get(0).getConditionId());
            } catch (APIException e) {
                e.printStackTrace();
            }
        });

        //Create Stations and tracks
        try (Graph graph = new Graph(uri, user, password)) {

            //First delete all nodes
            graph.deleteAll();

            // add all stations
            for (Station s : stationList) {
                graph.addStation(s.getName(), s.getEvaID(), s.getLongitude(), s.getLatitude(), s.getWeather());

                // add all tracks
                for (Integer t : s.getTrackList()) {
                    graph.addTrack(t, random.nextInt(8) != 0, random.nextInt(3) != 0, s.getEvaID());
                }
            }

            //Now create connections between stations and their tracks
            graph.connectTracksToStations();

            //Now create connections between tracks
            for (int i = 0; i <= 100; i++) {
                for (Station s : stationList) {

                    if (s.getLineList().contains(i)) {
                        for (lineInfo info : s.getLineInfoList()) {
                            if (info.getLine() == i) {
                                trackOrder.add(new Integer[]{info.getTrack(), i, info.getOrder(), s.getEvaID(), info.getLine()});
                            }
                        }
                    }
                }

                for (int t = 0; t < trackOrder.size(); t++) {
                    if (t < (trackOrder.size() - 1)) {
                        graph.addConnection(trackOrder.get(t)[0], trackOrder.get(t)[3], trackOrder.get(t + 1)[0], trackOrder.get(t + 1)[3], trackOrder.get(t)[4]);
                    }
                }
                trackOrder.clear();
            }

            // add change relations to graph
            graph.addChanges(weather, elevator);

            // add weights to graph
            graph.addWeights(changeWeight);

            // add hasSpecial to be able to query connection in Graph
            graph.addSpecial(startID, endID);

            System.out.println("Creation of Graph was successful!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}