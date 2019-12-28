package dbpro;
import dbpro.Station;

import java.util.Comparator;

public class Stop implements Comparable{
    private Station station;
    private int track;
    private String arrivalTime;
    private String departureTime;
    private String trainName;
    private String detailsId;

    public Stop(Station station, int track, String arrivalTime, String departureTime, String trainName, String detailsId) {
        this.station = station;
        this.track = track;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.trainName = trainName;
        this.detailsId = detailsId;
    }

    public String toString () {
        return station.toString() + "\n" + trainName + ": track: " + track + " arrive: " + arrivalTime + " depart: "
                + departureTime + " detailsID: " + detailsId + "\n";
    }

    private boolean biggerString(String a, String b) {
        int hoursA = Integer.parseInt(a.substring(11, 13));
        int minutesA = Integer.parseInt(a.substring(14));
        int hoursB = Integer.parseInt(b.substring(11, 13));
        int minutesB = Integer.parseInt(b.substring(14));

        if(hoursA > hoursB || (hoursA == hoursB && minutesA > minutesB)) {
            return true;
        }
        return false;
    }

    public int compare(Stop a, Stop b) {
        if(a.getDepartureTime() == b.getDepartureTime()) return 0;
        else if(biggerString(a.getDepartureTime(), b.getDepartureTime())) return 1;
        return -1;
    }

    public Station getStation() {return this.station;}
    public int getTrack() {return this.track;}
    public String getArrivalTime() {return this.arrivalTime;}
    public String getDepartureTime() {return this.departureTime;}
    public String getTrainName() {return this.trainName;}
    public String getDetailsId() {return this.detailsId;}

    @Override
    public int compareTo(Object o) {
        if(o instanceof Stop)
            return compare(this, (Stop) o);
        return -1;
    }
}
