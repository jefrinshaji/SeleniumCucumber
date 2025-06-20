package org.jeff;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",  // Path to the feature files
        glue = {"org.jeff"},  // Package name where step definitions are located
        tags = "@Regression",
        monochrome = true,  // Makes console output more readable
        plugin = {"pretty",
                "html:target/cucumber-reports.html"
        } // Plugins for reporting
)
public class TestRunner {
}
