package com.web;

import com.controller.FlightController;
import com.controller.FlightBookingController;
import com.model.FlightBooking;
import com.model.FlightDetails;
import com.model.FlightSearchResult;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FlightServlet")
public class FlightServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FlightController flightController;
    private FlightBookingController flightBookingController;
    
    @Override
    public void init() throws ServletException {
        flightController = new FlightController();
        flightBookingController = new FlightBookingController();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Delegate POST requests to doGet for simplicity.
        doGet(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    System.out.println("1111111111");
        // Determine the action parameter; default to "list"
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertFlight(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateFlight(request, response);
                break;
            case "delete":
                deleteFlight(request, response);
                break;
            case "confirmDelete":
                confirmDeleteFlight(request, response);
                break;
            case "search":
                searchFlights(request, response);
                break;
            default:
                listFlights(request, response);
                break;
        }
    }
    
    // Forwards to flightForm.jsp for adding a new flight.
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("flightForm.jsp");
        dispatcher.forward(request, response);
    }
    
    // Reads form data, creates a FlightDetails object, and adds it via FlightController.
    private void insertFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int carrierId = Integer.parseInt(request.getParameter("carrierId"));
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        double airfare = Double.parseDouble(request.getParameter("airfare"));
        int economySeats = Integer.parseInt(request.getParameter("economySeats"));
        int businessSeats = Integer.parseInt(request.getParameter("businessSeats"));
        int executiveSeats = Integer.parseInt(request.getParameter("executiveSeats"));
        
        FlightDetails flight = new FlightDetails(0, carrierId, origin, destination, airfare,
                economySeats, businessSeats, executiveSeats);
        flightController.addFlight(flight);
        response.sendRedirect("FlightServlet?action=list");
    }
    
    // Forwards to flightForm.jsp with existing flight data for editing.
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        FlightDetails flight = flightController.getFlight(id);
        request.setAttribute("flight", flight);
        RequestDispatcher dispatcher = request.getRequestDispatcher("flightForm.jsp");
        dispatcher.forward(request, response);
    }
    
    // Reads updated form data, creates a FlightDetails object, and updates via FlightController.
    private void updateFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        int carrierId = Integer.parseInt(request.getParameter("carrierId"));
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        double airfare = Double.parseDouble(request.getParameter("airfare"));
        int economySeats = Integer.parseInt(request.getParameter("economySeats"));
        int businessSeats = Integer.parseInt(request.getParameter("businessSeats"));
        int executiveSeats = Integer.parseInt(request.getParameter("executiveSeats"));
        
        FlightDetails flight = new FlightDetails(id, carrierId, origin, destination, airfare,
                economySeats, businessSeats, executiveSeats);
        flightController.updateFlight(flight);
        response.sendRedirect("FlightServlet?action=list");
    }
    
    // Retrieves a list of flights and forwards to flightList.jsp.
    private void listFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        List<FlightDetails> listFlights = flightController.getAllFlights();
        request.setAttribute("listFlights", listFlights);
        RequestDispatcher dispatcher = request.getRequestDispatcher("flightList.jsp");
        dispatcher.forward(request, response);
    }
    
    // Checks if the flight has associated bookings.
    // If yes, calculates total refund and forwards to flightDeleteConfirmation.jsp.
    // Otherwise, deletes the flight directly.
    private void deleteFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int flightId = Integer.parseInt(request.getParameter("id"));
        List<FlightBooking> bookings = flightBookingController.getBookingsByFlightId(flightId);
        double totalRefund = 0.0;
        LocalDate today = LocalDate.now();
        
        if (bookings != null && !bookings.isEmpty()) {
            for (FlightBooking booking : bookings) {
            	if(booking.getBookingStatus().equalsIgnoreCase("Booked")) {
            		LocalDate travelDate = booking.getDateOfTravel().toLocalDate();
            		long daysBeforeTravel = ChronoUnit.DAYS.between(today, travelDate);
            		double refundPercentage = (daysBeforeTravel <= 7 && daysBeforeTravel >= 0) ? 110.0 : 100.0;
            		double refund = (booking.getBookingAmount() * refundPercentage) / 100.0;
            		booking.setRefundAmount(refund);
              		totalRefund += refund;
            	}
            }
            
            request.setAttribute("flightId", flightId);
            request.setAttribute("bookings", bookings);
            HttpSession session = request.getSession();
            session.setAttribute("affectedBookingsFlight", bookings);
            request.setAttribute("totalRefund", totalRefund);
            RequestDispatcher dispatcher = request.getRequestDispatcher("flightDeleteConfirmation.jsp");
            dispatcher.forward(request, response);
        } else {
            flightController.deleteFlight(flightId);
            response.sendRedirect("FlightServlet?action=list");
        }
    }
    
    // Updates all associated bookings to "Cancelled" and then deletes the flight.
    private void confirmDeleteFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int flightId = Integer.parseInt(request.getParameter("flightId"));
    	HttpSession session = request.getSession();
        List<FlightBooking> bookings = (List<FlightBooking>) session.getAttribute("affectedBookingsFlight");
        for (FlightBooking booking : bookings) {
        	booking.setBookingStatus("Cancelled");
            flightBookingController.updateFlightBooking(booking);
        }

        flightController.deleteFlight(flightId);
        response.sendRedirect("FlightServlet?action=list");
    }

    // New method to handle search action.
    private void searchFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String travelDateStr = request.getParameter("travelDate");

        if (travelDateStr == null || travelDateStr.isEmpty()) {
            request.setAttribute("error", "Travel Date is required.");
            request.getRequestDispatcher("bookFlight.jsp").forward(request, response);
            return;
        }
        Date travelDate = Date.valueOf(travelDateStr);
         
        // Call FlightController to get matching flights (ensure this method is implemented).
        List<FlightSearchResult> flightList = flightController.searchFlights(origin, destination, travelDate);
        
        // Set the flight list as a request attribute and forward to bookFlight.jsp.
        request.setAttribute("flightList", flightList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bookFlight.jsp");
        dispatcher.forward(request, response);
    }
}
