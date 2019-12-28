package dbpro;
import dbpro.Stop;

import java.util.ArrayList;

public class Journey {
    ArrayList<Stop> stops;

    public Journey(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public ArrayList<Stop> getStops() {return this.stops;}
}
