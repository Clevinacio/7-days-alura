
import domain.Movie;
import utils.HTMLGenerator;
import utils.ManageStringsHandler;
import utils.ParseData;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws Exception {
        String baseURL = "https://imdb-api.com/";
        ManageStringsHandler manager = new ManageStringsHandler(baseURL, System.getenv("API_IMDB")); // Monta String  da URI
        String uriPath = manager.createURIPath("Top250Movies");

        URI imdbAPI = URI.create(uriPath);

        HttpClient hc = HttpClient.newHttpClient();
        HttpRequest hr = HttpRequest.newBuilder().uri(imdbAPI).build();

        HttpResponse<String> response = hc.send(hr, HttpResponse.BodyHandlers.ofString());

        String result = response.body();

        String[] moviesArray = ParseData.parseJsonMovies(result);

        List<Movie> movies = ParseData.parseMovies(moviesArray);
        Writer pw = new PrintWriter("movies.html");
        HTMLGenerator generator  = new HTMLGenerator(pw);
        generator.generate(movies);
        pw.close();
        System.out.println("Done");
    }

}
