Feature: Display and scroll the events list
  As a user on the Home screen
  I want to see a scrolling list of all the events

#  Background: Sign in the app using email and password
#    Given I am on the Splash screen
#    When I click on the "Sign in with email" button
#    Then I should arrive on the Login Screen
#    When I enter "fievel.farwest@example.com" in the "Email" field
#    And I click on the "Next" button
#    Then I should arrive on the Password screen
#    When I enter "test123&" in the "Password" field
#    And I click on the "Sign in" button
#    Then I should arrive on the Home Screen

#  Background: Navigate to home screen
#    Given I am on the Splash screen
#    When I click on the button tagged "home_screen"
#    Then I should arrive on the Home Screen

  Scenario: Display and scroll the events list
    Given I am on the Home screen
    Then All the events are displayed and scrollable on the screen