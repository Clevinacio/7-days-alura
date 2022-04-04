package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImdbApiClient {
    private final String API_KEY;

    public ImdbApiClient(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public String getJson(String path) throws IOException, InterruptedException {
        String uriPath = createURIPath(path);

        URI imdbAPI = URI.create(uriPath);

        HttpClient hc = HttpClient.newHttpClient();
        HttpRequest hr = HttpRequest.newBuilder().uri(imdbAPI).build();

        HttpResponse<String> response = hc.send(hr, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private String createURIPath(String path) {
        String BASE_URL = "https://imdb-api.com/";
        return BASE_URL +
                "API/" +
                path +
                "/" +
                API_KEY;
    }
}
