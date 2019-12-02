package dbpro;

import org.neo4j.driver.v1.*;

import java.util.HashMap;
import java.util.Map;

import static org.neo4j.driver.v1.Values.parameters;

public class Graph implements AutoCloseable
{
    private final Driver driver;

    public Graph(String uri, String user, String password)
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() {
        driver.close();
    }

    public void addStation(final String name ) {
        String queryText = "CREATE (rom:Station) " +
                "SET rom.name = $name " +
                "RETURN rom.name + ', from node ' + id(rom)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        runQuery(queryText, params);
    }

    public void addConnection(String from, String to) {
        String queryText = "MATCH (a:Station),(b:Station) " +
                "WHERE a.name = $from AND b.name = $to " +
                "CREATE (a)-[r:ZUG]->(b) " +
                "RETURN TYPE(r)";
        Map<String, Object> params = new HashMap<>();
        params.put("from", from);
        params.put("to", to);
        runQuery(queryText, params);
    }

    public void addConnection(int from, int to) {

    }

    private void runQuery(String query, Map<String, Object> params) {
        try (Session session = driver.session()) {
            String trans = session.writeTransaction(tx -> {
                StatementResult result = tx.run(query, params);
                return result.single().get(0).asString();
            });
            System.out.println(trans);
        }
    }
}