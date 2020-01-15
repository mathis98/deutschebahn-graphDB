package dbpro;

import java.util.ArrayList;

public class Station {

    private String name;
    private long evaID;
    private String longtitude;
    private String latitude;
    private ArrayList<Integer> lineList;
    private ArrayList<Long> trackList;
    private ArrayList<lineInfo> lineInfoList;

    public Station(String name, long evaID, String longtitude, String latitude, int line, long track, int order) {

        this.name = name;
        this.evaID = evaID;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.lineList = setLineList(line);
        this.trackList = setTrackList(track);
        this.lineInfoList = setLineInfoList(line, track, order);

    }

    private ArrayList<lineInfo> setLineInfoList(int line, long track, int order) {
        ArrayList<lineInfo> list = new ArrayList<lineInfo>();
        list.add(new lineInfo(line, track, order));
        return list;
    }

    private ArrayList<Long> setTrackList(long track) {
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(track);
        return list;
    }

    private ArrayList<Integer> setLineList(int line) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(line);
        return list;
    }

    public String toString() {
        return name + " (" + evaID + "), lat: " + latitude + ", long: " + longtitude;
    }

    public String getName() {
        return this.name;
    }

    public long getEvaID() {
        return this.evaID;
    }

    public String getLongtitude() {
        return this.longtitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public ArrayList<lineInfo> getLineInfoList() {
        return this.lineInfoList;
    }

}


