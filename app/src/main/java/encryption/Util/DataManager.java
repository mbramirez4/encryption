package encryption.Util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DataManager {
    private static final Logger logger = LogManager.getLogger(DataManager.class.getName());

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> List<T> getDataFromApi(String apiUrl, String memberName, Class<T> typeofDst) throws Exception {
        logger.info("Fetching data started");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .header("Accept", "application/json")
                .build();
        logger.info("Http request created");

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Http response received");
        } catch (Exception e) {
            logger.error("Http request failed:\n", e);
            throw new Exception("Http request failed: " + e.getMessage());
        }

        JsonArray jsonResponse;
        try {
            jsonResponse = gson.fromJson(response.body(), JsonArray.class);
            logger.debug("Json response parsed:\n" + jsonResponse.toString());
            
            logger.info("Response successfully parsed into json");
        } catch (Exception e) {
            logger.error("Parsing response to json failed:\n", e);
            throw new Exception("Parsing response to json failed: " + e.getMessage());
        }

        JsonObject objectToParse;
        List<T> data = new ArrayList<>();
        try {
            for (JsonElement element : jsonResponse) {
                objectToParse = element.getAsJsonObject();
                data.add(gson.fromJson(objectToParse.get(memberName), typeofDst));
            }
        } catch (Exception e) {
            logger.error("Parsing json to output object failed:\n", e);
            throw new Exception("Parsing json to output object failed: " + e.getMessage());
        }

        logger.info("Data fetched successfully");

        return data;
    }
}
