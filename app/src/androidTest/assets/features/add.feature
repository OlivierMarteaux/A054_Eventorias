Feature: Add a new customer
  As a user on the Home screen
  I want to add a new customer
  So that the new customer appears at the end of the customer list

  Background: Navigate to Add Screen
    Given I am on the Home screen
    When I click on the "Add a new customer" FAB button
    Then I should arrive on the Add screen

  Scenario: Add a new customer successfully
    Given I am on the Add screen
    When I enter "Fievel Farwest" in the "name" field
    And I enter "fievel.farwest@example.com" in the "email" field
    And I click on the "Save the new customer" FAB button
    Then I should return to the Home screen
    And I should see "Fievel Farwest" added at the end of the "customers" list
    And I should see a toast message "New customer successfully created"

  Scenario: Cannot add new customer when name field is empty
    Given I am on the Add screen
    When I enter "" in the "name" field
    And I enter "fievel.farwest@example.com" in the "email" field
    Then I cannot click on the "Save the new customer" FAB button

  Scenario: Cannot add new customer when email field is empty
    Given I am on the Add screen
    When I enter "Fievel Farwest" in the "name" field
    When I enter "" in the "email" field
    Then I cannot click on the "Save the new customer" FAB button

  Scenario Outline: Cannot add new customer when email field is invalid
    Given I am on the Add screen
    When I enter "Fievel Farwest" in the "name" field
    When I enter "<email>" in the "email" field
    Then I cannot click on the "Save the new customer" FAB button
    Examples:
      | email     |
      | x@x.xxxx  |
      | x.xxx     |
      | x@x.x     |
      | x@xxx     |
      | x         |