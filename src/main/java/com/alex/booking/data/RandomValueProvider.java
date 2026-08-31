package com.alex.booking.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomValueProvider {

    private static final String DATA_POOLS_PATH =
            "testdata/dictionaries/random-values.json";

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private static final Map<String, List<JsonNode>> DATA_POOLS =
            loadDataPools();

    private RandomValueProvider() {
    }

    public static JsonNode getRandomValue(String poolName) {
        List<JsonNode> values = DATA_POOLS.get(poolName);

        if (values == null) {
            throw new IllegalArgumentException(
                    "Random data pool was not found: " + poolName
            );
        }

        if (values.isEmpty()) {
            throw new IllegalStateException(
                    "Random data pool is empty: " + poolName
            );
        }

        int randomIndex =
                ThreadLocalRandom.current().nextInt(values.size());

        return values.get(randomIndex).deepCopy();
    }

    private static Map<String, List<JsonNode>> loadDataPools() {
        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream =
                     classLoader.getResourceAsStream(DATA_POOLS_PATH)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Random data file was not found: "
                                + DATA_POOLS_PATH
                );
            }

            return OBJECT_MAPPER.readValue(
                    inputStream,
                    new TypeReference<Map<String, List<JsonNode>>>() {
                    }
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read random data file: "
                            + DATA_POOLS_PATH,
                    exception
            );
        }
    }
}