Feature: Display the event details
  As a user on the Home screen,
  I want to access to the event details
  So that I can see its detailed data

  Background: Navigate to Home Screen
    Given I am on the Splash screen
    When I click on the "Sign in with email" button
    Then I should arrive on the Login Screen
    When I enter "fievel.farwest@example.com" in the "Email" field
    And I click on the "Next" button
    Then I should arrive on the Password screen
    When I enter "test123&" in the "Password" field
    And I click on the "Sign in" button
    Then I should arrive on the Home Screen

  Scenario Outline: Navigate to Detail Screen
    When I click on the "<name>" card
    Then I should arrive on the "Detail" screen for the "event" item named "<name>"

    Examples:
      | name                  |
      | City Carnival         |
      | Winter Light Festival |
      | City Film Festival    |
      | Science Festival      |
      | Book Fair             |