package dbpro;

import java.util.ArrayList;

public class Station {

    private String name;
    private int evaID;
    private Double longitude;
    private Double latitude;
    private boolean weather;
    private ArrayList<Integer> lineList = new ArrayList<Integer>();
    private ArrayList<Integer> trackList = new ArrayList<Integer>();
    private ArrayList<lineInfo> lineInfoList = new ArrayList<lineInfo>();

    public boolean getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        if(weather >= 800) {
            this.weather = true;
        } else {
            this.weather = false;
        }
    }

    public Station(String name, int evaID, String longitude, String latitude, int line, int track, int order) {

        this.name = name;
        this.evaID = evaID;
        this.longitude = Double.valueOf(longitude);
        this.latitude = Double.valueOf(latitude);

        if(!this.lineList.contains(line)) {
            this.lineList.add(line);
        }
        if(!this.trackList.contains(track)) {
            this.trackList.add(track);
        }
        this.lineInfoList.add(new lineInfo(line, track, order));

    }

    public void addToLineList(int line) {
        if(!this.lineList.contains(line)) {
            this.lineList.add(line);
        }
    }

    public void addToTrackList(int track) {
        if(!this.trackList.contains(track)) {
            this.trackList.add(track);
        }
    }

    public void addToLineInfoList(int line, int track, int order) {
        this.lineInfoList.add(new lineInfo(line, track, order));
    }

    public int getEvaID(){
        return this.evaID;
    }

    public String toString(){

        return "Line: " + this.lineInfoList.get(0).getLine() + " Order: " + this.lineInfoList.get(0).getOrder() + " Track: " + this.lineInfoList.get(0).getTrack();

    }

    public void writeLineInfo(){
        this.lineInfoList.stream().forEach(a -> System.out.println("Station: " + this.name + " (" + this.evaID + ") " + a.toString()));
    }

    public ArrayList<lineInfo> getLineInfoList() {
        return this.lineInfoList;
    }

    public ArrayList<Integer> getTrackList(){
        return this.trackList;
    }

    public String getName(){
        return this.name;
    }

    public Double getLatitude(){
        return this.latitude;
    }

    public Double getLongitude(){
        return this.longitude;
    }

    public ArrayList<Integer> getLineList(){
        return this.lineList;
    }
}


