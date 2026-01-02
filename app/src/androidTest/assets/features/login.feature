Feature: Sign in the app with email
  As a user on the Splash Screen
  I want to sign in the application using my email and password

  Scenario: Sign in the app using email and password
    Given I am on the Splash screen
    When I click on the "Sign in with email" button
    Then I should arrive on the Login Screen
    When I enter "fievel.farwest@example.com" in the "Email" field
    And I click on the "Next" button
    Then I should arrive on the Password screen
    When I enter "test123&" in the "Password" field
    And I click on the "Sign in" button
    Then I should arrive on the Home Screen