package org.jeff.steps;

import io.cucumber.java.en.Then;
import org.jeff.util.LocatorUtil;
import org.jeff.util.RunDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.URISyntaxException;

public class GoogleSteps {
    WebDriver driver = RunDriver.getDriver();
    LocatorUtil locatorUtil = new LocatorUtil();
    CommonSteps commonSteps = new CommonSteps();

    @Then("the user changes the page theme")
    public void changeTheme() throws URISyntaxException {
        By elementLocator = locatorUtil.locator("google.footer.settings.darktheme.xpath");
        WebElement themeButton = driver.findElement(elementLocator);
        String theme = themeButton.getText();
        if(theme.contains("On")) {
            System.out.println("Current theme is dark");
            themeButton.click();
            commonSteps.click("google.footer.settings.xpath");
            commonSteps.waitTillObjectLoad("google.footer.settings.darktheme.xpath");
            //getting text again to update the text and the element got stale
            theme = driver.findElement(elementLocator).getText();
            Assert.assertEquals("Failure to change theme, actual text: " + theme,
                    "Dark theme: Off", theme);
        } else if(theme.contains("Off")) {
            System.out.println("Current theme is light");
            themeButton.click();
            commonSteps.click("google.footer.settings.xpath");
            commonSteps.waitTillObjectLoad("google.footer.settings.darktheme.xpath");
            //getting text again to update the text and the element got stale
            theme = driver.findElement(elementLocator).getText();
            Assert.assertEquals("Failure to change theme, actual text: " + theme,
                    "Dark theme: On", theme);
        }
    }
}
