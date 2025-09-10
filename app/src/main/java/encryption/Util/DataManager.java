package encryption.Util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DataManager {
    private static final Logger logger = LogManager.getLogger(DataManager.class.getName());

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T getDataFromApi(String apiUrl, String memberName, Class<T> typeofDst) throws Exception {
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

        JsonObject objectToParse;
        try {
            JsonArray jsonResponse = gson.fromJson(response.body(), JsonArray.class);
            logger.debug("Json response parsed:\n" + jsonResponse.toString());
            
            objectToParse = jsonResponse.get(0).getAsJsonObject();
            logger.debug("objectToParse successfully obtained:\n" + objectToParse.toString());
            
            logger.info("Response successfully parsed into json");
        } catch (Exception e) {
            logger.error("Parsing response to json failed:\n", e);
            throw new Exception("Parsing response to json failed: " + e.getMessage());
        }

        T data = null;
        try {
            data = gson.fromJson(objectToParse.get(memberName).toString(), typeofDst);

            logger.info("Data successfully parsed into output object");
        } catch (Exception e) {
            logger.error("Parsing json to output object failed:\n", e);
            throw new Exception("Parsing json to output object failed: " + e.getMessage());
        }

        logger.info("Data fetched successfully");

        return data;
    }
}
