@Google @Regression
Feature: Validate Gmail homepage

  @Gmail
  Scenario: Validate Gmail homepage
    Given the user navigates to the site "google.url"
    And the user waits till the page is loaded

    When the user clicks on "google.gmail.xpath"

    # Validates page title
    And the user validates the page title is "Gmail: Private and secure email at no cost | Google Workspace"

    # Validate header
    Then the user validates "gmail.header.xpath" is visible
    And the user validates element "gmail.header.home.link.xpath" with attribute "href" value is "https://workspace.google.com/gmail/"
    And the user validates element "gmail.header.home.link.xpath" with attribute "aria-label" value is "Google Gmail"
    
    Then the user validates image "gmail.header.home.img.xpath" is not broken
    