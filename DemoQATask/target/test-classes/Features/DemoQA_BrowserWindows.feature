Feature: DemoQA Alerts, Frames and Browser Windows

Background:
    Given user navigates to DemoQA Browser Windows page

  Scenario: Handle all alert types on the Alerts page
    Given User clicks on "Alerts, Frame & Windows" in the left panel
    And   User clicks on "Alerts" menu item

    When  User clicks the "Click me" button to trigger an alert
    Then  The alert should be accepted successfully

    When  User clicks the button to trigger a confirm box
    Then  The confirm box should be accepted and result shows "You selected Ok"

    When  User clicks the button to trigger a prompt and enters "DemoQA Test"
    Then  The prompt result should display "You entered DemoQA Test"

  Scenario: Verify content inside top and bottom frames
    Given User clicks on "Alerts, Frame & Windows" in the left panel
    And   User clicks on "Frames" menu item

    When  User switches to the top frame
    Then  The top frame should display the heading "This is a sample page"

    When  User switches back to the main page
    And   User switches to the bottom frame
    Then  The bottom frame should display the heading "This is a sample page"

    When  User switches back to the main page

@test
  Scenario: Verify new tab opens with correct URL and content
    Given User clicks on "Alerts, Frame & Windows" in the left panel
    And   User clicks on "Browser Windows" menu item
    When  User clicks the "New Tab" button
    Then  A new tab should open with the expected URL
    And   The new tab should display the heading "This is a sample page"
    When  User switches back to the original tab
    Then  The original tab should still display the Browser Windows page
