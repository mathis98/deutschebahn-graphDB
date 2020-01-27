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

    private void addZugWeights() {
        String queryText = "MATCH (start:Track)<-[z:Zug]-(end:Track) " +
                "SET z.weight = 1";
        runQuery(queryText, new HashMap<>());
    }

    private void addChangeWeights() {
        String queryText = "MATCH (start:Track)<-[c:change]-(end:Track) " +
                "SET c.weight = 20";
        runQuery(queryText, new HashMap<>());
    }

    public void addWeights() {
        addZugWeights();
        addChangeWeights();
    }

    public void addChanges() {
        String queryText = "match (a:Track),(b:Track),(c:Station) " +
                "Where (a.evaID = b.evaID = c.evaID) and (id(a) <> id(b)) and (a.elevator = true and b.elevator = true) and ((c.goodWeather = false and a.roofing=true and b.roofing=true) or c.goodWeather = true) " +
                "Create (a)-[d:change]->(b) " +
                "return a,b,c";
        runQuery(queryText, new HashMap<>());
    }

    public String getConnection(int startID, int endID) {
        deleteStations(startID, endID);

        String queryText = "MATCH (start:Station{evaID:$startID}), (end:Station{evaID:$endID}) " +
                "CALL apoc.algo.dijkstra(start, end, 'has|Zug|change', 'weight', 1.0) YIELD path, weight " +
                "RETURN path, weight";
        Map<String, Object> params = new HashMap<>();
        params.put("startID", startID);
        params.put("endID", endID);
        return runQuery(queryText, params);
    }

    private void deleteStations(int startID, int endID) {
        String queryText = "MATCH (a:Station)-[:has]->(b:Track) " +
                "WHERE a.evaID = $startID OR a.evaID = $endID " +
                "CREATE (a)-[r:hasSpecial]->(b) ";
        Map<String, Object> params = new HashMap<>();
        params.put("startID", startID);
        params.put("endID", endID);
        runQuery(queryText, params);
    }

    /*
        Diese Funktion führt eine neo4j query auf der Datenbank aus
     */
    private String runQuery(String query, Map<String, Object> params) {
        Session session = driver.session();
        try {
            session.writeTransaction(tx -> {
                StatementResult result = tx.run(query, params);
                return result.toString();
            });
        } catch(Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return "what happened?";
    }
}