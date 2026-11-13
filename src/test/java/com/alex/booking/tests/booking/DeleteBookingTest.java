package com.alex.booking.tests.booking;

import com.alex.booking.context.BookingContext;
import com.alex.booking.data.BookingTestData;
import com.alex.booking.model.booking.BookingResponse;
import com.alex.booking.model.booking.CreateBookingRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseBookingTest {

    @Test
    public void shouldDeleteBooking() {
        CreateBookingRequest bookingRequest = BookingTestData.validBooking();

        BookingContext bookingContext = bookingPreconditions.createBooking(bookingRequest);
        int bookingId = bookingContext.getBookingId();

        registerBookingForCleanup(bookingId);

        Response deleteResponse = bookingApi.deleteBooking(bookingId);
        deleteResponse.then().statusCode(202);

        clearBookingForCleanup();

        Response getResponse = bookingApi.getBooking(bookingId);
        getResponse.then().statusCode(404);

        clearBookingForCleanup();
    }
}