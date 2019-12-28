package dbpro;
import dbpro.Station;

public class Stop {
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

    public Station getStation() {return this.station;}
    public int getTrack() {return this.track;}
    public String getArrivalTime() {return this.arrivalTime;}
    public String getDepartureTime() {return this.departureTime;}
    public String getTrainName() {return this.trainName;}
    public String getDetailsId() {return this.detailsId;}
}
