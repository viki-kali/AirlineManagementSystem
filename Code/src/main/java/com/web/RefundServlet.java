package com.web;

import com.controller.FlightBookingController;
import com.dao.FlightBookingDAO;
import com.model.FlightBooking;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RefundServlet")
public class RefundServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FlightBookingController flightBookingController;
    
    @Override
    public void init() {
    	flightBookingController = new FlightBookingController();
    }
    
    // Display refund input form
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("refundForm.jsp");
        dispatcher.forward(request, response);
        
    }
    
    // Process refund calculation
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        try {
            int flightId = Integer.parseInt(request.getParameter("flightId"));
            LocalDate travelDate = LocalDate.parse(request.getParameter("travelDate"));
            LocalDate today = LocalDate.now();
            long daysBeforeTravel = ChronoUnit.DAYS.between(today, travelDate);
            
            // Retrieve all bookings and filter by flight ID and travel date
            List<FlightBooking> allBookings = flightBookingController.getAllFlightBookings();
            List<FlightBooking> affectedBookings = new ArrayList<>();
            double totalRefundAmount = 0.0;
            for (FlightBooking booking : allBookings) {
                if (booking.getFlightID() == flightId && booking.getDateOfTravel().toString().equals(travelDate.toString()) && booking.getBookingStatus().equalsIgnoreCase("Booked")) {
                    LocalDate travelDates = booking.getDateOfTravel().toLocalDate();
                    long daysBeforeTraveling = ChronoUnit.DAYS.between(today, travelDate);
                    double refundPercentage = (daysBeforeTraveling >= 0 && daysBeforeTraveling <= 7) ? 110.0 : 100.0;
                    double refund = (booking.getBookingAmount() * refundPercentage) / 100.0;
                    booking.setRefundAmount(refund);
                    totalRefundAmount += booking.getBookingAmount();
                    booking.setBookingStatus("Cancelled");
//                    flightBookingController.updateFlightBooking(booking);
                    affectedBookings.add(booking);
                }
            }

            if (affectedBookings.isEmpty()) {
                request.setAttribute("errorMessage", "No bookings found for this flight on the given date.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("refundForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
            // Determine refund percentage based on days before travel.
            double refundPercentage;
            if (daysBeforeTravel >= 0 && daysBeforeTravel<=7) {
                refundPercentage = 110.0;
            } else {
            	refundPercentage = 100.0;
            }

            
            double refundAmount = (totalRefundAmount * refundPercentage) / 100.0;
            double compensationAmount = (daysBeforeTravel <= 7) ? totalRefundAmount * 0.10 : 0.0;
            double totalLoss = refundAmount + compensationAmount;
            
            // Set values as request attributes to be displayed on the result page.
            request.setAttribute("flightId", flightId);
            request.setAttribute("travelDate", travelDate);
            request.setAttribute("totalRefundAmount", totalRefundAmount);
            request.setAttribute("refundPercentage", refundPercentage);
            request.setAttribute("refundAmount", refundAmount);
            request.setAttribute("compensationAmount", compensationAmount);
            request.setAttribute("totalLoss", totalLoss);
            request.setAttribute("affectedBookings", affectedBookings);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("refundResult.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error calculating refund: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("refundForm.jsp");
            dispatcher.forward(request, response);
        }
        
    }
}
