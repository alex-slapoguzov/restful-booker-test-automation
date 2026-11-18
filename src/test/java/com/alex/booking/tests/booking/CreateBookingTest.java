package com.alex.booking.tests.booking;

import com.alex.booking.data.JsonDataReader;
import com.alex.booking.model.booking.CreateBookingRequest;
import com.alex.booking.model.booking.BookingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CreateBookingTest extends BaseBookingTest {

    private static final String TEST_DATA_FILE = "testdata/booking/create-booking.json";

    @Test
    public void shouldCreateBooking() {
        CreateBookingRequest bookingRequest = JsonDataReader.readTemplate(TEST_DATA_FILE, CreateBookingRequest.class);

        Response response = bookingApi.createBooking(bookingRequest);

        response.then().statusCode(201).contentType(ContentType.JSON);

        BookingResponse createdBooking = response.as(BookingResponse.class);

        registerBookingForCleanup(createdBooking.getBookingId());

        assertNotNull(createdBooking.getBookingId(), "Booking ID must be present");
        assertTrue(createdBooking.getBookingId() > 0, "Booking ID must be greater than zero");
        assertEquals(createdBooking.getRoomId(), bookingRequest.getRoomId(), "Room ID must match the request");
        assertEquals(createdBooking.getFirstName(), bookingRequest.getFirstName(), "First name must match the request");
        assertEquals(createdBooking.getLastName(), bookingRequest.getLastName(), "Last name must match the request");
        assertEquals(createdBooking.getDepositPaid(), bookingRequest.getDepositPaid(), "Deposit flag must match the request");
        assertNotNull(createdBooking.getBookingDates(), "Booking dates must be present");
        assertEquals(createdBooking.getBookingDates().getCheckIn(), bookingRequest.getBookingDates().getCheckIn(), "Check-in date must match the request");
        assertEquals(createdBooking.getBookingDates().getCheckOut(), bookingRequest.getBookingDates().getCheckOut(), "Check-out date must match the request");
    }

    @Test(dataProvider = "TestBookingWithoutFields")
    public void createBookingWithoutFields(String fieldName,String errorFieldName ) {
        CreateBookingRequest bookingRequest = JsonDataReader.readTemplate(TEST_DATA_FILE, CreateBookingRequest.class);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestBody = mapper.valueToTree(bookingRequest);
        requestBody.remove(fieldName);
        assertFalse(requestBody.has(fieldName));

        Response response = bookingApi.createBooking(requestBody);
        response.then().statusCode(400);
        assertTrue(response.jsonPath().getList("errors").contains(String.format("%s should not be blank", errorFieldName)));
    }

    @DataProvider(name = "TestBookingWithoutFields")
    public Object[][] testBookingWithoutFields() {
        return new Object[][] {
                { "firstname", "Firstname" },
                { "lastname", "Lastname" }
        };
    }
}