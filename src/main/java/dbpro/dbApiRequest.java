package dbpro;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;


public class dbApiRequest {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2) // use latest HTTP/2
            .build();

    /**
     *
     * <p>
     *     This method is used to get response data of db api
     *     The technical implementation is abstracted away in sendGetIds()
     * </p>
     * @return Ids of stations (@see sendGetIds())
     * @throws IOException if Connection fails
     * @throws InterruptedException if Connection is interrupted
     */
    public static String getIds() throws IOException, InterruptedException {

        // build HttpClient
        dbApiRequest request = new dbApiRequest();

        // get Ids from /station
        String ids = request.sendGetIds();

        // return them
        return ids;
    }

    /**
     *
     * <p>
     *     This Method is used to obtain data from stations db api
     *     This is the technical implementation
     * </p>
     * @return body of the response of station data request
     * @throws IOException if Connection fails
     * @throws InterruptedException if Connection is interrupted
     */
    private String sendGetIds() throws IOException, InterruptedException {

        // IMPORTANT: use your API token here
        String token = "da59491d3fed20049926c04cdaa29dae";

        // new HTTP get request to /stations
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.deutschebahn.com/stada/v2/stations"))
                .setHeader("Authorization", "Bearer " + token)
                .build();

        // response of above request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // pretty print response header
//        printResponse(response);

        // return response body to getIds()
        return response.body();
    }

    public String getDeparture(String id, String date) throws IOException, InterruptedException {
        dbApiRequest request = new dbApiRequest();

        String departure = request.sendGetDeparture(id, date);

        return departure;
    }

    private String sendGetDeparture(String id, String date) throws IOException, InterruptedException {
        String token = "da59491d3fed20049926c04cdaa29dae";

        URIBuilder uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.deutschebahn.com/fahrplan-plus/v1/arrivalBoard/"+id)
                .addParameter("date", "2019-12-27");

        System.out.println(uri.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri.toString()))
                .setHeader("Authorization", "Bearer " + token)
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        printResponse(response);

        return response.body();
    }

    /**
     * Method for pretty printing response headers
     * @param response The response to print
     */
    private void printResponse(HttpResponse<String> response) {

        // pretty print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        System.out.println(response.statusCode());
    }

}