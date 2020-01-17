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
                File directory = new File("./");
                System.out.println(directory.getAbsolutePath());
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