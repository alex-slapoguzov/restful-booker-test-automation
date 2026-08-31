package com.alex.booking.service;

import com.alex.booking.api.AuthApi;
import com.alex.booking.model.auth.AuthRequest;
import com.alex.booking.model.auth.AuthResponse;
import io.restassured.response.Response;

public final class AuthService {

    private final AuthApi authApi;

    public AuthService(AuthApi authApi) {
        this.authApi = authApi;
    }

    public String createToken(AuthRequest authRequest) {
        Response response = authApi.login(authRequest);

        response.then()
                .statusCode(200);

        AuthResponse authResponse =
                response.as(AuthResponse.class);

        String token = authResponse.getToken();

        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                    "Authentication succeeded, but token is missing"
            );
        }

        return token;
    }
}