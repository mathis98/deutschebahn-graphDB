package dbpro;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class JSONParser {

    public ArrayList<Station> parseJson(FileReader reader, String line) throws Exception {

        ArrayList<Station> stationList = new ArrayList<>();

        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        Object json = parser.parse(reader);
        JSONObject stationData = (JSONObject) json;

        try {

            JSONArray stations = (JSONArray) stationData.get("stations");

            System.out.println(stations.toString());

            int order = 0;

            for (Object station : stations) {

                JSONObject dataObj = (JSONObject) station;
                JSONObject trackObj = (JSONObject) dataObj.get("track");


                String stationName = (String) dataObj.get("stationName");
                long stationEva = (long) dataObj.get("evalID");
                String lat = dataObj.get("lat").toString();
                String lon = dataObj.get("long").toString();
                long trackNr = (long) trackObj.get("trackNumber");
                int lineNr = Integer.parseInt(line);

                stationList.add(new Station(stationName, stationEva, lon, lat, lineNr, trackNr, order));

                order++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        stationList.stream().forEach(a -> System.out.println(a.getLineInfoList().get(0).toString()));

        return stationList;
    }

    public ArrayList<Stop> parseStops(String jsonString, Station station) throws Exception {
        ArrayList<Stop> stopList = new ArrayList<>();
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONArray stopData = (JSONArray) parser.parse(jsonString);

        try {
            for (Object stopDatum : stopData) {
                JSONObject dataObj = (JSONObject) stopDatum;
                String trackString = (String) dataObj.get("track");
                int track;
                if (trackString != null) {
                    track = Integer.parseInt(trackString.substring(0, 1));
                } else continue;

                String departureTime = (String) dataObj.get("dateTime");
                String trainName = (String) dataObj.get("name");
                String detailsId = (String) dataObj.get("detailsId");

                stopList.add(new Stop(station, track, null, departureTime, trainName, detailsId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Stop can't be parsed");
        }
        return stopList;
    }
}


//    public ArrayList<Station> parse (String jsonString) throws Exception{
//
//        ArrayList<Station> stationList = new ArrayList<>();
//
//        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
//        JSONObject json = (JSONObject) parser.parse(jsonString);
//        JSONArray stationData = (JSONArray) json.get("result");
//
//        try{
//            //get Stationnames
//            for (Object stationDatum : stationData) {
//                JSONObject dataObj = (JSONObject) stationDatum;
//                String stationName = (String) dataObj.get("name");
//
//
//                //get Evaids
//                JSONArray stationEva = (JSONArray) dataObj.get("evaNumbers");
//                JSONObject dataObj1 = (JSONObject) stationEva.get(0);
//                long stationEvaNumber = (Long) dataObj1.get("number");
//
//
//                //get Coords
//                JSONObject dataObj2 = (JSONObject) dataObj1.get("geographicCoordinates");
//                JSONArray coordinates;
//                String longtitude = "0";
//                String latitude = "0";
//                if(dataObj2.containsKey("coordinates")) {
//                    coordinates = (JSONArray) dataObj2.get("coordinates");
//                    longtitude = coordinates.get(0).toString();
//                    latitude = coordinates.get(1).toString();
//                }
//                stationList.add(new Station(stationName, stationEvaNumber, longtitude, latitude));
//            }
//        }
//            catch(Exception e){
//                System.out.println("A station cant be parsed.");
////                System.out.println(jsonString);
//                e.printStackTrace();
//            }
//        return stationList;
//    }
//        JSONArray employeeList = (JSONArray) obj;

//        try{
//            //get Stationnames
//            for (Object stationDatum : stationData) {
//                JSONObject dataObj = (JSONObject) stationDatum;
//                String stationName = (String) dataObj.get("name");
//
//
//                //get Evaids
//                JSONArray stationEva = (JSONArray) dataObj.get("evaNumbers");
//                JSONObject dataObj1 = (JSONObject) stationEva.get(0);
//                long stationEvaNumber = (Long) dataObj1.get("number");
//
//
//                //get Coords
//                JSONObject dataObj2 = (JSONObject) dataObj1.get("geographicCoordinates");
//                JSONArray coordinates;
//                String longtitude = "0";
//                String latitude = "0";
//                if(dataObj2.containsKey("coordinates")) {
//                    coordinates = (JSONArray) dataObj2.get("coordinates");
//                    longtitude = coordinates.get(0).toString();
//                    latitude = coordinates.get(1).toString();
//                }
//                stationList.add(new Station(stationName, stationEvaNumber, longtitude, latitude));
//            }
//        }
//        catch(Exception e){
//            System.out.println("A station cant be parsed.");
////                System.out.println(jsonString);
//            e.printStackTrace();
//        }