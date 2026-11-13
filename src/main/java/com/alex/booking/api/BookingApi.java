package com.alex.booking.api;

import com.alex.booking.model.booking.CreateBookingRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class BookingApi {

    private static final String HEALTH_ENDPOINT = "/api/booking/actuator/health";
    private static final String BOOKING_ENDPOINT = "/api/booking";
    private static final String BOOKING_BY_ID_ENDPOINT = "/api/booking/{bookingId}";

    private final RequestSpecification requestSpecification;

    public BookingApi(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response healthCheck() {
        return given()
                .spec(requestSpecification)
                .when()
                .get(HEALTH_ENDPOINT);
    }

    public Response createBooking(CreateBookingRequest bookingRequest) {
        return given()
                .spec(requestSpecification)
                .body(bookingRequest)
                .when()
                .post(BOOKING_ENDPOINT);
    }

    public Response getBooking(int bookingId) {
        return given()
                .spec(requestSpecification)
                .pathParam("bookingId", bookingId)
                .when()
                .get(BOOKING_BY_ID_ENDPOINT);
    }

    public Response deleteBooking(int bookingId) {
        return given()
                .spec(requestSpecification)
                .pathParam("bookingId", bookingId)
                .when()
                .delete(BOOKING_BY_ID_ENDPOINT);
    }

    public Response updateBooking(int bookingId, CreateBookingRequest bookingRequest) {
        return given()
                .spec(requestSpecification)
                .body(bookingRequest)
                .when()
                .put(BOOKING_BY_ID_ENDPOINT, bookingId);
    }
}