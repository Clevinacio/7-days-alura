
import domain.Movie;
import utils.HTMLGenerator;
import utils.ImdbApiClient;
import utils.ImdbMovieJsonParser;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        String result = new ImdbApiClient(System.getenv("API_IMDB")).getJson("Top250Movies");

        List<Movie> movies = ImdbMovieJsonParser.parseMovies(result);

        Writer pw = new PrintWriter("content.html");

        new HTMLGenerator(pw).generate(movies);

        pw.close();
        System.out.println("Done");
    }

}
