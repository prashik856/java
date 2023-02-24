Feature: Is it Friday yet?
  Everybody wants to know when it's friday

  Scenario: Sunday isn't Friday
    Given today is Sunday
    When I ask whether it's friday yet
    Then I should be told "Nope"

  Scenario: Friday is Friday
    Given today is Friday
    When I ask whether it's friday yet
    Then I should be told "TGIF"