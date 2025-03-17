package com.usercontroller;

import java.util.List;

import com.userdao.FlightScheduleDao;
import com.model.FlightSchedule;

public class FlightScheduleService {
	FlightScheduleDao fsd = new FlightScheduleDao();
	public List<FlightSchedule> getScheduledFlights() {
		return fsd.getScheduledFlights();
	}
	public FlightSchedule getScheduledFlight(int flightScheduleId) {
		return fsd.getScheduledFlight(flightScheduleId);
	}
	public boolean scheduleFlight(FlightSchedule fs) {
		return fsd.scheduleFlight(fs);
	}
	public boolean updateSchedule(FlightSchedule fs) {
		return fsd.updateSchedule(fs);
	}
}
