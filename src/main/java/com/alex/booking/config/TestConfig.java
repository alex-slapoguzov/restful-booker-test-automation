package com.alex.booking.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestConfig {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private TestConfig() {
    }

    public static String baseUrl() {
        return getRequiredValue("base.url", "BASE_URL");
    }

    public static String authUsername() {
        return getRequiredValue("auth.username", "AUTH_USERNAME");
    }

    public static String authPassword() {
        return getRequiredValue("auth.password", "AUTH_PASSWORD");
    }

    public static boolean isLoggingEnabled() {
        String value = getRequiredValue("logging.enabled", "LOGGING_ENABLED");
        return Boolean.parseBoolean(value);
    }

    private static String getRequiredValue(String propertyName, String environmentName) {
        String systemValue = System.getProperty(propertyName);

        if (hasText(systemValue)) {
            return systemValue;
        }

        String environmentValue = System.getenv(environmentName);

        if (hasText(environmentValue)) {
            return environmentValue;
        }

        String fileValue = PROPERTIES.getProperty(propertyName);

        if (hasText(fileValue)) {
            return fileValue;
        }

        throw new IllegalStateException(
                "Configuration value was not found. Property: "
                        + propertyName
                        + ", environment variable: "
                        + environmentName
        );
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream =
                     TestConfig.class
                             .getClassLoader()
                             .getResourceAsStream(CONFIG_FILE)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Configuration file was not found: "
                                + CONFIG_FILE
                );
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read configuration file: "
                            + CONFIG_FILE,
                    exception
            );
        }
    }
}