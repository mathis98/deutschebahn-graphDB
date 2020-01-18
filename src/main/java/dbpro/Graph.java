package dbpro;

import org.neo4j.driver.v1.*;

import java.util.HashMap;
import java.util.Map;

public class Graph implements AutoCloseable {
    private final Driver driver;

    public Graph(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    /*
        Funktion um eine Station hinzuzufügen
        Das Wetter soll aus der OpenWeatherMap API kommen
     */
    public void addStation(
            final String name,
            final long id,
            final double longitude,
            final double latitude,
            boolean goodWeather
    ) {
        String queryText = "CREATE (s:Station) " +
                "SET s.name = $name " +
                "SET s.evaID = $id " +
                "SET s.long = $longitude " +
                "SET s.lat = $latitude " +
                "SET s.goodWeather = $goodWeather " +
                "RETURN s.name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("id", id);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("goodWeather", goodWeather);
        runQuery(queryText, params);
    }

    /*
        Funktion um eine Zugverbindung zwischen zwei Gleisen zweier
        Bahnhöfe hinzuzufügen
     */
    public void addConnection(int startTrack, int startID, int endTrack, int endID, int trainNumber) {
        String queryText = "MATCH (a:Track),(b:Track) " +
                "WHERE a.number = $startTrack AND a.evaID = $startID " +
                "AND b.number = $endTrack AND b.evaID = $endID " +
                "CREATE (a)-[r:Zug]->(b) " +
                "SET r.trainNumber = $trainNumber " +
                "RETURN TYPE(r)";
        Map<String, Object> params = new HashMap<>();
        params.put("startTrack", startTrack);
        params.put("startID", startID);
        params.put("endTrack", endTrack);
        params.put("endID", endID);
        params.put("trainNumber", trainNumber);
        runQuery(queryText, params);
    }

    /*
        Funktion um ein Gleis hinzuzufügen
        die id ist die des Bahnhofes zu welchem das Gleis gehört
     */
    public void addTrack(int trackNumber, boolean roofing, boolean elevator, int id) {
        String queryText = "CREATE (t:Track) " +
                "SET t.number = $number " +
                "Set t.roofing = $roofing " +
                "SET t.elevator = $elevator " +
                "SET t.evaID = $evaID " +
                "RETURN toString(t.number)";
        Map<String, Object> params = new HashMap<>();
        params.put("number", trackNumber);
        params.put("roofing", roofing);
        params.put("elevator", elevator);
        params.put("evaID", id);
        runQuery(queryText, params);
    }

    /*
        Funktion um die Gleise mit ihren jeweiligen Bahnhöfen zu verbinden
        dies geschieht anhand des evaID Werts
     */
    public void connectTracksToStations() {
        String queryText = "MATCH (a:Station),(b:Track) " +
                "WHERE a.evaID = b.evaID " +
                "CREATE (a)-[r:has]->(b) " +
                "RETURN DISTINCT type(r)";
        Map<String, Object> params = new HashMap<>();
        runQuery(queryText, params);
    }

    public void deleteAll() {
        String queryText = "MATCH (n) DETACH DELETE n";
        Map<String, Object> params = new HashMap<>();
        runQuery(queryText, params);
    }

    /*
        Diese Funktion führt eine neo4j query auf der Datenbank aus
     */
    private void runQuery(String query, Map<String, Object> params) {
        try (Session session = driver.session()) {
            String trans = session.writeTransaction(tx -> {
                StatementResult result = tx.run(query, params);
                return "test";//result.single().get(0).asString();
            });
            //  System.out.println(trans);
        }
    }
}