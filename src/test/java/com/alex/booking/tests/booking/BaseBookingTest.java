package com.alex.booking.tests.booking;

import com.alex.booking.config.TestConfig;
import com.alex.booking.model.auth.AuthRequest;
import com.alex.booking.tests.BaseApiTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseBookingTest extends BaseApiTest {

    protected String token;

    private Integer bookingIdForCleanup;

    @BeforeMethod(alwaysRun = true)
    public void authenticate() {
        bookingIdForCleanup = null;

        AuthRequest authRequest = new AuthRequest(
                TestConfig.authUsername(),
                TestConfig.authPassword()
        );

        token = authService.createToken(authRequest);
    }

    protected void registerBookingForCleanup(
            Integer bookingId
    ) {
        if (bookingId == null || bookingId <= 0) {
            throw new IllegalArgumentException(
                    "Booking ID for cleanup must be greater than zero"
            );
        }

        bookingIdForCleanup = bookingId;
    }

    protected void clearBookingForCleanup() {
        bookingIdForCleanup = null;
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpBooking() {
        if (bookingIdForCleanup == null || token == null) {
            return;
        }

        try {
            bookingService.deleteBooking(
                    bookingIdForCleanup,
                    token
            );
        } finally {
            bookingIdForCleanup = null;
            token = null;
        }
    }
}