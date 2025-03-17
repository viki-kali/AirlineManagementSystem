package com.model;

import java.sql.Date;

public class FlightSearchResult {
    private int flightId;
    private int carrierId;
    private int flightScheduleId;
    private String origin;
    private String destination;
    private Date dateOfTravel;
    private double airfare;
    private int availableEconomySeats;
    private int availableExecutiveSeats;
    private int availableBusinessSeats;
    
    public FlightSearchResult() {}

    // Getters and setters
    public int getFlightId() {
        return flightId;
    }
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
    public int getCarrierId() {
        return carrierId;
    }
    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }
    public int getFlightScheduleId() {
        return flightScheduleId;
    }
    public void setFlightScheduleId(int flightScheduleId) {
        this.flightScheduleId = flightScheduleId;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public Date getDateOfTravel() {
        return dateOfTravel;
    }
    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }
    public double getAirfare() {
        return airfare;
    }
    public void setAirfare(double airfare) {
        this.airfare = airfare;
    }
    public int getAvailableEconomySeats() {
        return availableEconomySeats;
    }
    public void setAvailableEconomySeats(int availableEconomySeats) {
        this.availableEconomySeats = availableEconomySeats;
    }
    public int getAvailableExecutiveSeats() {
        return availableExecutiveSeats;
    }
    public void setAvailableExecutiveSeats(int availableExecutiveSeats) {
        this.availableExecutiveSeats = availableExecutiveSeats;
    }
    public int getAvailableBusinessSeats() {
        return availableBusinessSeats;
    }
    public void setAvailableBusinessSeats(int availableBusinessSeats) {
        this.availableBusinessSeats = availableBusinessSeats;
    }
}
