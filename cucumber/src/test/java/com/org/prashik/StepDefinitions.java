package com.org.prashik;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

class IsItFriday {
    static String isItFriday(String today) {
        return today.equals("Friday") ? "TGIF" : "Nope";
    }
}

public class StepDefinitions {
    private String today;
    private String actualAnswer;
    @Given("today is Sunday")
    public void todayIsSunday() {
        // Write code here that turns the phrase above into concrete actions
        today = "Sunday";
    }
    @When("I ask whether it's friday yet")
    public void iAskWhetherItsFridayYet() {
        // Write code here that turns the phrase above into concrete actions
        actualAnswer = IsItFriday.isItFriday(today);
    }
    @Then("I should be told {string}")
    public void iShouldBeTold(String expectedAnswer) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Given("today is Friday")
    public void todayIsFriday() {
        today = "Friday";
    }
}
