Feature: To test user names on stack overflow editors page

  @HappyPath
  Scenario:Display username, location and edits
    Given user setup tests
    And user opens a web browser
    And Navigate to stackoverflow page
    When User clicks on Questions
    When User clicks on Users
    And User clicks on Editors
    Then User click on page 2
    And User should get maximum number of edits per user
    And User close the browser

  @HappyPath
  Scenario:Default page number
    Given user setup tests
    And user opens a web browser
    And Navigate to stackoverflow page
    When User clicks on Questions
    When User clicks on Users
    And User clicks on Editors
    Then User click on page 1
    And User should get maximum number of edits per user
    And User close the browser

  @UnHappyPath
  Scenario:Invalid page number
    Given user setup tests
    And user opens a web browser
    And Navigate to stackoverflow page
    When User clicks on Questions
    When User clicks on Users
    And User clicks on Editors
    Then User click on page 40
    And User should get maximum number of edits per user
    And User close the browser