package dbpro;

import java.util.ArrayList;

public class Station {

    private String name;
    private int evaID;
    private String longitude;
    private String latitude;
    private ArrayList<Integer> lineList = new ArrayList<Integer>();
    private ArrayList<Integer> trackList = new ArrayList<Integer>();
    private ArrayList<lineInfo> lineInfoList = new ArrayList<lineInfo>();

    public Station(String name, int evaID, String longitude, String latitude, int line, int track, int order) {

        this.name = name;
        this.evaID = evaID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lineList.add(line);
        this.trackList.add(track);
        this.lineInfoList.add(new lineInfo(line, track, order));

    }

    public void addToLineList(int line) {
        this.lineList.add(line);
    }

    public void addToTrackList(int track) {
        this.trackList.add(track);
    }

    public void addToLineInfoList(int line, int track, int order) {
        this.lineInfoList.add(new lineInfo(line, track, order));
    }

    public int getEvaID(){
        return this.evaID;
    }

    public String toString(){

        return "Line: " + this.lineInfoList.get(0).getLine() + " Track: " + this.lineInfoList.get(0).getTrack() + " Order: " + this.lineInfoList.get(0).getOrder();

    }

    public void writeLineInfo(){
        this.lineInfoList.stream().forEach(a -> System.out.println("Station: " + this.name + " (" + this.evaID + ") " + a.toString()));
    }

    public ArrayList<lineInfo> getLineInfoList() {
        return lineInfoList;
    }
}


