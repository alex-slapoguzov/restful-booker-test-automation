package com.alex.booking.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonTemplateProcessor {

    private static final Pattern PLACEHOLDER_PATTERN =
            Pattern.compile(
                    "^\\$\\{([A-Za-z][A-Za-z0-9._-]*)}$"
            );

    private JsonTemplateProcessor() {
    }

    public static JsonNode process(JsonNode template) {
        if (template == null) {
            throw new IllegalArgumentException(
                    "JSON template must not be null"
            );
        }

        Map<String, JsonNode> selectedValues = new HashMap<>();

        return resolveNode(
                template.deepCopy(),
                selectedValues
        );
    }

    private static JsonNode resolveNode(
            JsonNode node,
            Map<String, JsonNode> selectedValues
    ) {
        if (node.isTextual()) {
            return resolveTextNode(node, selectedValues);
        }

        if (node.isObject()) {
            return resolveObjectNode(
                    (ObjectNode) node,
                    selectedValues
            );
        }

        if (node.isArray()) {
            return resolveArrayNode(
                    (ArrayNode) node,
                    selectedValues
            );
        }

        return node;
    }

    private static JsonNode resolveTextNode(
            JsonNode node,
            Map<String, JsonNode> selectedValues
    ) {
        Matcher matcher =
                PLACEHOLDER_PATTERN.matcher(node.asText());

        if (!matcher.matches()) {
            return node;
        }

        String poolName = matcher.group(1);

        JsonNode selectedValue = selectedValues.computeIfAbsent(
                poolName,
                RandomValueProvider::getRandomValue
        );

        return selectedValue.deepCopy();
    }

    private static JsonNode resolveObjectNode(
            ObjectNode objectNode,
            Map<String, JsonNode> selectedValues
    ) {
        Iterator<String> fieldNames = objectNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldValue = objectNode.get(fieldName);

            objectNode.set(
                    fieldName,
                    resolveNode(fieldValue, selectedValues)
            );
        }

        return objectNode;
    }

    private static JsonNode resolveArrayNode(
            ArrayNode arrayNode,
            Map<String, JsonNode> selectedValues
    ) {
        for (int index = 0; index < arrayNode.size(); index++) {
            JsonNode element = arrayNode.get(index);

            arrayNode.set(
                    index,
                    resolveNode(element, selectedValues)
            );
        }

        return arrayNode;
    }
}