package com.alex.booking.tests.booking;

import com.alex.booking.context.BookingContext;
import com.alex.booking.model.booking.BookingResponse;
import com.alex.booking.model.booking.CreateBookingRequest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GetBookingTest extends BaseBookingTest {

    private static final String TEST_DATA_FILE = "testdata/booking/create-booking.json";

    @Test
    public void shouldReturnBookingById() {
        BookingContext bookingContext = bookingPreconditions.createBooking(TEST_DATA_FILE);

        registerBookingForCleanup(bookingContext.getBookingId());

        BookingResponse actualBooking = bookingService.getBooking(bookingContext.getBookingId());

        CreateBookingRequest expectedBooking = bookingContext.getRequest();

        assertEquals(actualBooking.getBookingId(), bookingContext.getBookingId());
        assertEquals(actualBooking.getFirstName(), expectedBooking.getFirstName());
        assertEquals(actualBooking.getLastName(), expectedBooking.getLastName());
        assertEquals(actualBooking.getRoomId(), expectedBooking.getRoomId());
        assertEquals(actualBooking.getDepositPaid(), expectedBooking.getDepositPaid());
        assertEquals(actualBooking.getBookingDates().getCheckIn(), expectedBooking.getBookingDates().getCheckIn());
        assertEquals(actualBooking.getBookingDates().getCheckOut(), expectedBooking.getBookingDates().getCheckOut());
    }
}