package com.alex.booking.tests;

import com.alex.booking.api.AuthApi;
import com.alex.booking.api.BookingApi;
import com.alex.booking.core.RequestSpecFactory;
import com.alex.booking.precondition.BookingPreconditions;
import com.alex.booking.service.AuthService;
import com.alex.booking.service.BookingService;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    protected AuthApi authApi;
    protected BookingApi bookingApi;

    protected AuthService authService;
    protected BookingService bookingService;

    protected BookingPreconditions bookingPreconditions;

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        RequestSpecification requestSpecification =
                RequestSpecFactory.defaultSpec();

        authApi = new AuthApi(requestSpecification);
        bookingApi = new BookingApi(requestSpecification);

        authService = new AuthService(authApi);
        bookingService = new BookingService(bookingApi);

        bookingPreconditions = new BookingPreconditions(bookingService);
    }
}