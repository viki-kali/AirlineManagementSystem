package com.controller;

import com.dao.FlightScheduleDAO;
import com.model.FlightSchedule;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class FlightScheduleController {

    private FlightScheduleDAO flightScheduleDAO;

    public FlightScheduleController() {
        this.flightScheduleDAO = new FlightScheduleDAO();
    }

    // Add a new flight schedule.
    public void addFlightSchedule(FlightSchedule flightSchedule) {
        try {
            flightScheduleDAO.createFlightSchedule(flightSchedule);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a flight schedule by its ID.
    public FlightSchedule getFlightSchedule(int flightScheduleId) {
        try {
            return flightScheduleDAO.getFlightScheduleById(flightScheduleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all flight schedules.
    public List<FlightSchedule> getAllFlightSchedules() {
        try {
            return flightScheduleDAO.getAllFlightSchedules();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing flight schedule.
    public boolean updateFlightSchedule(FlightSchedule flightSchedule) {
        try {
            return flightScheduleDAO.updateFlightSchedule(flightSchedule);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a flight schedule by its ID.
    public boolean deleteFlightSchedule(int flightScheduleId) {
        try {
            return flightScheduleDAO.deleteFlightSchedule(flightScheduleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public FlightSchedule getFlightScheduleByFlightIdAndDate(int flightId, Date travelDate) {
        try {
            return flightScheduleDAO.getFlightScheduleByFlightIdAndDate(flightId, travelDate);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
