Feature: Display the customer details
  As a user on the Home screen,
  I want to access to the customer details
  So that I can see its detailed data

  Background: Navigate to Add Screen
    Given I am on the Home screen

  Scenario Outline: Navigate to Detail Screen
    When I click on the "<name>" card
    Then I should arrive on the "Detail" screen for the "customer" item named "<name>"

    Examples:
      | name               |
      | Alice Wonderland   |
      | Bob Builder        |
      | Charlie Chocolate  |
      | Diana Dream        |
      | Evan Escape        |