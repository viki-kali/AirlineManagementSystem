package com.userdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.model.FlightSchedule;
import com.helper.DBHelper;

public class FlightScheduleDao {
    
    public List<FlightSchedule> getScheduledFlights() {
        List<FlightSchedule> fs = new ArrayList<>();
        String query = "SELECT * FROM FlightSchedule";
        try (Connection conn = DBHelper.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                FlightSchedule f = new FlightSchedule(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getDate(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getInt(6)
                );
                fs.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fs;
    }
    
    public FlightSchedule getScheduledFlight(int flightScheduleId) {
        FlightSchedule f = null;
        String query = "SELECT * FROM FlightSchedule where flightScheduleId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, flightScheduleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    f = new FlightSchedule(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getDate(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    
    public boolean scheduleFlight(FlightSchedule f) {
        boolean result = false;
        String query = "INSERT INTO FlightSchedule(flightId_schedule, dateOfTravel) VALUES(?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, f.getFlightID());
            ps.setDate(2, f.getDateOfTravel());
            if (ps.executeUpdate() > 0) {
                result = true;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        System.out.println("Flight Schedule id generated : " + rs.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateSchedule(FlightSchedule f) {
        boolean res = false;
        String query = "UPDATE FlightSchedule SET flightId_schedule=?, dateOfTravel=?, businessClassBookedCount=?, economyClassBookedcount=?, executiveClassBookedcount=? WHERE flightScheduleId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, f.getFlightID());
            ps.setDate(2, f.getDateOfTravel());
            ps.setInt(3, f.getBusinessClassBookedCount());
            ps.setInt(4, f.getEconomyClassBookedCount());
            ps.setInt(5, f.getExecutiveClassBookedCount());
            ps.setInt(6, f.getFlightScheduleID());
            res = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
