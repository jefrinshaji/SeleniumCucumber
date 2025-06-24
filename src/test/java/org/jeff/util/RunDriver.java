package org.jeff.util;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Locale;

public class RunDriver {
    private static WebDriver driver;

    @Before
    public static void setUp() {
        String windowsPathReference = "src/test/resources/BrowserDriver/windows/";
        String macPathReference = "src/test/resources/BrowserDriver/mac/";
        String linuxPathReference = "src/test/resources/BrowserDriver/linux/";

        // Detect the operating system
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String driverPath = "";

        // Get the browser type from the command line, default to Chrome if not specified
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        // Set the appropriate driver path based on the OS and browser type
        switch (browser) {
            case "chrome":
                if (os.contains("win")) {
                    driverPath = windowsPathReference + "chromedriver.exe";
                } else if (os.contains("mac")) {
                    driverPath = macPathReference + "chromedriver";
                } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                    driverPath = linuxPathReference + "chromedriver";
                }
                System.setProperty("webdriver.chrome.driver", driverPath);
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                if (os.contains("win")) {
                    driverPath = windowsPathReference + "geckodriver.exe";
                } else if (os.contains("mac")) {
                    driverPath = macPathReference + "geckodriver";
                } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                    driverPath = linuxPathReference + "geckodriver";
                }
                System.setProperty("webdriver.gecko.driver", driverPath);
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                if (os.contains("win")) {
                    driverPath = windowsPathReference + "msedgedriver.exe";
                } else if (os.contains("mac")) {
                    driverPath = macPathReference + "msedgedriver";
                } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                    driverPath = linuxPathReference + "msedgedriver";
                }
                System.setProperty("webdriver.edge.driver", driverPath);
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }
        // Maximize the browser window
        driver.manage().window().maximize();
    }


    public static WebDriver getDriver() {
        if (driver == null) {
            System.out.println("WebDriver not initialized. @Before didn't run");
        }
        return driver;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // prevent reuse of closed driver
        }
    }
}
