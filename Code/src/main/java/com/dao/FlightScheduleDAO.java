package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.helper.DBHelper;
import com.model.FlightSchedule;

public class FlightScheduleDAO {

    // Create a new flight schedule record.
    public void createFlightSchedule(FlightSchedule flightSchedule) throws SQLException {
        String sql = "INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, flightSchedule.getFlightID());
            ps.setDate(2, flightSchedule.getDateOfTravel());
            ps.setInt(3, flightSchedule.getBusinessClassBookedCount());
            ps.setInt(4, flightSchedule.getEconomyClassBookedCount());
            ps.setInt(5, flightSchedule.getExecutiveClassBookedCount());

            ps.executeUpdate();

            // Retrieve and set the generated FlightScheduleID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    flightSchedule.setFlightScheduleID(generatedId);
                }
            }
        }
    }

    // Retrieve a flight schedule by its ID.
    public FlightSchedule getFlightScheduleById(int flightScheduleId) throws SQLException {
        String sql = "SELECT * FROM FlightSchedule WHERE FlightScheduleID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flightScheduleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FlightSchedule(
                        rs.getInt("FlightScheduleID"),
                        rs.getInt("FlightID_Schedule"),
                        rs.getDate("DateOfTravel"),
                        rs.getInt("BusinessClassBookedCount"),
                        rs.getInt("EconomyClassBookedCount"),
                        rs.getInt("ExecutiveClassBookedCount")
                    );
                }
            }
        }
        return null;
    }

    // Retrieve all flight schedules.
    public List<FlightSchedule> getAllFlightSchedules() throws SQLException {
        List<FlightSchedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM FlightSchedule";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FlightSchedule schedule = new FlightSchedule(
                    rs.getInt("FlightScheduleID"),
                    rs.getInt("FlightID_Schedule"),
                    rs.getDate("DateOfTravel"),
                    rs.getInt("BusinessClassBookedCount"),
                    rs.getInt("EconomyClassBookedCount"),
                    rs.getInt("ExecutiveClassBookedCount")
                );
                schedules.add(schedule);
            }
        }
        return schedules;
    }

    // Update an existing flight schedule.
    public boolean updateFlightSchedule(FlightSchedule flightSchedule) throws SQLException {
        String sql = "UPDATE FlightSchedule SET FlightID_Schedule = ?, DateOfTravel = ?, BusinessClassBookedCount = ?, EconomyClassBookedCount = ?, ExecutiveClassBookedCount = ? WHERE FlightScheduleID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flightSchedule.getFlightID());
            ps.setDate(2, flightSchedule.getDateOfTravel());
            ps.setInt(3, flightSchedule.getBusinessClassBookedCount());
            ps.setInt(4, flightSchedule.getEconomyClassBookedCount());
            ps.setInt(5, flightSchedule.getExecutiveClassBookedCount());
            ps.setInt(6, flightSchedule.getFlightScheduleID());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete a flight schedule by its ID.
    public boolean deleteFlightSchedule(int flightScheduleId) throws SQLException {
        String sql = "DELETE FROM FlightSchedule WHERE FlightScheduleID = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flightScheduleId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public FlightSchedule getFlightScheduleByFlightIdAndDate(int flightId, Date travelDate) throws SQLException {
        String sql = "SELECT * FROM FlightSchedule WHERE FlightID_Schedule = ? AND DateOfTravel = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, flightId);
            ps.setDate(2, travelDate);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FlightSchedule(
                        rs.getInt("FlightScheduleID"),
                        rs.getInt("FlightID_Schedule"),
                        rs.getDate("DateOfTravel"),
                        rs.getInt("BusinessClassBookedCount"),
                        rs.getInt("EconomyClassBookedCount"),
                        rs.getInt("ExecutiveClassBookedCount")
                    );
                }
            }
        }
        return null; // No matching record found
    }
}
