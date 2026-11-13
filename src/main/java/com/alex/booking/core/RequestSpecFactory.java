package com.alex.booking.core;

import com.alex.booking.config.TestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private static final String TOKEN_COOKIE_NAME = "token";

    private RequestSpecFactory() {
    }

    public static RequestSpecification defaultSpec() {
        return baseBuilder()
                .build();
    }

    public static RequestSpecification authenticatedSpec(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException(
                    "Token must not be null or blank"
            );
        }

        return baseBuilder()
                .addCookie(TOKEN_COOKIE_NAME, token)
                .build();
    }

    private static RequestSpecBuilder baseBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(TestConfig.baseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new AllureRestAssured());
    }
}