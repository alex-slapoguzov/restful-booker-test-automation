package com.alex.booking.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonDataReader {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private JsonDataReader() {
    }

    public static <T> T read(
            String resourcePath,
            Class<T> targetClass
    ) {
        try (InputStream inputStream =
                     openResource(resourcePath)) {

            return OBJECT_MAPPER.readValue(
                    inputStream,
                    targetClass
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read JSON file: " + resourcePath,
                    exception
            );
        }
    }

    public static <T> T readTemplate(
            String resourcePath,
            Class<T> targetClass
    ) {
        try (InputStream inputStream =
                     openResource(resourcePath)) {

            JsonNode template =
                    OBJECT_MAPPER.readTree(inputStream);

            JsonNode processedTemplate =
                    JsonTemplateProcessor.process(template);

            return OBJECT_MAPPER.treeToValue(
                    processedTemplate,
                    targetClass
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read JSON template: " + resourcePath,
                    exception
            );
        }
    }

    private static InputStream openResource(
            String resourcePath
    ) {
        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

        InputStream inputStream =
                classLoader.getResourceAsStream(resourcePath);

        if (inputStream == null) {
            throw new IllegalArgumentException(
                    "Resource was not found: " + resourcePath
            );
        }

        return inputStream;
    }
}