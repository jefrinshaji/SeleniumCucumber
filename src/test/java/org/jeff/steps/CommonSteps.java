package org.jeff.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.jeff.util.*;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class CommonSteps {
    WebDriver driver = RunDriver.getDriver();
    ReadProperties propertiesReader = new ReadProperties();
    LocatorUtil locatorUtil = new LocatorUtil();
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Actions action = new Actions(driver);

    @Given("the user navigates to the site {string}")
    public void navigateTo(String urlValue) throws IOException {
        // Use the base URL from the properties file
        propertiesReader.loadProperties("src/test/resources/url.properties");
        String url = propertiesReader.getLocator(urlValue);
        driver.get(url);
    }

    @Then("the user waits till the page is loaded")
    public void waitTillPageLoad() {
        int initiatminutesVal;
        int minutesVal;
        int newMinutesvalue;
        boolean booleanFlag = false;
        initiatminutesVal = LocalDateTime.now().getMinute();
        minutesVal = initiatminutesVal + 2;
        System.out.println("The minutes value is " + minutesVal);
        while (!booleanFlag) {
            newMinutesvalue = LocalDateTime.now().getMinute();
            System.out.println("The minutes value is " + minutesVal);
            if (newMinutesvalue < minutesVal && ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").equals("complete")) {
                booleanFlag = true;
            }
            Long pageLoadtime = (Long) ((JavascriptExecutor) driver).executeScript(
                    "return performance.timing.loadEventEnd - performance.timing.navigationStart;");
            System.out.println("The page took " + pageLoadtime / 1000 + " seconds to load completely");
            Assert.assertTrue("The page has not loaded even though the user waited for a minute", booleanFlag);
        }
    }

    @Then ("the user validates the page title is {string}")
    public void  pageTitle(String title) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Title not as expected, actual title: " + actualTitle,
                title, actualTitle);
    }

    @Then("the user waits for {string} seconds")
    public void waitsSeconds(String seconds) {
        long milliSeconds = Long.parseLong(seconds) * 1000;
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the user validates all links and images are not broken")
    public void linksandImagesNotBroken() {
        // Validate images is not broken
        List<WebElement> imgElements = driver.findElements(By.tagName("img"));

        int respCode = 0;
        for (WebElement element : imgElements) {
            String hrefValue = element.getAttribute("src");
            if (hrefValue != null) {
                if (hrefValue.contains("https://dm-assets.navyfederal.org/is")) {
                    try {
                        HttpURLConnection huc = (HttpURLConnection) (new URL(hrefValue).openConnection());
                        huc.setRequestMethod("HEAD");
                        huc.connect();
                        respCode = huc.getResponseCode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Assert.assertFalse("Image is broken", respCode > 400);
                }
            }
        }

        // Validate links is not broken
        List<WebElement> aElements = driver.findElements(By.tagName("a"));
        respCode = 0;
        for (WebElement element : aElements) {
            String hrefValue = element.getAttribute("href");
            if (hrefValue != null) {
                try {
                    HttpURLConnection huc = (HttpURLConnection) (new URL(hrefValue).openConnection());
                    huc.setRequestMethod("HEAD");
                    huc.connect();
                    respCode = huc.getResponseCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Assert.assertFalse("Link is broken", respCode > 400);
            }
        }
    }

    @Then("the user clicks on {string}")
    public void click(String locator) throws URISyntaxException {
        driver.findElement(locatorUtil.locator(locator)).click();
    }

    @Then("the user force clicks on {string}")
    public void forceClick(String locator) throws URISyntaxException {
        js.executeScript("arguments[0].click();", driver.findElement(locatorUtil.locator(locator)));
    }

    @Then("the use validates the count of {string} is {int}")
    public void countElement(String locator, int count) throws URISyntaxException {
        int actualCount = driver.findElements(locatorUtil.locator(locator)).size();
        Assert.assertEquals("Count not as expected, actual count: " + actualCount,
                count, actualCount);
    }

    @Then("the user resets window size to {string} width and {string} height")
    public void windowResize(String width, String height) {
        driver.manage().window().setSize(new Dimension(Integer.parseInt(width), Integer.parseInt(height)));
    }

    @Then("the user waits till the object {string} is displayed")
    public void waitTillObjectLoad(String locator) throws URISyntaxException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        By xpathVal = locatorUtil.locator(locator);
//        System.out.println("The locator is " + xpathVal);
        Assert.assertTrue("The object did not load in a minute",
                wait.until(ExpectedConditions.visibilityOfElementLocated(xpathVal)).isDisplayed());
    }

    @Then("the user validates {string} is visible")
    public void validateIsVisible(String locator) throws URISyntaxException {
        Assert.assertTrue("Element is not visible",
                driver.findElement(locatorUtil.locator(locator)).isDisplayed());
    }

    @Then("the user validates {string} is in focus")
    public void verifyInFocus(String locator) throws URISyntaxException {
        WebElement activeElement = driver.switchTo().activeElement();
        WebElement element = driver.findElement(locatorUtil.locator(locator));
        Assert.assertEquals("Element is not in focus", element, activeElement);
    }

    @And("the user verifies {string} have {int} elements")
    public void validateNoOfElements(String locator, int size) throws URISyntaxException {
        List<WebElement> elements = driver.findElements(locatorUtil.locator(locator));
        Assert.assertEquals("Number of elements not as expected, Actial Number: " + elements.size(),
                elements.size(), size);
    }

    @Then("the user validates {string} has text and is not empty")
    public void validateHasText(String locator) throws URISyntaxException {
        String text = driver.findElement(locatorUtil.locator(locator)).getText();
        Assert.assertFalse("Element does not contain text", text.isEmpty() || text.isBlank());
    }

    @Then("the user validates {string} text is {string}")
    public void validateHasText(String locator, String expectedText) throws URISyntaxException {
        String actualValue = driver.findElement(locatorUtil.locator(locator)).getText();
        Assert.assertEquals("Element does not has expected text, Actual text: " + actualValue,
                actualValue, expectedText);
    }

    @Then("the user validates {string} contains text {string}")
    public void validateContainsText(String locator, String expectedText) throws URISyntaxException {
        String actualValue = driver.findElement(locatorUtil.locator(locator)).getText();
        Assert.assertTrue("Element does not contain expected text, Actual text: " + actualValue,
                actualValue.contains(expectedText));
    }

    @Then("the user validates {string} text are")
    public void validateTextsAre(String locator, List<String> texts) throws URISyntaxException {
        List<WebElement> elements = driver.findElements(locatorUtil.locator(locator));
        for(int i=0; i< elements.size(); i++) {
            String actualText = elements.get(i).getText();
            Assert.assertEquals("Text not as expected atr index: " + i + ", actual text: " + actualText,
                    texts.get(i), actualText);
        }
    }

    @Then("the user validates element {string} with attribute {string} value is {string}")
    public void validateAttributeHasValue(String locator, String attribute, String expectedValue) throws URISyntaxException {
        String actualValue = driver.findElement(locatorUtil.locator(locator)).getAttribute(attribute);
        Assert.assertEquals("Expected value not found, Actual value: " + actualValue,
                expectedValue, actualValue);
    }

    @Then("the user validates element {string} with attribute {string} contains value {string}")
    public void validateAttributeContainsValue(String locator, String attribute, String expectedValue) throws URISyntaxException {
        String actualValue = driver.findElement(locatorUtil.locator(locator)).getAttribute(attribute);
        Assert.assertTrue("Expected value not found, Actual value: " + actualValue,
                actualValue.contains(expectedValue));
    }

    @Then("the user validates element {string} with attribute {string} does not contains value {string}")
    public void validateAttributeNotContainsValue(String locator, String attribute, String expectedValue) throws URISyntaxException {
        String actualValue = driver.findElement(locatorUtil.locator(locator)).getAttribute(attribute);
        Assert.assertFalse("Attribute value found, Actual value: " + actualValue,
                actualValue.contains(expectedValue));
    }

    @Then("the user validate {string} css attribute {string} value is {string}")
    public void validateHasText(String locator, String attribute, String expectedValue) throws URISyntaxException {
        String actaulValue = driver.findElement(locatorUtil.locator(locator)).getCssValue(attribute);
        Assert.assertEquals("CSS attribute not as expected, Actual value: " + actaulValue,
                expectedValue, actaulValue);
    }

    @Then("the user validates {string} has {int} below items")
    public void validateListWithCount(String locator, int count, DataTable dataTable) throws URISyntaxException {
        List<String> expectedValue = dataTable.asList();
        Assert.assertEquals("Number of items listed not equal to the count", count, expectedValue.size());
        List<WebElement> elements = driver.findElements(locatorUtil.locator(locator));
        Assert.assertEquals("Count didn't match, Actial count = " + elements.size(), elements.size(),
                expectedValue.size());
        int i = 0;
        for (WebElement element : elements) {
            Assert.assertEquals("Text didn't match at index: " + i, element.getText(), expectedValue.get(i));
            i++;
        }
    }

    @Then("the user validates image {string} is not broken")
    public void validateBrokenImage(String locator) throws URISyntaxException {
        WebElement img = driver.findElement(locatorUtil.locator(locator));
        String hrefValue = img.getAttribute("src");
        int respCode = 0;
        boolean verifyBrokenImages = false;
        if (hrefValue.contains("https://dm-assets.navyfederal.org/is")) {
            try {
                HttpURLConnection huc = (HttpURLConnection) (new URL(hrefValue).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                respCode = huc.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (img.getAttribute("naturalWidth").equals("0")) {
            verifyBrokenImages = true;
        }
        Assert.assertFalse("Image is broken", verifyBrokenImages && respCode > 400);
    }
}
