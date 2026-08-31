package com.alex.booking.model.booking;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookingRequest {

    @JsonProperty("roomid")
    private Integer roomId;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("depositpaid")
    private Boolean depositPaid;

    private String email;

    private String phone;

    @JsonProperty("bookingdates")
    private BookingDates bookingDates;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(
            Integer roomId,
            String firstName,
            String lastName,
            Boolean depositPaid,
            String email,
            String phone,
            BookingDates bookingDates
    ) {
        this.roomId = roomId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.depositPaid = depositPaid;
        this.email = email;
        this.phone = phone;
        this.bookingDates = bookingDates;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(Boolean depositPaid) {
        this.depositPaid = depositPaid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BookingDates getBookingDates() {
        return bookingDates;
    }

    public void setBookingDates(BookingDates bookingDates) {
        this.bookingDates = bookingDates;
    }
}