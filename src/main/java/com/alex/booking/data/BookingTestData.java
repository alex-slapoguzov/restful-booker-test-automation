package com.alex.booking.data;

import com.alex.booking.model.booking.BookingDates;
import com.alex.booking.model.booking.CreateBookingRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public final class BookingTestData {

    private static final int DEFAULT_ROOM_ID = 1;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ISO_LOCAL_DATE;

    private BookingTestData() {
    }

    public static CreateBookingRequest validBooking() {
        long daysOffset = ThreadLocalRandom.current()
                .nextLong(365, 3650);

        LocalDate checkInDate = LocalDate.now()
                .plusDays(daysOffset);

        LocalDate checkOutDate = checkInDate.plusDays(2);

        BookingDates bookingDates = new BookingDates(
                checkInDate.format(DATE_FORMATTER),
                checkOutDate.format(DATE_FORMATTER)
        );

        return new CreateBookingRequest(
                DEFAULT_ROOM_ID,
                "Alex",
                "Tester",
                true,
                "alex.tester@example.com",
                "07123456789",
                bookingDates
        );
    }
}