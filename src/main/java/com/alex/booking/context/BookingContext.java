package com.alex.booking.context;

import com.alex.booking.model.booking.BookingResponse;
import com.alex.booking.model.booking.CreateBookingRequest;

import java.util.Objects;

public final class BookingContext {

    private final CreateBookingRequest request;
    private final BookingResponse createdBooking;

    public BookingContext(
            CreateBookingRequest request,
            BookingResponse createdBooking
    ) {
        this.request = Objects.requireNonNull(
                request,
                "Booking request must not be null"
        );

        this.createdBooking = Objects.requireNonNull(
                createdBooking,
                "Created booking must not be null"
        );
    }

    public CreateBookingRequest getRequest() {
        return request;
    }

    public BookingResponse getCreatedBooking() {
        return createdBooking;
    }

    public Integer getBookingId() {
        return createdBooking.getBookingId();
    }
}