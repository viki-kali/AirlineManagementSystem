package com.web;

import com.controller.FlightScheduleController;
import com.controller.FlightBookingController;
import com.model.FlightBooking;
import com.model.FlightSchedule;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/FlightScheduleServlet")
public class FlightScheduleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FlightScheduleController flightScheduleController;
    private FlightBookingController flightBookingController;
    
    @Override
    public void init() throws ServletException {
        flightScheduleController = new FlightScheduleController();
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
        // Determine the action; default to "list"
    	
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertSchedule(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateSchedule(request, response);
                break;
            case "delete":
                deleteFlightSchedule(request, response);
                break;
            case "confirmDelete":
                confirmDeleteFlightSchedule(request, response);
                break;
            default:
                listSchedule(request, response);
                break;
        }
    }
    
    // Display form for adding a new flight schedule.
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("flightScheduleForm.jsp");
        dispatcher.forward(request, response);
    }
    
    // Insert a new flight schedule record.
    private void insertSchedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int flightId = Integer.parseInt(request.getParameter("flightId"));
        // Expecting date in yyyy-MM-dd format.
        Date dateOfTravel = Date.valueOf(request.getParameter("dateOfTravel"));
        int businessBooked = Integer.parseInt(request.getParameter("businessBooked"));
        int economyBooked = Integer.parseInt(request.getParameter("economyBooked"));
        int executiveBooked = Integer.parseInt(request.getParameter("executiveBooked"));
        
        FlightSchedule schedule = new FlightSchedule(0, flightId, dateOfTravel,
                businessBooked, economyBooked, executiveBooked);
        flightScheduleController.addFlightSchedule(schedule);
        response.sendRedirect("FlightScheduleServlet?action=list");
    }
    
    // Display form for editing an existing flight schedule.
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        FlightSchedule schedule = flightScheduleController.getFlightSchedule(id);
        request.setAttribute("schedule", schedule);
        RequestDispatcher dispatcher = request.getRequestDispatcher("flightScheduleForm.jsp");
        dispatcher.forward(request, response);
    }
    
    // Update an existing flight schedule record.
    private void updateSchedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int id = Integer.parseInt(request.getParameter("id"));
        int flightId = Integer.parseInt(request.getParameter("flightId"));
        Date dateOfTravel = Date.valueOf(request.getParameter("dateOfTravel"));
        int businessBooked = Integer.parseInt(request.getParameter("businessBooked"));
        int economyBooked = Integer.parseInt(request.getParameter("economyBooked"));
        int executiveBooked = Integer.parseInt(request.getParameter("executiveBooked"));
        
        FlightSchedule schedule = new FlightSchedule(id, flightId, dateOfTravel,
                businessBooked, economyBooked, executiveBooked);
        flightScheduleController.updateFlightSchedule(schedule);
        response.sendRedirect("FlightScheduleServlet?action=list");
    }
    
    // List all flight schedules.
    private void listSchedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        List<FlightSchedule> listSchedules = flightScheduleController.getAllFlightSchedules();
        request.setAttribute("listSchedules", listSchedules);
        RequestDispatcher dispatcher = request.getRequestDispatcher("flightScheduleList.jsp");
        dispatcher.forward(request, response);
    }
    
    // Delete flight schedule:
    // Check if there are any bookings for this flight (by flight ID and date of travel).
    // If bookings exist, forward to confirmation JSP; otherwise, delete directly.
    private void deleteFlightSchedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int scheduleId = Integer.parseInt(request.getParameter("id"));
        FlightSchedule schedule = flightScheduleController.getFlightSchedule(scheduleId);
        if (schedule == null) {
            response.sendRedirect("FlightScheduleServlet?action=list");
            return;
        }
        int flightId = schedule.getFlightID();
        Date dateOfTravel = schedule.getDateOfTravel();
        
        // Retrieve bookings for the given flight ID and date of travel.
        List<FlightBooking> bookings = flightBookingController.getBookingsByFlightIdAndDate(flightId, dateOfTravel);
        if (bookings != null && !bookings.isEmpty()) {
            // Calculate total refund using the updated logic:
            // If the number of days between today and travel date is between 0 and 7 (inclusive), apply 110% refund.
            // Otherwise, apply 100% refund.
            double totalRefund = 0.0;
            LocalDate today = LocalDate.now();
            for (FlightBooking booking : bookings) {
            	if(booking.getBookingStatus().equalsIgnoreCase("Booked"))
            	{
                LocalDate travelDate = booking.getDateOfTravel().toLocalDate();
                long daysBeforeTravel = ChronoUnit.DAYS.between(today, travelDate);
                double refundPercentage = (daysBeforeTravel >= 0 && daysBeforeTravel <= 7) ? 110.0 : 100.0;
                double refund = (booking.getBookingAmount() * refundPercentage) / 100.0;
                booking.setRefundAmount(refund);
                totalRefund += refund;
            	}
            }
            // Set attributes and forward to confirmation page.
            request.setAttribute("scheduleId", scheduleId);
            request.setAttribute("bookings", bookings);
            HttpSession session = request.getSession();
            session.setAttribute("affectedBookingsFlightSchedule", bookings);
            request.setAttribute("totalRefund", totalRefund);
            RequestDispatcher dispatcher = request.getRequestDispatcher("flightScheduleDeleteConfirmation.jsp");
            dispatcher.forward(request, response);
        } else {
            // No associated bookings: delete schedule directly.
            flightScheduleController.deleteFlightSchedule(scheduleId);
            response.sendRedirect("FlightScheduleServlet?action=list");
        }
    }

    
    // Confirm deletion:
    private void confirmDeleteFlightSchedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
    	HttpSession session = request.getSession();
        List<FlightBooking> bookings = (List<FlightBooking>) session.getAttribute("affectedBookingsFlightSchedule");
        for (FlightBooking booking : bookings) {
        	booking.setBookingStatus("Cancelled");
            flightBookingController.updateFlightBooking(booking);
        }

        flightScheduleController.deleteFlightSchedule(scheduleId);
        response.sendRedirect("FlightScheduleServlet?action=list");
    }
}
