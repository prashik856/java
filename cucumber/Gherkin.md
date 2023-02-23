# Gherkin Reference

Doc can be found here: https://cucumber.io/docs/gherkin/reference/

## Specs
- Just like yaml
- comments start from start of the line 
- Has to use identation

Example:
```gherkin
Feature: Guess the word

  # The first example has two steps
  Scenario: Maker starts a game
    When the Maker starts a game
    Then the Maker waits for a Breaker to join

  # The second example has three steps
  Scenario: Breaker joins a game
    Given the Maker has started a game with the word "silky"
    When the Breaker joins the Maker's game
    Then the Breaker must guess a word with 5 characters
```
Please note that some keywords are followed by a colon (:) and some are not

## Keywords
Each line that isn’t a blank line has to start with a Gherkin keyword, followed by any text you like

The primary keywords are:
- Feature
- Rule (as of Gherkin 6)
- Example (or Scenario)
- Given, When, Then, And, But for steps (or *)
- Background
- Scenario Outline (or Scenario Template)
- Examples (or Scenarios)

There are a few secondary keywords as well:
- """ (Doc Strings)
- | (Data Tables)
- @ (Tags)
- # (Comments)


## Feature
The purpose of the Feature keyword is to provide a high-level description of a software feature, and to group related scenarios
The first primary keyword in a Gherkin document must always be Feature, followed by a : and a short text that describes the feature.
You can add free-form text underneath Feature to add more description.

example:
```gherkin
Feature: Guess the word

  The word guess game is a turn-based game for two players.
  The Maker makes a word for the Breaker to guess. The game
  is over when the Breaker guesses the Maker's word.

  Example: Maker starts a game
```
The free format description for Feature ends when you start a line with the keyword Background, Rule, Example or Scenario Outline (or their alias keywords).
You can only have a single Feature in a .feature file.

## Descriptions
Free-form descriptions (as described above for Feature) can also be placed underneath Example/Scenario, Background, Scenario Outline and Rule.

## Rule
The purpose of the Rule keyword is to represent one business rule that should be implemented.
It provides additional information for a feature. 
A Rule is used to group together several scenarios that belong to this business rule.
A Rule should contain one or more scenarios that illustrate the particular rule.

example:
```gherkin
# -- FILE: features/gherkin.rule_example.feature
Feature: Highlander

  Rule: There can be only One

    Example: Only One -- More than one alive
      Given there are 3 ninjas
      And there are more than one ninja alive
      When 2 ninjas meet, they will fight
      Then one ninja dies (but not me)
      And there is one ninja less alive

    Example: Only One -- One alive
      Given there is only 1 ninja alive
      Then he (or she) will live forever ;-)

  Rule: There can be Two (in some cases)

    Example: Two -- Dead and Reborn as Phoenix
      ...
```

## Example
This is a concrete example that illustrates a business rule. 
It consists of a list of steps.
The keyword Scenario is a synonym of the keyword Example.
You can have as many steps as you like, but we recommend 3-5 steps per example. Having too many steps will cause the example to lose its expressive power as a specification and documentation.
In addition to being a specification and documentation, an example is also a test. As a whole, your examples are an executable specification of the system.

Examples follow this same pattern:
- Describe an initial context (Given steps)
- Describe an event (When steps)
- Describe an expected outcome (Then steps)

## Steps
Each step starts with Given, When, Then, And, or But.
Cucumber executes each step in a scenario one at a time, in the sequence you’ve written them in. When Cucumber tries to execute a step, it looks for a matching step definition to execute.
Keywords are not taken into account when looking for a step definition. This means you cannot have a Given, When, Then, And or But step with the same text as another step.

Cucumber considers the following steps duplicates:
```gherkin
Given there is money in my account
Then there is money in my account
```

This might seem like a limitation, but it forces you to come up with a less ambiguous, more clear domain language:
```gherkin
Given my account has a balance of £430
Then my account should have a balance of £430
```

## Given
Given steps are used to describe the initial context of the system - the scene of the scenario. 
It is typically something that happened in the past.
When Cucumber executes a Given step, it will configure the system to be in a well-defined state, such as creating and configuring objects or adding data to a test database.
The purpose of Given steps is to put the system in a known state before the user (or external system) starts interacting with the system (in the When steps).
Avoid talking about user interaction in Given
If you were creating use cases, Given’s would be your preconditions.

Examples:
- Mickey and Minnie have started a game
- I am logged in
- Joe has a balance of £42

## When
When steps are used to describe an event, or an action.
This can be a person interacting with the system, or it can be an event triggered by another system.
It’s strongly recommended you only have a single When step per Scenario.
If you feel compelled to add more, it’s usually a sign that you should split the scenario up into multiple scenarios.

Examples:
- Guess a word
- Invite a friend
- Withdraw money

