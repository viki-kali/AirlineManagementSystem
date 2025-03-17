package com.model;

public class FlightDetails {

    private int FlightID;
    private int CarrierId;
    private String Origin;
    private String Destination;
    private double Airfare;
    private int SeatCapacityEconomyClass;
    private int SeatCapacityBusinessClass;
    private int SeatCapacityExecutiveClass;
    
	public FlightDetails(int flightID, int carrierId, String origin, String destination, double airfare,
			int seatCapacityEconomyClass, int seatCapacityBusinessClass, int seatCapacityExecutiveClass) {
		super();
		FlightID = flightID;
		CarrierId = carrierId;
		Origin = origin;
		Destination = destination;
		Airfare = airfare;
		SeatCapacityEconomyClass = seatCapacityEconomyClass;
		SeatCapacityBusinessClass = seatCapacityBusinessClass;
		SeatCapacityExecutiveClass = seatCapacityExecutiveClass;
	}
	public int getFlightID() {
		return FlightID;
	}
	public void setFlightID(int flightID) {
		FlightID = flightID;
	}
	public int getCarrierId() {
		return CarrierId;
	}
	public void setCarrierId(int carrierId) {
		CarrierId = carrierId;
	}
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public double getAirfare() {
		return Airfare;
	}
	public void setAirfare(double airfare) {
		Airfare = airfare;
	}
	public int getSeatCapacityEconomyClass() {
		return SeatCapacityEconomyClass;
	}
	public void setSeatCapacityEconomyClass(int seatCapacityEconomyClass) {
		SeatCapacityEconomyClass = seatCapacityEconomyClass;
	}
	public int getSeatCapacityBusinessClass() {
		return SeatCapacityBusinessClass;
	}
	public void setSeatCapacityBusinessClass(int seatCapacityBusinessClass) {
		SeatCapacityBusinessClass = seatCapacityBusinessClass;
	}
	public int getSeatCapacityExecutiveClass() {
		return SeatCapacityExecutiveClass;
	}
	public void setSeatCapacityExecutiveClass(int seatCapacityExecutiveClass) {
		SeatCapacityExecutiveClass = seatCapacityExecutiveClass;
	}
	@Override
	public String toString() {
		return "FlightDetails [FlightID=" + FlightID + ", CarrierId=" + CarrierId + ", Origin=" + Origin
				+ ", Destination=" + Destination + ", Airfare=" + Airfare + ", SeatCapacityEconomyClass="
				+ SeatCapacityEconomyClass + ", SeatCapacityBusinessClass=" + SeatCapacityBusinessClass
				+ ", SeatCapacityExecutiveClass=" + SeatCapacityExecutiveClass + "]";
	}
    

}
