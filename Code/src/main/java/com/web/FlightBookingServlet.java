package com.web;

import com.controller.FlightBookingController;
import com.controller.FlightController;
import com.controller.CarrierController;
import com.controller.FlightScheduleController;
import com.model.FlightBooking;
import com.model.FlightDetails;
import com.model.CarrierDetails;
import com.model.User;
import com.model.FlightSchedule;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FlightBookingServlet")
public class FlightBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private FlightBookingController flightBookingController;
    private FlightController flightController;
    private CarrierController carrierController;
    private FlightScheduleController flightScheduleController;
    
    @Override
    public void init() throws ServletException {
        flightBookingController = new FlightBookingController();
        flightController = new FlightController();
        carrierController = new CarrierController();
        flightScheduleController = new FlightScheduleController();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
 
        String action = request.getParameter("action");
        if ("book".equalsIgnoreCase(action)) {
            try {
                // Retrieve booking form parameters.
                int flightId = Integer.parseInt(request.getParameter("flightId"));
                String seatCategory = request.getParameter("seatCategory");
                int seats = Integer.parseInt(request.getParameter("numberOfSeats"));
                // Expect travel date to be passed as a hidden field in the booking form.
                String travelDateStr = request.getParameter("dateOfTravel"); // Get date as String
                Date travelDate = Date.valueOf(travelDateStr); // Convert to java.sql.Date

                
                // Retrieve logged-in customer from session.
                if (session == null || session.getAttribute("user") == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                User customer = (User) session.getAttribute("user");
                
                // Retrieve flight details using FlightController.
                FlightDetails chosenFlight = flightController.getFlight(flightId);
                if (chosenFlight == null) {
                    request.setAttribute("error", "Flight not found.");
                    RequestDispatcher rd = request.getRequestDispatcher("bookFlight.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Calculate base fare and total ticket price.
                int baseFare = (int) chosenFlight.getAirfare(); // Casting for simplicity.
                int totalAmount = 0;
                switch (seatCategory.toLowerCase()) {
                    case "economy":
                        totalAmount = baseFare * seats;
                        break;
                    case "business":
                        totalAmount = (baseFare * 2) * seats;
                        break;
                    case "executive":
                        totalAmount = (baseFare * 5) * seats;
                        break;
                    default:
                        request.setAttribute("error", "Invalid seat category.");
                        RequestDispatcher rd = request.getRequestDispatcher("bookFlight.jsp");
                        rd.forward(request, response);
                        return;
                }
                
                // Retrieve carrier details using CarrierController.
                CarrierDetails carrier = carrierController.getCarrier(chosenFlight.getCarrierId());
                if (carrier == null) {
                    request.setAttribute("error", "Carrier details not found.");
                    RequestDispatcher rd = request.getRequestDispatcher("bookFlight.jsp");
                    rd.forward(request, response);
                    return;
                }
                
                // Calculate discount percentage based on days until travel.
                LocalDate today = LocalDate.now();
                LocalDate travelLocalDate = travelDate.toLocalDate();
                long daysBeforeTravel = ChronoUnit.DAYS.between(today, travelLocalDate);
                double discountPercentage = 0.0;
                if (daysBeforeTravel >= 90) {
                    discountPercentage += carrier.getDiscountPercentageNinetyDaysAdvanceBooking();
                } else if (daysBeforeTravel >= 60) {
                    discountPercentage += carrier.getDiscountPercentageSixtyDaysAdvanceBooking();
                } else if (daysBeforeTravel >= 30) {
                    discountPercentage += carrier.getDiscountPercentageThirtyDaysAdvanceBooking();
                }
                // Apply bulk booking discount if applicable.
                if (seats >= 10) {
                    discountPercentage += carrier.getBulkBookingDiscount();
                }
                // Additional discount based on customer category.
                String customerCategory = customer.getCustomerType();
                if (customerCategory == null) {
                    customerCategory = "Silver"; // Default category
                }
                customerCategory = customerCategory.toLowerCase();
                // Assuming this holds category info.
                switch (customerCategory.toLowerCase()) {
                    case "silver":
                        discountPercentage += carrier.getSilverUserDiscount();
                        break;
                    case "gold":
                        discountPercentage += carrier.getGoldUserDiscount();
                        break;
                    case "platinum":
                        discountPercentage += carrier.getPlatinumUserDiscount();
                        break;
                }
                
                double discountAmount = (totalAmount * discountPercentage) / 100.0;
                double finalAmount = totalAmount - discountAmount;
                
                // Set booking details.
                Date dateOfBooking = Date.valueOf(today);
                String bookingStatus = "Booked";
                
                // Create FlightBooking object.
                FlightBooking booking = new FlightBooking(0, flightId, customer.getUserId(), seats, seatCategory,
                        dateOfBooking, travelDate, bookingStatus, finalAmount, 0.0);
                
                // Create booking.
                boolean bookingSuccess = flightBookingController.createBooking(booking);
                if (bookingSuccess) {
                    // Update FlightSchedule booked seats.
                    // Get the FlightSchedule record for this flight and travel date.
                    FlightSchedule schedule = flightScheduleController.getFlightScheduleByFlightIdAndDate(flightId, travelDate);
                    if (schedule != null) {
                        if (seatCategory.equalsIgnoreCase("economy")) {
                            int newEconomyBooked = schedule.getEconomyClassBookedCount() + seats;
                            schedule.setEconomyClassBookedCount(newEconomyBooked);
                        } else if (seatCategory.equalsIgnoreCase("executive")) {
                            int newExecutiveBooked = schedule.getExecutiveClassBookedCount() + seats;
                            schedule.setExecutiveClassBookedCount(newExecutiveBooked);
                        } else if (seatCategory.equalsIgnoreCase("business")) {
                            int newBusinessBooked = schedule.getBusinessClassBookedCount() + seats;
                            schedule.setBusinessClassBookedCount(newBusinessBooked);
                        }
                        // Update the FlightSchedule in the database.
                        flightScheduleController.updateFlightSchedule(schedule);
                    }
                    
                    // Set calculated attributes for display.
                    request.setAttribute("booking", booking);
                    request.setAttribute("totalAmount", (double) totalAmount);
                    request.setAttribute("discountPercentage", discountPercentage);
                    request.setAttribute("discountAmount", discountAmount);
                    request.setAttribute("finalAmount", finalAmount);
                    
                    RequestDispatcher rd = request.getRequestDispatcher("bookingConfirmation.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("error", "Booking failed. Please try again.");
                    RequestDispatcher rd = request.getRequestDispatcher("bookFlight.jsp");
                    rd.forward(request, response);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "An error occurred: " + e.getMessage());
                RequestDispatcher rd = request.getRequestDispatcher("bookFlight.jsp");
                rd.forward(request, response);
            }
        } else {
            response.sendRedirect("bookFlight.jsp");
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        doPost(request, response);
    }
}
