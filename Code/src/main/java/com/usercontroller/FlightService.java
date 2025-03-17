package com.usercontroller;

import java.util.List;

import com.userdao.FlightDao;
import com.model.FlightDetails;

public class FlightService {
	FlightDao flightDao = new FlightDao();
	public List<FlightDetails> getFlights(){
		return flightDao.getFlights();
	}
	public FlightDetails getFlight(int flightId) {
		return flightDao.getFlight(flightId);
	}
	public boolean addFlight(FlightDetails f) {
		return flightDao.addFlight(f);
	}
	public boolean updateFlight(FlightDetails f) {
		return flightDao.updateFlight(f);
	}
	public boolean cancelFlight(int flightId) {
		return flightDao.cancelFlight(flightId);
	}
}
