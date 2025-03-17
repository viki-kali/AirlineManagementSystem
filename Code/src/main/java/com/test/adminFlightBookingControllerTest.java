package com.test;

import com.controller.FlightBookingController;
import com.model.FlightBooking;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

public class adminFlightBookingControllerTest {
    private FlightBookingController controller;
    private FlightBooking testBooking;

    @Before
    public void setUp() {
        controller = new FlightBookingController();
        testBooking = new FlightBooking(
            0, // BookingID (auto-generated)
            4004, // FlightID_Booking
            10000, // UserID_Booking
            2, // NoOfSeats
            "Economy", // SeatCategory
            new Date(System.currentTimeMillis()), // DateOfBooking
            Date.valueOf("2025-06-15"), // DateOfTravel
            "Booked", // BookingStatus
            500.0, // BookingAmount
            0.0 // RefundAmount
        );
        controller.addFlightBooking(testBooking);
    }

    @Test
    public void testGetFlightBooking() {
        FlightBooking booking = controller.getFlightBooking(testBooking.getBookingID());
        assertNotNull("Booking should not be null", booking);
        assertEquals("Booking ID should match", testBooking.getBookingID(), booking.getBookingID());
        assertEquals("Flight ID should match", testBooking.getFlightID(), booking.getFlightID());
    }

    @Test
    public void testUpdateFlightBooking() {
        testBooking.setBookingStatus("Cancelled");
        boolean isUpdated = controller.updateFlightBooking(testBooking);
        assertEquals("Booking should be updated successfully", true, isUpdated);

        FlightBooking updatedBooking = controller.getFlightBooking(testBooking.getBookingID());
        assertNotNull("Updated booking should not be null", updatedBooking);
        assertEquals("Booking status should be updated", "Cancelled", updatedBooking.getBookingStatus());
    }

    @Test
    public void testDeleteFlightBooking() {
        boolean isDeleted = controller.deleteFlightBooking(testBooking.getBookingID());
        assertEquals("Booking should be deleted successfully", true, isDeleted);

        FlightBooking deletedBooking = controller.getFlightBooking(testBooking.getBookingID());
        assertNull("Deleted booking should be null", deletedBooking);
    }

    @After
    public void tearDown() {
        controller.deleteFlightBooking(testBooking.getBookingID());
    }
}