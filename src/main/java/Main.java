
import utils.ManageStringsHandler;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        List<String> titles = parseAttribute(moviesArray, "title");
        titles.forEach(System.out::println);

        List<String> urlImages = parseAttribute(moviesArray, "image");
        urlImages.forEach(System.out::println);
    }

    private static List<String> parseAttribute(String[] moviesArray, String attribute){
        List<String> attributeList = new ArrayList<>();
        String[] attributes;

        for (String movie :
                moviesArray) {
            attributes = movie.split(",");

            for (String attr :
                    attributes) {
                if (attr.contains(attribute)){
                    attributeList.add(attr);
                    break;
                }
            }
        }

        return attributeList;
    }

    private static List<String> parseTitles(String[] moviesArray) {
        List<String> titles = new ArrayList<>();
        String[] attributes;

        for (String movie :
                moviesArray) {
            attributes = movie.split(",");

            for (String attribute :
                    attributes) {
                if (attribute.contains("title"))
                    titles.add(attribute);
            }
        }

        return titles;
    }

    private static String[] parseJsonMovies(String json){
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(json);
        StringBuilder sb  = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group(1));
        }
        String[] resultList = sb.toString().split("(},)");

        for (int i = 0; i < resultList.length; i++) {
            resultList[i] = resultList[i].substring(1);
        }

        resultList[249] = resultList[249].substring(0,resultList[249].length()-1);

        return resultList;
    }

}
