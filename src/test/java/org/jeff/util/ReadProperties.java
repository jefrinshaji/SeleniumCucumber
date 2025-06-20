package org.jeff.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ReadProperties {
    Properties props = new Properties();

    // Read all properties file from one or more file(s)
    public void loadProperties(String... filePaths) throws IOException {
        for (String path : filePaths) {
            FileInputStream fis = new FileInputStream(path);
            props.load(fis);
            fis.close();
        }
    }

    public String readLocatorValue(String targetKey) throws URISyntaxException {
        String value = null;
        // Load 'locators' directory from resources
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resourceUrl = classLoader.getResource("locators");

        if (resourceUrl == null) {
            System.err.println("Directory 'locators' not found in resources.");
            return null;
        }

        Path folderPath = Paths.get(resourceUrl.toURI());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "*.properties")) {
            boolean found = false;
            for (Path path : stream) {
                try (InputStream is = Files.newInputStream(path)) {
                    props.load(is);
                    if (props.containsKey(targetKey)) {
                        System.out.println("Searched in: " + path.getFileName());
                        value = props.getProperty(targetKey);
//                        System.out.println("  Key: " + targetKey + ", Value: " + value);
                        found = true;
                    }
                }
            }
            if (!found) {
                System.out.println("Key '" + targetKey + "' not found in any properties file.");
            }
        } catch (IOException e) {
            System.err.println("Error reading properties files: " + e.getMessage());
        }
        return value;
    }

    public String getLocator(String key) {
        return props.getProperty(key);
    }
}
