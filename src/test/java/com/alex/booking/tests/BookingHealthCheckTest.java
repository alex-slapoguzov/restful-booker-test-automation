package com.alex.booking.tests;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class BookingHealthCheckTest extends BaseApiTest {

    @Test
    public void shouldReturnBookingServiceHealth() {
        bookingApi.healthCheck()
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}