package com.alex.booking.tests.auth;

import com.alex.booking.config.TestConfig;
import com.alex.booking.model.auth.AuthRequest;
import com.alex.booking.model.auth.AuthResponse;
import com.alex.booking.tests.BaseApiTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationTest extends BaseApiTest {

    @Test
    public void shouldCreateTokenForValidCredentials() {
        AuthRequest authRequest = new AuthRequest(
                TestConfig.authUsername(),
                TestConfig.authPassword()
        );

        Response response = authApi.login(authRequest);

        response.then()
                .statusCode(200);

        AuthResponse authResponse =
                response.as(AuthResponse.class);

        Assert.assertNotNull(
                authResponse.getToken(),
                "Token must be present in the response body"
        );

        Assert.assertFalse(
                authResponse.getToken().isBlank(),
                "Token must not be blank"
        );
    }
}