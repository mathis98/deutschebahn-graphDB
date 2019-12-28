package dbpro;
import dbpro.Station;

public class Stop {
    private Station station;
    private int track;
    private String arrivalTime;
    private String departureTime;

    public Stop(Station station, int track, String arrivalTime, String departureTime) {
        this.station = station;
        this.track = track;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public Station getStation() {return this.station;}
    public int getTrack() {return this.track;}
    public String getArrivalTime() {return this.arrivalTime;}
    public String getDepartureTime() {return this.departureTime;}
}
