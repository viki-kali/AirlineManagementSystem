package com.model;


import java.sql.Date;

public class FlightBooking {
	    private int bookingID;
	    private int flightID;
	    private int customerID;
	    private int noOfSeats;
	    private String seatCategory; // Economy / Executive / Business
	    private Date dateOfBooking;
	    private Date dateOfTravel;
	    private String bookingStatus; // Booked / Travel Completed / Cancelled
	    private double bookingAmount;
	    private double refundAmount;
		public FlightBooking(int bookingID, int flightID, int customerID, int noOfSeats, String seatCategory,
				Date dateOfBooking, Date dateOfTravel, String bookingStatus, double bookingAmount, double refundAmount) {
			super();
			this.bookingID = bookingID;
			this.flightID = flightID;
			this.customerID = customerID;
			this.noOfSeats = noOfSeats;
			this.seatCategory = seatCategory;
			this.dateOfBooking = dateOfBooking;
			this.dateOfTravel = dateOfTravel;
			this.bookingStatus = bookingStatus;
			this.bookingAmount = bookingAmount;
			this.refundAmount = refundAmount;
		}


		public int getBookingID() {
			return bookingID;
		}


		public void setBookingID(int bookingID) {
			this.bookingID = bookingID;
		}


		public int getFlightID() {
			return flightID;
		}


		public void setFlightID(int flightID) {
			this.flightID = flightID;
		}


		public int getCustomerID() {
			return customerID;
		}


		public void setCustomerID(int customerID) {
			this.customerID = customerID;
		}


		public int getNoOfSeats() {
			return noOfSeats;
		}


		public void setNoOfSeats(int noOfSeats) {
			this.noOfSeats = noOfSeats;
		}


		public String getSeatCategory() {
			return seatCategory;
		}


		public void setSeatCategory(String seatCategory) {
			this.seatCategory = seatCategory;
		}


		public Date getDateOfBooking() {
			return dateOfBooking;
		}


		public void setDateOfBooking(Date dateOfBooking) {
			this.dateOfBooking = dateOfBooking;
		}


		public Date getDateOfTravel() {
			return dateOfTravel;
		}


		public void setDateOfTravel(Date dateOfTravel) {
			this.dateOfTravel = dateOfTravel;
		}


		public String getBookingStatus() {
			return bookingStatus;
		}


		public void setBookingStatus(String bookingStatus) {
			this.bookingStatus = bookingStatus;
		}


		public double getBookingAmount() {
			return bookingAmount;
		}


		public void setBookingAmount(double bookingAmount) {
			this.bookingAmount = bookingAmount;
		}


		public double getRefundAmount() {
			return refundAmount;
		}


		public void setRefundAmount(double refundAmount) {
			this.refundAmount = refundAmount;
		}


		@Override
		public String toString() {
			return "FlightBooking [bookingID=" + bookingID + ", flightID=" + flightID + ", customerID=" + customerID
					+ ", noOfSeats=" + noOfSeats + ", seatCategory=" + seatCategory + ", dateOfBooking=" + dateOfBooking
					+ ", dateOfTravel=" + dateOfTravel + ", bookingStatus=" + bookingStatus + ", bookingAmount="
					+ bookingAmount + ", refundAmount=" + refundAmount + "]";
		}
  
}
