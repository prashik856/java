package com.prashik.flexy.rest;

import com.prashik.flexy.model.Star;
import com.prashik.flexy.utils.Utils;
import io.helidon.config.Config;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.*;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The Stars class which implements the /stars API.
 *
 * @author prashik
 */
public class Stars implements HttpService {
    private static final Logger logger = Utils.getLogger(Stars.class.getName());
    public static final JsonBuilderFactory JSON = Utils.getJSONBuilder();
    Config config;
    private ArrayList<Star> allStars;

    /**
     * Class constructor.
     */
    public Stars() {

    }

    /**
     * Class constructor with config input.
     *
     * @param config The application config
     */
    public  Stars(Config config) {
        this.config = config;
        try {
            Path starsDirectoryPath = Utils.checkDirectory(
                    config.get("local.directory.stars").asString().get()
                    , "Stars");

            // array list to store all Stars
            this.allStars = Utils.getAllStars(starsDirectoryPath);
            // logger.info("All Stars: {}", stars.toString());
        } catch (Exception e) {
            logger.error("Error constructing Stars object.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * The overridden routing method which maps /stars API endpoints
     * to corresponding methods.
     *
     * @param rules The HTTP rules used by the endpoints.
     */
    @Override
    public void routing(HttpRules rules) {
        logger.info("Setting up routes for stars API.");
        rules.get("/", this::getDefaultMessageHandler);
    }

    /**
     * the method which returns the default response on /stars API
     *
     * @param serverRequest The input server request.
     * @param serverResponse the input server response.
     */
    private void getDefaultMessageHandler(ServerRequest serverRequest,
                                          ServerResponse serverResponse) {

        logger.info("/stars API accessed.");
        // return a list of objects.
        String reason = "OK";
        String status = Status.OK_200.toString();

        JsonObjectBuilder jsonObjectBuilder = JSON.createObjectBuilder();
        jsonObjectBuilder.add("reason", reason);
        jsonObjectBuilder.add("status", status);

        JsonArrayBuilder jsonArrayBuilder = JSON.createArrayBuilder();
        for(Star star: this.allStars) {
            JsonObject json = JSON.createObjectBuilder()
                    .add("name", star.getName())
                    .build();
            jsonArrayBuilder.add(json);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        jsonObjectBuilder.add("data", jsonArray);

        JsonObject returnObject = jsonObjectBuilder.build();
        serverResponse.status(Status.OK_200);
        sendResponse(serverResponse, returnObject);
    }

    /**
     * the method to send back response to the client.
     *
     * @param serverResponse the server response object
     * @param returnObject the build JSON object to be returned.
     */
    private void sendResponse(ServerResponse serverResponse,
                              JsonObject returnObject) {
        serverResponse.send(returnObject);
    }
}
