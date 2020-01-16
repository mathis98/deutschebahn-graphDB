package dbpro;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.neo4j.unsafe.impl.batchimport.stats.Stat;

import java.io.FileReader;
import java.util.ArrayList;



public class JSONParser {

    public ArrayList<Station> stationList = new ArrayList<>();

    public int flag = 0;



    public ArrayList<Station> parseJson(FileReader reader, String line) throws Exception {

        //get JSONObject out of JSON file
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        Object json = parser.parse(reader);
        JSONObject stationData = (JSONObject) json;

        //counter is for the position of the station for the line
        int counter = 0;
        int flag = 0;

        //get Array of Stations out of JSONObject
        JSONArray stations = (JSONArray) stationData.get("stations");

        //Iterate station Array
        for(Object station : stations) {
            flag = 0;
            
            JSONObject dataObj = (JSONObject) station;
            JSONObject trackObj = (JSONObject) dataObj.get("track");

            try {
                // get variables
                String stationName = (String) dataObj.get("stationName");
                int stationEva = Integer.valueOf(Long.toString((Long)dataObj.get("evalID")));
                String lat = dataObj.get("lat").toString();
                String lon = dataObj.get("long").toString();
                int trackNr = Integer.valueOf(Long.toString((Long)trackObj.get("trackNumber")));
                int lineNr = Integer.parseInt(line);

                if(stationList.isEmpty()) {
                    stationList.add(new Station(stationName, stationEva, lon, lat, lineNr, trackNr, counter));
                    counter++;
                }else{
                    for(Station s : stationList){

                        if(s.getEvaID() == stationEva && flag == 0){
                            s.addToLineList(lineNr);
                            s.addToTrackList(trackNr);
                            s.addToLineInfoList(lineNr,trackNr,counter);
                            counter++;
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0){
                        stationList.add(new Station(stationName,stationEva,lon,lat,lineNr,trackNr,counter));
                        counter++;
                    }

                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
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

