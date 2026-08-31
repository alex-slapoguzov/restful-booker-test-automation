package com.alex.booking.precondition;

import com.alex.booking.context.BookingContext;
import com.alex.booking.data.JsonDataReader;
import com.alex.booking.model.booking.BookingResponse;
import com.alex.booking.model.booking.CreateBookingRequest;
import com.alex.booking.service.BookingService;

import java.util.Objects;

public final class BookingPreconditions {

    private final BookingService bookingService;

    public BookingPreconditions(BookingService bookingService) {
        this.bookingService = Objects.requireNonNull(bookingService, "BookingService must not be null");
    }

    public BookingContext createBooking(String resourcePath, String token) {
        CreateBookingRequest request = JsonDataReader.readTemplate(resourcePath, CreateBookingRequest.class);

        return createBooking(request, token);
    }

    public BookingContext createBooking(CreateBookingRequest request, String token) {
        BookingResponse createdBooking = bookingService.createBooking(request, token);

        return new BookingContext(request, createdBooking);
    }
}