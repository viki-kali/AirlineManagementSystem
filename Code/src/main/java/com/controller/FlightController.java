package com.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dao.FlightDAO;
import com.model.FlightDetails;
import com.model.FlightSearchResult;

public class FlightController {

    private FlightDAO flightDAO;

    public FlightController() {
        this.flightDAO = new FlightDAO();
    }

    // Add a new flight.
    public void addFlight(FlightDetails flight) {
        try {
            flightDAO.createFlight(flight);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a flight by its ID.
    public FlightDetails getFlight(int flightId) {
        try {
            return flightDAO.getFlightById(flightId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all flights.
    public List<FlightDetails> getAllFlights() {
        try {
            return flightDAO.getAllFlights();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update flight details.
    public boolean updateFlight(FlightDetails flight) {
        try {
            return flightDAO.updateFlight(flight);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a flight.
    public boolean deleteFlight(int flightId) {
        try {
            return flightDAO.deleteFlight(flightId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Delegate call to the DAO to get flights by carrier ID.
    public List<FlightDetails> getFlightsByCarrierId(int carrierId) {
        try {
            return flightDAO.getFlightsByCarrierId(carrierId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Return an empty list or null based on your application's error handling strategy
            return new ArrayList<>();
        }
    }
    
    public List<FlightSearchResult> searchFlights(String origin, String destination, Date travelDate) {
        try {
            return flightDAO.searchFlights(origin, destination, travelDate);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
