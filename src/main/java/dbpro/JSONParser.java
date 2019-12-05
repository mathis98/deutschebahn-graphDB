package dbpro;

import dbpro.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;


public class JSONParser {

    public ArrayList<Station> parse (String jsonString) throws Exception{

        ArrayList<Station> stationList = new ArrayList<Station>();

        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);
        JSONArray stationData = (JSONArray) json.get("result");

        try{
            //get Stationnames
            for(int i=0; i<stationData.size(); i++) {
                JSONObject dataObj = (JSONObject) stationData.get(i);
                String stationName = (String) dataObj.get("name");


                //get Evaids
                JSONArray stationEva = (JSONArray) dataObj.get("evaNumbers");
                JSONObject dataObj1 = (JSONObject) stationEva.get(0);
                long stationEvaNumber = (Long) dataObj1.get("number");


                //get Coords
                JSONObject dataObj2 = (JSONObject) dataObj1.get("geographicCoordinates");
                JSONArray coordinates = (JSONArray) dataObj2.get("coordinates");
                String longtitude = (String) coordinates.get(0).toString();
                String latitude = (String) coordinates.get(1).toString();

                stationList.add(new Station(stationName,stationEvaNumber,longtitude,latitude));
            }}
            catch(Exception e){

            System.out.println("A station cant be parsed.");

            }









        return stationList;
    }
}
