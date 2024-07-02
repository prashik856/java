package com.prashik.flexy;

import io.helidon.config.Config;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;

import java.util.Collections;

public class Stars implements HttpService {
    public static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    public Stars() {

    }

    public  Stars(Config config) {

    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest serverRequest,
                                          ServerResponse serverResponse) {
        String message = "Welcome to the backend server.";
        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", message).build();
        sendResponse(serverResponse, returnObject);
    }

    private void sendResponse(ServerResponse serverResponse,
                              JsonObject returnObject) {
        serverResponse.send(returnObject);
    }
}
