package com.prashik.splitvise.rest;

import com.prashik.splitvise.model.AllCustomers;
import com.prashik.splitvise.utils.Utils;
import io.helidon.config.Config;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObjectBuilder;
import org.apache.logging.log4j.Logger;

public class Persons implements HttpService {
    private static final Logger logger = Utils.getLogger(Persons.class.getName());
    public static final JsonBuilderFactory JSON = Utils.getJSONBuilder();
    Config config;
    AllCustomers allCustomers;

    public Persons() {

    }

    public Persons(Config config, AllCustomers allCustomers) {
        this.config = config;
        this.allCustomers = allCustomers;
    }

    @Override
    public void routing(HttpRules rules) {
        logger.info("Setting up routes for persons API");
        rules.get("/", this::getPersons)
                .put("/add", this::addPerson);
    }

    public void getPersons(ServerRequest serverRequest, ServerResponse serverResponse) {
        logger.info("/api/v1/persons/ API accessed");

        JsonObjectBuilder jsonObjectBuilder = Utils.createOkJsonResponse(JSON);

    }

    public void addPerson(ServerRequest serverRequest, ServerResponse serverResponse) {

    }
}
