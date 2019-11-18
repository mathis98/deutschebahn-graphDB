package dbpro;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import static org.neo4j.driver.v1.Values.parameters;

public class Graph implements AutoCloseable
{
    private final Driver driver;

    public Graph(String uri, String user, String password)
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void addStation( final String name ) {
        try (Session session = driver.session()) {
            String trans = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    StatementResult result = tx.run("CREATE (a:Station) " +
                                    "SET a.name = $name " +
                                    "RETURN a.name + ', from node ' + id(a)",
                            parameters("name", name));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(trans);
        }
    }
}