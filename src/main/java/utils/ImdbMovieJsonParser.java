package utils;

import domain.Movie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImdbMovieJsonParser {
    private static String[] parseJsonMovies(String json){
        Pattern pattern = Pattern.compile(".*\\[(.*)].*");
        Matcher matcher = pattern.matcher(json);
        StringBuilder sb  = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group(1));
        }
        String[] resultList = sb.toString().split("},\\{");

        int last = resultList.length - 1;
        resultList[0] = resultList[0].substring(1);
        resultList[last] = resultList[last].substring(0,resultList[last].length()-1);

        return resultList;
    }

    public static List<Movie> parseMovies(String json){
        String [] moviesArray = parseJsonMovies(json);
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
}
