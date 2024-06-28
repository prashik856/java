package com.prashik.helidon;

import io.helidon.metrics.api.*;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;

import java.util.Collections;
import java.util.Random;

public class GreetingCards implements HttpService {
    public static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private final Counter cardCounter;

    private final Timer cardTimer;

    private final DistributionSummary cardSummary;

    GreetingCards() {
        cardCounter = Metrics.globalRegistry()
                .getOrCreate(Counter.builder("cardCount")
                        .description("Counts card retrievals"));

        cardTimer = Metrics.globalRegistry()
                .getOrCreate(Timer.builder("cardTimer")
                        .description("Times card retrievals"));

        cardSummary = Metrics.globalRegistry()
                .getOrCreate(DistributionSummary.builder("cardSummary")
                        .description("Random card destribution"));

        Random r2 = new Random();
        Metrics.globalRegistry()
                .getOrCreate(Gauge.builder("temperature", () -> r2.nextDouble())
                                .description("Ambient Temperature"));
    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        Timer.Sample timerSample = Timer.start();
        cardCounter.increment();
        Random r = new Random();
        for(int i=0; i<1000; i++) {
            cardSummary.record(1 + r.nextDouble());
        }
        sendResponse(response, "Here are some cards ...");
        response.whenSent(() -> timerSample.stop(cardTimer));
    }

    private void sendResponse(ServerResponse response, String msg) {
        JsonObject returnObject = JSON.createObjectBuilder().add("message", msg).build();
        response.send(returnObject);
    }
}
