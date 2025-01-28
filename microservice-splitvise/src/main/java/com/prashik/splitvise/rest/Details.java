package com.prashik.splitvise.rest;

import com.prashik.splitvise.utils.Utils;
import io.helidon.config.Config;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.*;
import org.apache.logging.log4j.Logger;

/**
 * The Details class which implements the /details API.
 *
 * @author prashik
 */
public class Details implements HttpService {
    private static final Logger logger = Utils.getLogger(Details.class.getName());
    public static final JsonBuilderFactory JSON = Utils.getJSONBuilder();
    Config config;

    /**
     * Class constructor.
     */
    public Details() {

    }

    /**
     * Class constructor with config input.
     *
     * @param config The application config
     */
    public  Details(Config config) {
        this.config = config;
    }

    /**
     * The overridden routing method which maps /details API endpoints
     * to corresponding methods.
     *
     * @param rules The HTTP rules used by the endpoints.
     */
    @Override
    public void routing(HttpRules rules) {
        logger.info("Setting up routes for details API.");
        rules.get("/", this::getDetails);
    }

    /**
     * Function to return details of splitvise service
     *
     * @param serverRequest The input server request.
     * @param serverResponse the input server response.
     */
    public void getDetails(ServerRequest serverRequest, ServerResponse serverResponse) {
        logger.info("/api/v1/details/ API accessed");

        JsonObjectBuilder jsonObjectBuilder = Utils.createOkJsonResponse(JSON);
        JsonArrayBuilder jsonArrayBuilder = JSON.createArrayBuilder();

        // details REST API object
        JsonObject detailsRESTAPI = JSON.createObjectBuilder()
                .add("Details REST API", "/api/v1/details/")
                .add("Allowed methods", "GET")
                .build();

        jsonArrayBuilder.add(detailsRESTAPI);
        JsonArray restArray = jsonArrayBuilder.build();
        JsonObject dataObject = JSON.createObjectBuilder()
                .add("version", "1.0.0")
                .add("description", "microservice split-vise application is developed "
                        + "as a replica of the split-vise app backend by me.")
                .add("restEndpoints", restArray)
                .build();

        jsonObjectBuilder.add("data", dataObject);

        JsonObject returnObject = jsonObjectBuilder.build();
        serverResponse.status(Status.OK_200);
        Utils.sendOKResponse(serverResponse, returnObject);
    }
}
