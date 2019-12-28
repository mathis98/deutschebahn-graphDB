package dbpro;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class JSONParser {

    public ArrayList<Station> parse (String jsonString) throws Exception{

        ArrayList<Station> stationList = new ArrayList<>();

        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);
        JSONArray stationData = (JSONArray) json.get("result");

        try{
            //get Stationnames
            for (Object stationDatum : stationData) {
                JSONObject dataObj = (JSONObject) stationDatum;
                String stationName = (String) dataObj.get("name");


                //get Evaids
                JSONArray stationEva = (JSONArray) dataObj.get("evaNumbers");
                JSONObject dataObj1 = (JSONObject) stationEva.get(0);
                long stationEvaNumber = (Long) dataObj1.get("number");


                //get Coords
                JSONObject dataObj2 = (JSONObject) dataObj1.get("geographicCoordinates");
                JSONArray coordinates = (JSONArray) dataObj2.get("coordinates");
                String longtitude = coordinates.get(0).toString();
                String latitude = coordinates.get(1).toString();

                stationList.add(new Station(stationName, stationEvaNumber, longtitude, latitude));
            }
        }
            catch(Exception e){

                System.out.println("A station cant be parsed.");
                e.printStackTrace();
            }
        return stationList;
    }

    public HashMap<String, Stop> parseStops (String jsonString, Station station) throws Exception {
        HashMap<String, Stop> stopList = new HashMap<>();
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONArray stopData = (JSONArray) parser.parse(jsonString);

        try {
            for (Object stopDatum : stopData) {
                JSONObject dataObj = (JSONObject) stopDatum;
                String trackString = (String) dataObj.get("track");
                int track;
                if(trackString != null) {
                    track = Integer.parseInt(trackString);
                }
                else continue;

                String departureTime = (String) dataObj.get("dateTime");
                String trainName = (String) dataObj.get("name");
                String detailsId = (String) dataObj.get("detailsId");

                stopList.put(detailsId, new Stop(station, track, null, departureTime, trainName, detailsId));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Stop can't be parsed");
        }
        return stopList;
    }
}
