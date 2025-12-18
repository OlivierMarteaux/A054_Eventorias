Feature: Add a new event
  As a user on the Home screen
  I want to add a new event
  So that the new event appears at the top of the event list

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
    When I click on the "Add" FAB button
    Then I should arrive on the Add Screen

  Scenario: Add a new customer successfully
    Given I am on the Add Screen
    When I enter "Christmas Tree" in the "New event" field
    And I enter "Join us at the Eiffel Tower for a festive event, decorating Christmas trees with lights, ornaments, and holiday cheer." in the "Tap here to enter your description" field
    And I select "18/12/2025" in the Date field
    And I select "15:00" in the Time field
    And I enter "Tour Eiffel, Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France" in the "Address" field
    And I pick the first photo from the photo library
    And I click on the "Validate" button
    Then I should arrive on the Home Screen

#  Scenario: Cannot add new customer when name field is empty
#    Given I am on the Add screen
#    When I enter "" in the "name" field
#    And I enter "fievel.farwest@example.com" in the "email" field
#    Then I cannot click on the "Save the new customer" FAB button
#
#  Scenario: Cannot add new customer when email field is empty
#    Given I am on the Add screen
#    When I enter "Fievel Farwest" in the "name" field
#    When I enter "" in the "email" field
#    Then I cannot click on the "Save the new customer" FAB button
#
#  Scenario Outline: Cannot add new customer when email field is invalid
#    Given I am on the Add screen
#    When I enter "Fievel Farwest" in the "name" field
#    When I enter "<email>" in the "email" field
#    Then I cannot click on the "Save the new customer" FAB button
#    Examples:
#      | email     |
#      | x@x.xxxx  |
#      | x.xxx     |
#      | x@x.x     |
#      | x@xxx     |
#      | x         |