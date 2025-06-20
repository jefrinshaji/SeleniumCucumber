package org.jeff.util;

import org.openqa.selenium.By;
import java.net.URISyntaxException;

public class LocatorUtil {
    ReadProperties readProperties = new ReadProperties();

    public final By locator(String targetKey) throws URISyntaxException {
        String value = readProperties.readLocatorValue(targetKey);
        By by;
        int lastDotIndex = targetKey.lastIndexOf('.');
        String locatorType = targetKey.substring(lastDotIndex + 1);
        switch (locatorType) {
            case "id":
                by = By.id(value);
                break;
            case "name":
                by = By.name(value);
                break;
            case "xpath":
                by = By.xpath(value);
                break;
            case "css":
                by = By.cssSelector(value);
                break;
            case "classname":
                by = By.className(value);
                break;
            case "tag":
                by = By.tagName(value);
                break;
            case "link":
                by = By.linkText(value);
                break;
            case "partialLink":
                by = By.partialLinkText(value);
                break;
            default:
                by = null;
        }
        return by;
    }
}