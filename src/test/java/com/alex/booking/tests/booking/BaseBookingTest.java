package com.alex.booking.tests.booking;

import com.alex.booking.api.BookingApi;
import com.alex.booking.config.TestConfig;
import com.alex.booking.core.RequestSpecFactory;
import com.alex.booking.model.auth.AuthRequest;
import com.alex.booking.precondition.BookingPreconditions;
import com.alex.booking.service.BookingService;
import com.alex.booking.tests.BaseApiTest;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseBookingTest extends BaseApiTest {

    private Integer bookingIdForCleanup;

    @BeforeMethod(alwaysRun = true)
    public void setUpBookingTest() {
        bookingIdForCleanup = null;

        AuthRequest authRequest = new AuthRequest(
                TestConfig.authUsername(),
                TestConfig.authPassword()
        );

        String token = authService.createToken(authRequest);

        RequestSpecification authenticatedSpecification = RequestSpecFactory.authenticatedSpec(token);

        bookingApi = new BookingApi(authenticatedSpecification);
        bookingService = new BookingService(bookingApi);
        bookingPreconditions = new BookingPreconditions(bookingService);
    }

    protected void registerBookingForCleanup(Integer bookingId) {
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
        if (bookingIdForCleanup == null) {
            return;
        }

        try {
            bookingService.deleteBooking(bookingIdForCleanup);
        } finally {
            bookingIdForCleanup = null;
        }
    }
}