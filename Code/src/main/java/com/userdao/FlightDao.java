package com.userdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.usercontroller.FlightBookingService;
import com.model.FlightDetails;
import com.helper.DBHelper;

public class FlightDao {
    
    public List<FlightDetails> getFlights() {
        List<FlightDetails> flights = new ArrayList<>();
        String query = "SELECT * FROM Flight";
        try (Connection conn = DBHelper.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                FlightDetails flight = new FlightDetails(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getInt(6),
                    rs.getInt(7),
                    rs.getInt(8)
                );
                flights.add(flight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flights;
    }
    
    public FlightDetails getFlight(int flightId) {
        FlightDetails flight = null;
        String query = "SELECT * FROM Flight WHERE flightId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, flightId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flight = new FlightDetails(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flight;
    }
    
    public boolean addFlight(FlightDetails f) {
        boolean result = false;
        String query = "INSERT INTO Flight(CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, f.getCarrierId());
            ps.setString(2, f.getOrigin());
            ps.setString(3, f.getDestination());
            ps.setDouble(4, f.getAirfare());
            ps.setInt(5, f.getSeatCapacityBusinessClass());
            ps.setInt(6, f.getSeatCapacityEconomyClass());
            ps.setInt(7, f.getSeatCapacityExecutiveClass());
            if (ps.executeUpdate() > 0) {
                result = true;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        System.out.println("Flight ID generated is : " + rs.getInt(1));
                }
            } else {
                System.out.println("Issue in saving Flight information, Please check the data and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateFlight(FlightDetails f) {
        boolean res = false;
        String query = "UPDATE Flight SET carrierId_Flight=?, origin=?, destination=?, airFare=?, seatCapacityBusinessClass=?, seatCapacityEconomyClass=?, seatCapacityExecutiveClass=? WHERE flightId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, f.getCarrierId());
            ps.setString(2, f.getOrigin());
            ps.setString(3, f.getDestination());
            ps.setDouble(4, f.getAirfare());
            ps.setInt(5, f.getSeatCapacityBusinessClass());
            ps.setInt(6, f.getSeatCapacityEconomyClass());
            ps.setInt(7, f.getSeatCapacityExecutiveClass());
            ps.setInt(8, f.getFlightID());
            res = ps.executeUpdate() > 0;
            if (!res)
                System.out.println("Either data is incorrect or no Flight information is available for the given Flight ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean cancelFlight(int flightId) {
        boolean res = false;
        String query = "DELETE FROM Flight WHERE flightId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, flightId);
            res = ps.executeUpdate() > 0;
            FlightBookingService fbs = new FlightBookingService();
            if (res)
                fbs.cancelTicketByAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
