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
        String queryText = "CREATE (a:Station) " +
                "SET a.name = $name " +
                "RETURN a.name + ', from node ' + id(a)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        runQuery(queryText, params);
    }

    public void addConnection(String from, String to) {

    }

    public void addConnection(int from, int to) {

    }

    private void addConnectionGraph() {

    }

    private void runQuery(String query, Map<String, Object> params) {
        try (Session session = driver.session()) {
            String trans = session.writeTransaction(tx -> {
                StatementResult result = tx.run(query,
                        parameters(params));
                return result.single().get(0).asString();
            });
            System.out.println(trans);
        }
    }
}