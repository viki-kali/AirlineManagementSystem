package com.usercontroller;

import java.sql.Date;
import java.util.List;

import com.userdao.FlightBookingDao;
import com.model.FlightBooking;
import com.model.FlightSchedule;

public class FlightBookingService {
	FlightBookingDao fbd = new FlightBookingDao();
	public List<FlightBooking> getBookings(int userId){
		return fbd.getBookings(userId);
	}
	public FlightBooking getBooking(int userId, int flightBookingId) {
		return fbd.getBooking(userId, flightBookingId);
	}
	public boolean bookTicket(FlightBooking fb) {
		return fbd.bookTicket(fb);
	}
	public double calculateRefundAmount(int userId, int flightBookingId) {
		return fbd.calculateRefundAmount(userId,flightBookingId);
	}
	public boolean cancelTicket(int bookingId,int userId,double refundedAmount) {
		return fbd.cancelTicket(bookingId, userId,refundedAmount);
	}
	public boolean cancelTicketByAdmin() {
		return fbd.cancelTicketsByAdmin();
	}
	public boolean findAllFlights() {
		return fbd.findAllFlights();
	}
	public boolean findFlights(String origin,String destination,Date dateOfTravel) {
		return fbd.findFlights(origin, destination, dateOfTravel);
	}
	public List<FlightBooking> getCategoryBookings(int userId,String bookingStatus){
		return fbd.getCategoryBookings(userId,bookingStatus);
	}
	public List<FlightSchedule> searchFlights(String origin, String destination, Date dateOfTravel){
		return fbd.searchFlights(origin, destination, dateOfTravel);
	}
	public boolean checkTickets(FlightBooking fb) {
		return fbd.checkTickets(fb);
	}
	public double[] getBookingAmount(FlightBooking fb) {
		return fbd.getBookingAmount(fb);
	}
}
