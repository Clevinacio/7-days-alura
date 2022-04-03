
import domain.Movie;
import utils.ManageStringsHandler;
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

        String[] moviesArray = parseJsonMovies(result);

        List<Movie> movies = parseMovies(moviesArray);
        movies.forEach((movie) -> {
            System.out.println(movie.toString());
        });
    }

    private static List<Movie> parseMovies(String[] moviesArray){
        List<Movie> listMovies = new ArrayList<>();
        Iterator<String> titles = parseTitles(moviesArray).iterator();
        Iterator<String> urlImages = parseUrlImages(moviesArray).iterator();
        Iterator<String> ratings = parseRating(moviesArray).iterator();
        Iterator<String> years = parseYear(moviesArray).iterator();

        while (titles.hasNext()){
            listMovies.add(new Movie(titles.next(),
                    urlImages.next(),
                    Double.parseDouble(ratings.next()),
                    Integer.parseInt(years.next())));
        }

        return listMovies;
    }

    private static List<String> parseTitles(String[] moviesArray) {
        return parseAttribute(moviesArray, 3);
    }

    private static List<String> parseUrlImages(String[] moviesArray) {
        return parseAttribute(moviesArray, 5);
    }

    private static List<String> parseRating(String[] moviesArray) {
        return parseAttribute(moviesArray, 7);
    }

    private static List<String> parseYear(String[] moviesArray) {
        return parseAttribute(moviesArray, 4);
    }

    private static List<String> parseAttribute(String[] moviesArray, int pos) {
        return Stream.of(moviesArray)
                .map(e -> e.split("\",\"")[pos])
                .map(e -> e.split(":\"")[1])
                .map(e -> e.replaceAll("\"", ""))
                .collect(Collectors.toList());
    }

    private static String[] parseJsonMovies(String json){
        Pattern pattern = Pattern.compile(".*\\[(.*)\\].*");
        Matcher matcher = pattern.matcher(json);
        StringBuilder sb  = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group(1));
        }
        String[] resultList = sb.toString().split("\\},\\{");

        int last = resultList.length - 1;
        resultList[0] = resultList[0].substring(1);
        resultList[last] = resultList[last].substring(0,resultList[last].length()-1);

        return resultList;  
    }

}
