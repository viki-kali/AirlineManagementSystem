package com.model;

import java.sql.Date;

public class FlightSchedule {
    
    private int FlightScheduleID;
    private int FlightID;
    private Date DateOfTravel;
	private int BusinessClassBookedCount;
    private int EconomyClassBookedCount;
    private int ExecutiveClassBookedCount;

    public FlightSchedule(int flightScheduleID, int flightID, Date dateOfTravel, int businessClassBookedCount,
			int economyClassBookedCount, int executiveClassBookedCount) {
		super();
		FlightScheduleID = flightScheduleID;
		FlightID = flightID;
		DateOfTravel = dateOfTravel;
		BusinessClassBookedCount = businessClassBookedCount;
		EconomyClassBookedCount = economyClassBookedCount;
		ExecutiveClassBookedCount = executiveClassBookedCount;
	}

	public int getFlightScheduleID() {
		return FlightScheduleID;
	}

	public void setFlightScheduleID(int flightScheduleID) {
		FlightScheduleID = flightScheduleID;
	}

	public int getFlightID() {
		return FlightID;
	}

	public void setFlightID(int flightID) {
		FlightID = flightID;
	}

	public Date getDateOfTravel() {
		return DateOfTravel;
	}

	public void setDateOfTravel(Date dateOfTravel) {
		DateOfTravel = dateOfTravel;
	}

	public int getBusinessClassBookedCount() {
		return BusinessClassBookedCount;
	}

	public void setBusinessClassBookedCount(int businessClassBookedCount) {
		BusinessClassBookedCount = businessClassBookedCount;
	}

	public int getEconomyClassBookedCount() {
		return EconomyClassBookedCount;
	}

	public void setEconomyClassBookedCount(int economyClassBookedCount) {
		EconomyClassBookedCount = economyClassBookedCount;
	}

	public int getExecutiveClassBookedCount() {
		return ExecutiveClassBookedCount;
	}

	public void setExecutiveClassBookedCount(int executiveClassBookedCount) {
		ExecutiveClassBookedCount = executiveClassBookedCount;
	}

	@Override
	public String toString() {
		return "FlightSchedule [FlightScheduleID=" + FlightScheduleID + ", FlightID=" + FlightID + ", DateOfTravel="
				+ DateOfTravel + ", BusinessClassBookedCount=" + BusinessClassBookedCount + ", EconomyClassBookedCount="
				+ EconomyClassBookedCount + ", ExecutiveClassBookedCount=" + ExecutiveClassBookedCount + "]";
	}
    
	public boolean bookSeats(FlightDetails flight,String seatCategory, int seats) {
        switch (seatCategory.toLowerCase()) {
            case "economy":
                int totalEconomy = flight.getSeatCapacityEconomyClass();
                int bookedEconomy = EconomyClassBookedCount;
                int availableEconomy = totalEconomy - bookedEconomy -seats;
                if(availableEconomy>=0)
                {
                	EconomyClassBookedCount=EconomyClassBookedCount+seats;
                	return true;
                }
                break;

            case "business":
                int totalBusiness = flight.getSeatCapacityBusinessClass();
                int bookedBusiness = BusinessClassBookedCount;
                int availableBusiness = totalBusiness - bookedBusiness -seats;
                if(availableBusiness>=0)
                {
                	BusinessClassBookedCount=BusinessClassBookedCount+seats;
                	return true;
                }
                break;
                
            case "executive":
                int totalExecutive = flight.getSeatCapacityExecutiveClass();
                int bookedExecutive = ExecutiveClassBookedCount;
                int availableExecutive = totalExecutive - bookedExecutive -seats;
                if(availableExecutive>=0)
                {
                	ExecutiveClassBookedCount=ExecutiveClassBookedCount+seats;
                	return true;
                }
                break;
        }
        return false; // Not enough seats
    }
	
	public void cancelSeats(String seatCategory, int seats) {
	    if (seatCategory.equalsIgnoreCase("economy")) {
	        int newCount = this.EconomyClassBookedCount - seats;
	        this.EconomyClassBookedCount = Math.max(newCount, 0);
	    } else if (seatCategory.equalsIgnoreCase("business")) {
	        int newCount = this.BusinessClassBookedCount - seats;
	        this.BusinessClassBookedCount = Math.max(newCount, 0);
	    } else if (seatCategory.equalsIgnoreCase("executive")) {
	        int newCount = this.ExecutiveClassBookedCount - seats;
	        this.ExecutiveClassBookedCount = Math.max(newCount, 0);
	    }
	}
}
