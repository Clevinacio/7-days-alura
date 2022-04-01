package utils;

public class ManageStringsHandler {
    private final String baseURL;
    private final String API_KEY;

    public ManageStringsHandler(String baseURL, String API_KEY) {
        this.baseURL = baseURL;
        this.API_KEY = API_KEY;
    }

    public String createURIPath(String path) {
        return baseURL +
                "API/" +
                path +
                "/" +
                API_KEY;
    }
}
