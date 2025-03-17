package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.helper.DBHelper;
import com.model.FlightBooking;

public class FlightBookingDAO {

    // Create a new flight booking record in the database.
    public void createFlightBooking(FlightBooking booking) throws SQLException {
        String sql = "INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount, RefundAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, booking.getFlightID());
            ps.setInt(2, booking.getCustomerID());
            ps.setInt(3, booking.getNoOfSeats());
            ps.setString(4, booking.getSeatCategory());
            ps.setDate(5, booking.getDateOfBooking());
            ps.setDate(6, booking.getDateOfTravel());
            ps.setString(7, booking.getBookingStatus());
            ps.setDouble(8, booking.getBookingAmount());
            ps.setDouble(9, booking.getRefundAmount());

            ps.executeUpdate();

            // Retrieve and set the auto-generated BookingID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    booking.setBookingID(generatedId);
                }
            }
        }
    }

    // Retrieve a flight booking by its unique ID.
    public FlightBooking getFlightBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM FlightBooking WHERE BookingID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FlightBooking(
                        rs.getInt("BookingID"),
                        rs.getInt("FlightID_Booking"),
                        rs.getInt("UserID_Booking"),
                        rs.getInt("NoOfSeats"),
                        rs.getString("SeatCategory"),
                        rs.getDate("DateOfBooking"),
                        rs.getDate("DateOfTravel"),
                        rs.getString("BookingStatus"),
                        rs.getDouble("BookingAmount"),
                        rs.getDouble("RefundAmount")
                    );
                }
            }
        }
        return null;
    }

    // Retrieve all flight bookings.
    public List<FlightBooking> getAllFlightBookings() throws SQLException {
        List<FlightBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM FlightBooking";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FlightBooking booking = new FlightBooking(
                    rs.getInt("BookingID"),
                    rs.getInt("FlightID_Booking"),
                    rs.getInt("UserID_Booking"),
                    rs.getInt("NoOfSeats"),
                    rs.getString("SeatCategory"),
                    rs.getDate("DateOfBooking"),
                    rs.getDate("DateOfTravel"),
                    rs.getString("BookingStatus"),
                    rs.getDouble("BookingAmount"),
                    rs.getDouble("RefundAmount")
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }

    // Update an existing flight booking.
    public boolean updateFlightBooking(FlightBooking booking) throws SQLException {
        String sql = "UPDATE FlightBooking SET FlightID_Booking = ?, UserID_Booking = ?, NoOfSeats = ?, SeatCategory = ?, DateOfBooking = ?, DateOfTravel = ?, BookingStatus = ?, BookingAmount = ?, RefundAmount = ? WHERE BookingID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, booking.getFlightID());
            ps.setInt(2, booking.getCustomerID());
            ps.setInt(3, booking.getNoOfSeats());
            ps.setString(4, booking.getSeatCategory());
            ps.setDate(5, booking.getDateOfBooking());
            ps.setDate(6, booking.getDateOfTravel());
            ps.setString(7, booking.getBookingStatus());
            ps.setDouble(8, booking.getBookingAmount());
            ps.setDouble(9, booking.getRefundAmount());
            ps.setInt(10, booking.getBookingID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete a flight booking by its ID.
    public boolean deleteFlightBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM FlightBooking WHERE BookingID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public List<FlightBooking> getBookingsByFlightId(int flightId) throws SQLException {
        List<FlightBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM FlightBooking WHERE FlightID_Booking = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, flightId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightBooking booking = new FlightBooking(
                        rs.getInt("BookingID"),
                        rs.getInt("FlightID_Booking"),
                        rs.getInt("UserID_Booking"),
                        rs.getInt("NoOfSeats"),
                        rs.getString("SeatCategory"),
                        rs.getDate("DateOfBooking"),
                        rs.getDate("DateOfTravel"),
                        rs.getString("BookingStatus"),
                        rs.getDouble("BookingAmount"),
                        rs.getDouble("RefundAmount")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
    
    // Retrieve bookings based on flight ID and date of travel.
    public List<FlightBooking> getBookingsByFlightIdAndDate(int flightId, Date dateOfTravel) throws SQLException {
        List<FlightBooking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM FlightBooking WHERE FlightID_Booking = ? AND DateOfTravel = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flightId);
            ps.setDate(2, dateOfTravel);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightBooking booking = new FlightBooking(
                        rs.getInt("BookingID"),
                        rs.getInt("FlightID_Booking"),
                        rs.getInt("UserID_Booking"),
                        rs.getInt("NoOfSeats"),
                        rs.getString("SeatCategory"),
                        rs.getDate("DateOfBooking"),
                        rs.getDate("DateOfTravel"),
                        rs.getString("BookingStatus"),
                        rs.getDouble("BookingAmount"),
                        rs.getDouble("RefundAmount")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
    
    public boolean createBooking(FlightBooking booking) throws SQLException {
        String sql = "INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount, RefundAmount) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, booking.getFlightID());
            ps.setInt(2, booking.getCustomerID());
            ps.setInt(3, booking.getNoOfSeats());
            ps.setString(4, booking.getSeatCategory());
            ps.setDate(5, booking.getDateOfBooking());
            ps.setDate(6, booking.getDateOfTravel());
            ps.setString(7, booking.getBookingStatus());
            ps.setDouble(8, booking.getBookingAmount());
            ps.setDouble(9, booking.getRefundAmount());
            
            int rowsAffected = ps.executeUpdate();
            
            // Retrieve the auto-generated booking ID and set it in the booking object.
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    booking.setBookingID(generatedId);
                }
            }
            return rowsAffected > 0;
        }
    }
    
    public FlightBooking getBooking(int userId, int flightBookingId) throws SQLException {
        String sql = "SELECT * FROM FlightBooking WHERE UserID_Booking = ? AND BookingID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, flightBookingId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FlightBooking(
                        rs.getInt("BookingID"),
                        rs.getInt("FlightID_Booking"),
                        rs.getInt("UserID_Booking"),
                        rs.getInt("NoOfSeats"),
                        rs.getString("SeatCategory"),
                        rs.getDate("DateOfBooking"),
                        rs.getDate("DateOfTravel"),
                        rs.getString("BookingStatus"),
                        rs.getDouble("BookingAmount"),
                        rs.getDouble("RefundAmount")
                    );
                }
            }
        }
        return null;
    }
}
