package dbpro;

public class Station {

    private String name;
    private long evaID;
    private String longtitude;
    private String latitude;


    public Station(String name, long evaID, String longtitude, String latitude){

    this.name = name;
    this.evaID = evaID;
    this.longtitude = longtitude;
    this.latitude = latitude;

    }

    public String getName(){
        return this.name;
    }

    public long getEvaID(){
        return this.evaID;
    }
    public String getLongtitude(){
        return this.longtitude;
    }
    public String getLatitude(){
        return this.latitude;
    }


}


