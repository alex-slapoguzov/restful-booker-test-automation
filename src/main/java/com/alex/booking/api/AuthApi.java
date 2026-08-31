package com.alex.booking.api;

import com.alex.booking.model.auth.AuthRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class AuthApi {

    private static final String LOGIN_ENDPOINT = "/api/auth/login";

    private final RequestSpecification requestSpecification;

    public AuthApi(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response login(AuthRequest authRequest) {
        return given()
                .spec(requestSpecification)
                .body(authRequest)
                .when()
                .post(LOGIN_ENDPOINT);
    }
}