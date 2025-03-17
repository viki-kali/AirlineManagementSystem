package com.controller;

import com.dao.FlightBookingDAO;
import com.model.FlightBooking;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightBookingController {

    private FlightBookingDAO bookingDAO;

    public FlightBookingController() {
        this.bookingDAO = new FlightBookingDAO();
    }

    // Add a new flight booking.
    public void addFlightBooking(FlightBooking booking) {
        try {
            bookingDAO.createFlightBooking(booking);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a flight booking by its ID.
    public FlightBooking getFlightBooking(int bookingId) {
        try {
            return bookingDAO.getFlightBookingById(bookingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all flight bookings.
    public List<FlightBooking> getAllFlightBookings() {
        try {
            return bookingDAO.getAllFlightBookings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing flight booking.
    public boolean updateFlightBooking(FlightBooking booking) {
        try {
            return bookingDAO.updateFlightBooking(booking);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a flight booking by its ID.
    public boolean deleteFlightBooking(int bookingId) {
        try {
            return bookingDAO.deleteFlightBooking(bookingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
  
    // Delegate call to DAO to get bookings by flight ID.
    public List<FlightBooking> getBookingsByFlightId(int flightId) {
        try {
            return bookingDAO.getBookingsByFlightId(flightId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Depending on your error handling, you may return an empty list or null.
            return new ArrayList<>();
        }
    }
 
    // Delegate the retrieval to the DAO, handling exceptions appropriately.
    public List<FlightBooking> getBookingsByFlightIdAndDate(int flightId, Date dateOfTravel) {
        try {
            return bookingDAO.getBookingsByFlightIdAndDate(flightId, dateOfTravel);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean createBooking(FlightBooking booking) {
        try {
            return bookingDAO.createBooking(booking);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Retrieve a flight booking by userId and bookingId
    public FlightBooking getBooking(int userId, int flightBookingId) {
        try {
            return bookingDAO.getBooking(userId, flightBookingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
