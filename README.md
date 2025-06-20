# Welcome to Jeff's BDD Test Automation Framework (Selenium-Java) starter kit

This project is a starter kit that demonstrates the pattems and practices that have been developed to support automated
UI testing for a website. This project currently showcases the following functionality:
* A Test Suite using the test runner
* Support for local Selenium drivers
* Cucumber integration with BDD style tests written using Gherkin
* Support for click and keystroke metrics counting and mouse movements for tests

## Local Setup

Installing dependencies can be done with the following command:

```shell
mvn install
```

### WebDrivers

The default driver for testing being used is `chromedriver` update the driver stored in `BrowserDriver.directory`

## Run tests via command line

This is a Java/Maven project so tests can be compiled and run with basic Maven commands.


In order to run all tests with the default configuration, run this command:

```shell
mvn test
```

### Running specefic tests

The default test command will run all tests in the project.

You can specify a subset of tests to run using the tags that are placed on the Feature/Scenarios that have been created.

1. You can modify `TestRunner.java`. The existing definition does not specify any tags to run so it will run everything. \
   You can add a list by adding the `tags` property to the `CucumberOptions` attribute as seen below:

```java
@CucumberOptions(
        features = "src/main/resources/features",  // Path to the feature files
        glue = {"org.jeff"},  // Package name where step definitions are located
        tags = "@Regression",
        monochrome = true,  // Makes console output more readable
        plugin = {"pretty",
                "html:target/cucumber-reports.html"
        } // Plugins for reporting
)
```

2. You can specify the tags using `—D` when you run the tests from the command line. An example is shown below:

```shell
mvn test '-Dcucumber.filter.tags="@Regression"'
```

Both the code example and command line example will execute all tests tagged with `@Regression`.

### Browser and Driver setup

The browser and driver initialization is done in `RunDriver.java` and all browser drivers are stored in `BrowserDriver.directory`

You can run the test with the non-deafult browser, chrome, by running the following command:

```shell
mvn test '-Dbrowser=edge'
```

You can add new options to existing configurations or add entirely new browser configurations by editing this file.

When tests run they will choose the url that is specified in `url.properties`.

## Locators

Locators are properties that define how to find web elements using a property name and
locator strategy. Locator properties are used in feature files to identify a web element or in custom Step
definitions.
Locator strategies are id, name, xpath, css, tag, link and partialLink.

### Locator file and property recommendations

### The name of the locator file should follow the guidelines or rules below.

    1. The name of the locator file Should be named after the most logical common sense page name.
    2. Any words in the file name should be in Pascal cases.

    example: HomePage.properties

### Locators should follow the guidelines and rules below.

    1. Words in the locator name should be separated by dots.
    2. The locator names should contain all lower case letters.
    3. The locator name should end with .locatorstartegy

    example: common.body.id = body
           : google.navigation.xpath = //div[@role='navigation']/a

### Referencing a locator in a feature file

For example, if you have locator `google.navigation.xpath = //div[@role='navigation']/a` in a locator file then you can
call the locator in the feature file step as shown below.

```gherkin
Then the user validates "google.navigation.xpath" is visible
```

### LocatorUtil

LocatorUtil is a java class in the **org.jeff** project which loads locators found in locator property files.

This class will load locators found in locator property files found in the `locator.directory`

LocatorUtil are used in custom steps shown in the example below.

```java
@Then("the user validates {string} is visible")
public void validateIsVisible(String locator) {
    Assert.assertTrue("Element is not visible",
    driver.findElement(locatorUtil.locator(locator)).isDisplayed());
}
```
