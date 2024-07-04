package com.prashik.flexy.rest;

import com.prashik.flexy.utils.Utils;
import io.helidon.config.Config;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import org.apache.logging.log4j.Logger;

/**
 * The Stars class which implements the /stars API.
 *
 * @author prashik
 */
public class Stars implements HttpService {
    private static final Logger logger = Utils.getLogger(Stars.class.getName());
    public static final JsonBuilderFactory JSON = Utils.getJSONBuilder();

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
        String message = "Welcome to the backend server.";
        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", message).build();
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
