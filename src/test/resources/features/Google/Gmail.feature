@Google @Regression
Feature: Validate Gmail homepage

  @Google
  Scenario: Validate Gmail homepage
    Given the user navigates to the site "google.url"
    And the user waits till the page is loaded

    When the user clicks on "google.gmail.xpath"

    # Validates page title
    And the user validates the page title is "Gmail: Private and secure email at no cost | Google Workspace"