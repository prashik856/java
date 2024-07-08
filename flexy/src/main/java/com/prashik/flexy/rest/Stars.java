package com.prashik.flexy.rest;

import com.prashik.flexy.model.Movie;
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
import java.util.Set;

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
        rules.get("/", this::getDefaultMessageHandler)
                .get("/{name}", this::getStarMovies)
                .get("/{name}/random", this::getStarRandomMovies)
                .get("/random", this::getRandomStarsAndMovies);
    }

    /**
     * Function to return all movies of the star when input provided is star name.
     *
     * @param serverRequest The input server request.
     * @param serverResponse the input server response.
     */
    public void getStarMovies(ServerRequest serverRequest,
                                      ServerResponse serverResponse) {
        String name = serverRequest.path().pathParameters().get("name");
        logger.info("/api/v1/stars/{} API accessed.", name);
        Star star = Utils.getStarFromName(name, allStars);
        if(star == null) {
            logger.info("Null response received when searching from name. Checking if id is provided.");
            star = Utils.getStarFromId(name, allStars);
        }

        checkNull(serverResponse, star);

        JsonObjectBuilder jsonObjectBuilder = createOkJsonResponse();

        JsonArrayBuilder jsonArrayBuilder = JSON.createArrayBuilder();

        for(Movie movie: star.getMovies()) {
            JsonObject json = JSON.createObjectBuilder()
                    .add("name", movie.getName())
                    .add("id", movie.getId())
                    .add("location", movie.getLocation())
                    .build();
            jsonArrayBuilder.add(json);
        }

        JsonArray jsonArray = jsonArrayBuilder.build();
        jsonObjectBuilder.add("data", jsonArray);

        JsonObject returnObject = jsonObjectBuilder.build();
        serverResponse.status(Status.OK_200);
        sendOKResponse(serverResponse, returnObject);
    }

    /**
     * Function to return 5 random movies of star when name is provided.
     *
     * @param serverRequest The input server request.
     * @param serverResponse the input server response.
     */
    public void getStarRandomMovies(ServerRequest serverRequest,
                                            ServerResponse serverResponse) {
        String name = serverRequest.path().pathParameters().get("name");
        logger.info("/api/v1/stars/{}/random API accessed.", name);
        Star star = Utils.getStarFromName(name, allStars);
        if(star == null) {
            logger.info("Null response received when searching from name. Checking if id is provided.");
            star = Utils.getStarFromId(name, allStars);
        }

        checkNull(serverResponse, star);

        JsonObjectBuilder jsonObjectBuilder = createOkJsonResponse();

        JsonArrayBuilder jsonArrayBuilder = JSON.createArrayBuilder();
        if(star.getMovies().size() < 5) {
            for(Movie movie: star.getMovies()) {
                JsonObject json = JSON.createObjectBuilder()
                        .add("name", movie.getName())
                        .add("id", movie.getId())
                        .add("location", movie.getLocation())
                        .build();
                jsonArrayBuilder.add(json);
            }
        } else {
            Set<Integer> randomIndices = Utils.getRandomIndices(star.getMovies().size(), 5);
            for(Integer index: randomIndices) {
                Movie movie = star.getMovies().get(index);
                JsonObject json = JSON.createObjectBuilder()
                        .add("name", movie.getName())
                        .add("id", movie.getId())
                        .add("location", movie.getLocation())
                        .build();
                jsonArrayBuilder.add(json);
            }
        }

        JsonArray jsonArray = jsonArrayBuilder.build();
        jsonObjectBuilder.add("data", jsonArray);

        JsonObject returnObject = jsonObjectBuilder.build();
        sendOKResponse(serverResponse, returnObject);
    }

    /**
     * Function to return 5 random stars with 5 random movies.
     *
     * @param serverRequest The input server request.
     * @param serverResponse the input server response.
     */
    public void getRandomStarsAndMovies(ServerRequest serverRequest,
                                        ServerResponse serverResponse) {

    }

    /**
     * the method which returns the default response on /stars API
     *
     * @param serverRequest The input server request.
     * @param serverResponse the input server response.
     */
    private void getDefaultMessageHandler(ServerRequest serverRequest,
                                          ServerResponse serverResponse) {

        logger.info("/api/v1/stars API accessed.");

        JsonObjectBuilder jsonObjectBuilder = createOkJsonResponse();

        JsonArrayBuilder jsonArrayBuilder = JSON.createArrayBuilder();
        for(Star star: this.allStars) {
            JsonObject json = JSON.createObjectBuilder()
                    .add("name", star.getName())
                    .add("id", star.getId())
                    .build();
            jsonArrayBuilder.add(json);
        }
        JsonArray jsonArray = jsonArrayBuilder.build();
        jsonObjectBuilder.add("data", jsonArray);

        JsonObject returnObject = jsonObjectBuilder.build();
        sendOKResponse(serverResponse, returnObject);
    }

    /**
     * the method to send back response to the client.
     *
     * @param serverResponse the server response object
     * @param returnObject the build JSON object to be returned.
     */
    private void sendOKResponse(ServerResponse serverResponse,
                              JsonObject returnObject) {
        serverResponse.status(Status.OK_200);
        serverResponse.send(returnObject);
    }

    /**
     * Function to create Ok Json builder
     *
     * @return returns a JsonObjectBuild with OK reason and status.
     */
    public JsonObjectBuilder createOkJsonResponse() {
        String reason = "OK";
        String status = Integer.toString(Status.OK_200.code());

        JsonObjectBuilder jsonObjectBuilder = JSON.createObjectBuilder();
        jsonObjectBuilder.add("reason", reason);
        jsonObjectBuilder.add("status", status);
        return jsonObjectBuilder;
    }

    /**
     * Function to send Not found response.
     *
     * @param serverResponse The Server Response object.
     */
    private void sendNotFoundResponse(ServerResponse serverResponse) {
        String reason = "Not Found";
        String status = Integer.toString(Status.NOT_FOUND_404.code());
        JsonObjectBuilder jsonObjectBuilder = JSON.createObjectBuilder();

        jsonObjectBuilder.add("reason", reason);
        jsonObjectBuilder.add("status", status);
        JsonObject jsonObject = jsonObjectBuilder.build();

        serverResponse.status(Status.NOT_FOUND_404);
        sendOKResponse(serverResponse, jsonObject);
    }


    /**
     * Function to check null of the star response.
     *
     * @param serverResponse the serverResponse object
     * @param star the Star object
     */
    private void checkNull(ServerResponse serverResponse, Star star) {
        if(star == null) {
            sendNotFoundResponse(serverResponse);
        }

        if(star.getMovies() == null) {
            sendNotFoundResponse(serverResponse);
        }
    }
}
