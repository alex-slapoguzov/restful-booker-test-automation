package com.alex.booking.tests.booking;

import com.alex.booking.context.BookingContext;
import com.alex.booking.data.BookingTestData;
import com.alex.booking.data.JsonDataReader;
import com.alex.booking.model.booking.BookingResponse;
import com.alex.booking.model.booking.CreateBookingRequest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Booking API")
@Feature("Booking management")
public class UpdateBookingTest extends BaseBookingTest {

    private static final String UPDATE_DATA_FILE = "testdata/booking/update-booking.json";

    @Test(description = "Update an existing booking using PUT")
    @Story("Update booking")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Creates a booking, updates all its fields through PUT and verifies saved data through GET")
    public void shouldUpdateBooking() {
        CreateBookingRequest initialRequest = BookingTestData.validBooking();

        BookingContext bookingContext = bookingPreconditions.createBooking(initialRequest);
        Integer bookingId = bookingContext.getBookingId();

        registerBookingForCleanup(bookingId);

        CreateBookingRequest updateRequest = JsonDataReader.readTemplate(UPDATE_DATA_FILE, CreateBookingRequest.class);

        Response updateResponse = bookingApi.updateBooking(bookingId, updateRequest);

        updateResponse.then().statusCode(200);

        Response getResponse = bookingApi.getBooking(bookingId);

        getResponse.then().statusCode(200);

        BookingResponse savedBooking = getResponse.as(BookingResponse.class);

        assertBookingMatches(bookingId, updateRequest, savedBooking);
    }

    private void assertBookingMatches(Integer expectedBookingId, CreateBookingRequest expected, BookingResponse actual) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actual.getBookingId(), expectedBookingId, "Booking ID is incorrect");
        softAssert.assertEquals(actual.getRoomId(), expected.getRoomId(), "Room ID was not updated");
        softAssert.assertEquals(actual.getFirstName(), expected.getFirstName(), "First name was not updated");
        softAssert.assertEquals(actual.getLastName(), expected.getLastName(), "Last name was not updated");
        softAssert.assertEquals(actual.getDepositPaid(), expected.getDepositPaid(), "Deposit status was not updated");
        softAssert.assertEquals(actual.getBookingDates().getCheckIn(), expected.getBookingDates().getCheckIn(), "Check-in date was not updated");
        softAssert.assertEquals(actual.getBookingDates().getCheckOut(), expected.getBookingDates().getCheckOut(), "Check-out date was not updated");

        softAssert.assertAll();
    }
}