@Google @Regression
Feature: Validate Google homepage

  @Google
  Scenario: Validate Google homepage
    Given the user navigates to the site "google.url"
    And the user waits till the page is loaded

    # Validates page title
    Then the user validates the page title is "Google"

    # Validate Navigation links
    Then the user validates element "google.navigation.xpath" has 2 items
      | About |
      | Store |
    And the user waits till the object "google.gmail.xpath" is displayed
    And the user validates element "google.gmail.xpath" with attribute "aria-label" contains value "Gmail"
#    And the user validate "google.gmail.xpath" css attribute "href" value is "https://mail.google.com/mail/&ogbl"
    And the user validates element "google.images.xpath" with attribute "aria-label" contains value "Search for Images"
#    And the user validate "google.images.xpath" css attribute "href" value is "https://www.google.com/imghp?hl=en&ogbl"
    And the user validates element "google.apps.xpath" with attribute "aria-label" contains value "Google apps"
    Then the user validates element "google.apps.window.xpath" with attribute "style" contains value "visibility: hidden;"
    And the user validates element "google.signinButton.xpath" with attribute "aria-label" contains value "Sign in"

    # Validate Google apps window
    When the user force clicks on "google.apps.xpath"
    And the user validates element "google.apps.window.xpath" with attribute "style" does not contains value "visibility: hidden;"
    
    # Validate input/search text area
    Then the user validates "google.searchicon.xpath" is visible
    And the user validates "google.searchbox.xpath" is visible
    And the user validates "google.voicesearch.xpath" is visible
    And the user validates "google.imagesearch.xpath" is visible
    Then the user validates element "google.imagesearch.xpath" with attribute "data-base-lens-url" value is "https://lens.google.com"

    #Validate search buttons
    Then the user validates "google.searchbutton.xpath" is visible
    And the user validates element "google.searchbutton.xpath" with attribute "aria-label" value is "Google Search"
    Then the user validates "google.feelingluckybutton.xpath" is visible
    And the user validates element "google.feelingluckybutton.xpath" with attribute "aria-label" value is "I'm Feeling Lucky"
    
    # Validate languages supported
    Then the user validates element "google.languages.xpath" has 9 items
      | हिन्दी  |
      | বাংলা   |
      | తెలుగు  |
      | मराठी   |
      | தமிழ்   |
      | ગુજરાતી |
      | ಕನ್ನಡ   |
      | മലയാളം  |
      | ਪੰਜਾਬੀ  |

    # Validate global footer
    # Country
    Then the user validates "google.footer.country.xpath" text is "India"
    And the user validates "google.footer.ad.xpath" text is "Advertising"
    And the user validates "google.business.xpath" text is "Business"
    And the user validates "google.footer.how.xpath" contains text "How Search works"