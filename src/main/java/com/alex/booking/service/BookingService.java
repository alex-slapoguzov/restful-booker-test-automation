package com.alex.booking.service;

import com.alex.booking.api.BookingApi;
import com.alex.booking.model.booking.CreateBookingRequest;
import com.alex.booking.model.booking.BookingResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public final class BookingService {

    private final BookingApi bookingApi;

    public BookingService(BookingApi bookingApi) {
        this.bookingApi = bookingApi;
    }

    public BookingResponse createBooking(CreateBookingRequest bookingRequest) {
        Response response = bookingApi.createBooking(bookingRequest);

        response.then()
                .statusCode(201)
                .contentType(ContentType.JSON);

        BookingResponse createdBooking =
                response.as(BookingResponse.class);

        Integer bookingId = createdBooking.getBookingId();

        if (bookingId == null || bookingId <= 0) {
            throw new IllegalStateException(
                    "Booking was created, but booking ID is missing"
            );
        }

        return createdBooking;
    }

    public void deleteBooking(int bookingId) {
        Response response = bookingApi.deleteBooking(bookingId);

        response.then().statusCode(202);
    }

    public BookingResponse getBooking(int bookingId) {
        Response response = bookingApi.getBooking(bookingId);

        response.then().statusCode(200);
        return response.as(BookingResponse.class);
    }
}