package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.helper.DBHelper;
import com.model.FlightDetails;
import com.model.FlightSearchResult;

public class FlightDAO {

    // Create a new flight record in the database.
    public void createFlight(FlightDetails flight) throws SQLException {
        String sql = "INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, flight.getCarrierId());
            ps.setString(2, flight.getOrigin());
            ps.setString(3, flight.getDestination());
            ps.setDouble(4, flight.getAirfare());
            ps.setInt(5, flight.getSeatCapacityEconomyClass());
            ps.setInt(6, flight.getSeatCapacityBusinessClass());
            ps.setInt(7, flight.getSeatCapacityExecutiveClass());
            
            ps.executeUpdate();
            
            // Retrieve and set the auto-generated FlightID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    flight.setFlightID(generatedId);
                }
            }
        }
    }

    // Retrieve a flight by its unique ID.
    public FlightDetails getFlightById(int flightId) throws SQLException {
        String sql = "SELECT * FROM Flight WHERE FlightID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, flightId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FlightDetails(
                        rs.getInt("FlightID"),
                        rs.getInt("CarrierID_Flight"),
                        rs.getString("Origin"),
                        rs.getString("Destination"),
                        rs.getDouble("Airfare"),
                        rs.getInt("SeatCapacityEconomyClass"),
                        rs.getInt("SeatCapacityBusinessClass"),
                        rs.getInt("SeatCapacityExecutiveClass")
                    );
                }
            }
        }
        return null;
    }

    // Retrieve all flights.
    public List<FlightDetails> getAllFlights() throws SQLException {
        List<FlightDetails> flights = new ArrayList<>();
        String sql = "SELECT * FROM Flight";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                FlightDetails flight = new FlightDetails(
                    rs.getInt("FlightID"),
                    rs.getInt("CarrierID_Flight"),
                    rs.getString("Origin"),
                    rs.getString("Destination"),
                    rs.getDouble("Airfare"),
                    rs.getInt("SeatCapacityEconomyClass"),
                    rs.getInt("SeatCapacityBusinessClass"),
                    rs.getInt("SeatCapacityExecutiveClass")
                );
                flights.add(flight);
            }
        }
        return flights;
    }

    // Update an existing flight.
    public boolean updateFlight(FlightDetails flight) throws SQLException {
        String sql = "UPDATE Flight SET CarrierID_Flight = ?, Origin = ?, Destination = ?, Airfare = ?, SeatCapacityEconomyClass = ?, SeatCapacityBusinessClass = ?, SeatCapacityExecutiveClass = ? WHERE FlightID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, flight.getCarrierId());
            ps.setString(2, flight.getOrigin());
            ps.setString(3, flight.getDestination());
            ps.setDouble(4, flight.getAirfare());
            ps.setInt(5, flight.getSeatCapacityEconomyClass());
            ps.setInt(6, flight.getSeatCapacityBusinessClass());
            ps.setInt(7, flight.getSeatCapacityExecutiveClass());
            ps.setInt(8, flight.getFlightID());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete a flight by its ID.
    public boolean deleteFlight(int flightId) throws SQLException {
        String sql = "DELETE FROM Flight WHERE FlightID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, flightId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    // Retrieve flights associated with a specific carrier ID.
    public List<FlightDetails> getFlightsByCarrierId(int carrierId) throws SQLException {
        List<FlightDetails> flights = new ArrayList<>();
        String sql = "SELECT * FROM Flight WHERE CarrierID_Flight = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, carrierId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightDetails flight = new FlightDetails(
                        rs.getInt("FlightID"),
                        rs.getInt("CarrierID_Flight"),
                        rs.getString("Origin"),
                        rs.getString("Destination"),
                        rs.getDouble("Airfare"),
                        rs.getInt("SeatCapacityEconomyClass"),
                        rs.getInt("SeatCapacityBusinessClass"),
                        rs.getInt("SeatCapacityExecutiveClass")
                    );
                    flights.add(flight);
                }
            }
        }
        return flights;
    }

    public List<FlightSearchResult> searchFlights(String origin, String destination, Date travelDate) throws SQLException {
        List<FlightSearchResult> results = new ArrayList<>();
        String sql = "SELECT f.FlightID, f.CarrierID_Flight, fs.FlightScheduleID, f.Origin, f.Destination, fs.DateOfTravel, f.Airfare, " +
                     " (f.SeatCapacityEconomyClass - fs.EconomyClassBookedCount) AS availableEconomy, " +
                     " (f.SeatCapacityExecutiveClass - fs.ExecutiveClassBookedCount) AS availableExecutive, " +
                     " (f.SeatCapacityBusinessClass - fs.BusinessClassBookedCount) AS availableBusiness " +
                     "FROM Flight f " +
                     "JOIN FlightSchedule fs ON f.FlightID = fs.FlightID_Schedule " +
                     "WHERE UPPER(f.Origin) LIKE UPPER(?) AND UPPER(f.Destination) LIKE UPPER(?) AND fs.DateOfTravel = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + origin + "%");
            ps.setString(2, "%" + destination + "%");
            ps.setDate(3, travelDate);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightSearchResult result = new FlightSearchResult();
                    result.setFlightId(rs.getInt("FlightID"));
                    result.setCarrierId(rs.getInt("CarrierID_Flight"));
                    result.setFlightScheduleId(rs.getInt("FlightScheduleID"));
                    result.setOrigin(rs.getString("Origin"));
                    result.setDestination(rs.getString("Destination"));
                    result.setDateOfTravel(rs.getDate("DateOfTravel"));
                    result.setAirfare(rs.getDouble("Airfare"));
                    result.setAvailableEconomySeats(rs.getInt("availableEconomy"));
                    result.setAvailableExecutiveSeats(rs.getInt("availableExecutive"));
                    result.setAvailableBusinessSeats(rs.getInt("availableBusiness"));
                    results.add(result);
                }
            }
        }
        return results;
    }
}
