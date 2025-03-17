package com.web;

import com.controller.CarrierController;
import com.controller.FlightBookingController;
import com.controller.FlightController;
import com.model.CarrierDetails;
import com.model.FlightBooking;
import com.model.FlightDetails;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CarrierServlet")
public class CarrierServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CarrierController carrierController;
    private FlightController flightController;
    private FlightBookingController flightBookingController;

    @Override
    public void init() throws ServletException {
        carrierController = new CarrierController();
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
        // Determine action parameter; default to "list"
    	
        String action = request.getParameter("action"); 
        if (action == null) {
            action = "list";
        }
        
        switch(action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertCarrier(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateCarrier(request, response);
                break;
            case "delete":
                deleteCarrier(request, response);
                break;
            case "confirmDelete":
                confirmDeleteCarrier(request, response);
                break;
            case "list":
            default:
                listCarriers(request, response);
                break;
        }
    }

    // Display form to create a new carrier.
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("carrierForm.jsp");
        dispatcher.forward(request, response);
    }

    // Insert a new carrier.
    private void insertCarrier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String carrierName = request.getParameter("carrierName");
        int discount30 = Integer.parseInt(request.getParameter("discount30"));
        int discount60 = Integer.parseInt(request.getParameter("discount60"));
        int discount90 = Integer.parseInt(request.getParameter("discount90"));
        int refund2 = Integer.parseInt(request.getParameter("refund2"));
        int refund10 = Integer.parseInt(request.getParameter("refund10"));
        int refund20 = Integer.parseInt(request.getParameter("refund20"));
        int bulkDiscount = Integer.parseInt(request.getParameter("bulkDiscount"));
        int silverDiscount = Integer.parseInt(request.getParameter("silverDiscount"));
        int goldDiscount = Integer.parseInt(request.getParameter("goldDiscount"));
        int platinumDiscount = Integer.parseInt(request.getParameter("platinumDiscount"));

        CarrierDetails carrier = new CarrierDetails(0, carrierName, discount30, discount60, discount90, 
                bulkDiscount, refund2, refund10, refund20, silverDiscount, goldDiscount, platinumDiscount);
        carrierController.addCarrier(carrier);
        response.sendRedirect("CarrierServlet?action=list");
    }

    // Display form to edit an existing carrier.
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
       
        int id = Integer.parseInt(request.getParameter("id"));
        CarrierDetails carrier = carrierController.getCarrier(id);
        request.setAttribute("carrier", carrier);
        RequestDispatcher dispatcher = request.getRequestDispatcher("carrierForm.jsp");
        dispatcher.forward(request, response);
    }

    // Update an existing carrier.
    private void updateCarrier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
       
        int id = Integer.parseInt(request.getParameter("id"));
        String carrierName = request.getParameter("carrierName");
        int discount30 = Integer.parseInt(request.getParameter("discount30"));
        int discount60 = Integer.parseInt(request.getParameter("discount60"));
        int discount90 = Integer.parseInt(request.getParameter("discount90"));
        int refund2 = Integer.parseInt(request.getParameter("refund2"));
        int refund10 = Integer.parseInt(request.getParameter("refund10"));
        int refund20 = Integer.parseInt(request.getParameter("refund20"));
        int bulkDiscount = Integer.parseInt(request.getParameter("bulkDiscount"));
        int silverDiscount = Integer.parseInt(request.getParameter("silverDiscount"));
        int goldDiscount = Integer.parseInt(request.getParameter("goldDiscount"));
        int platinumDiscount = Integer.parseInt(request.getParameter("platinumDiscount"));

        CarrierDetails carrier = new CarrierDetails(id, carrierName, discount30, discount60, discount90, 
                bulkDiscount, refund2, refund10, refund20, silverDiscount, goldDiscount, platinumDiscount);
        carrierController.updateCarrier(carrier);
        response.sendRedirect("CarrierServlet?action=list");
    }

    // Delete carrier: if there are flights (and associated bookings), confirm deletion.
    private void deleteCarrier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int carrierId = Integer.parseInt(request.getParameter("id"));
        
        // Retrieve flights associated with this carrier using FlightController.
        List<FlightDetails> flights = flightController.getFlightsByCarrierId(carrierId);
        List<FlightBooking> allAffectedBookings = new ArrayList<>();
        double totalRefund = 0.0;
        LocalDate today = LocalDate.now();
        
        if (flights != null && !flights.isEmpty()) {
            for (FlightDetails flight : flights) {
                // For each flight, retrieve its bookings using FlightBookingController.
                List<FlightBooking> bookings = flightBookingController.getBookingsByFlightId(flight.getFlightID());
                if (bookings != null && !bookings.isEmpty()) {
                    for (FlightBooking booking : bookings) {
                    	if(booking.getBookingStatus().equals("Booked"))
                    	{
                    		LocalDate travelDate = booking.getDateOfTravel().toLocalDate();
                    		long daysBeforeTravel = ChronoUnit.DAYS.between(today, travelDate);
                    		double refundPercentage = (daysBeforeTravel <= 7 && daysBeforeTravel >= 0) ? 110.0 : 100.0;
                    		double refund = (booking.getBookingAmount() * refundPercentage) / 100.0;
                    		booking.setRefundAmount(refund);
                    		totalRefund += refund;
                    		allAffectedBookings.add(booking);
                    	}
                    }
                }
            }
        }
        request.setAttribute("carrierId", carrierId);
        HttpSession session = request.getSession();
        request.setAttribute("affectedBookings", allAffectedBookings);
        session.setAttribute("affectedBookingscarrier", allAffectedBookings);
        request.setAttribute("totalRefund", totalRefund);
        RequestDispatcher dispatcher = request.getRequestDispatcher("carrierDeleteConfirmation.jsp");
        dispatcher.forward(request, response);
    }
    
    // Confirm deletion: update all affected bookings (mark them as Cancelled) and then delete the carrier.
    private void confirmDeleteCarrier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
        int carrierId = Integer.parseInt(request.getParameter("carrierId"));
        List<FlightBooking> bookings = (List<FlightBooking>) session.getAttribute("affectedBookingscarrier");
        for (FlightBooking booking : bookings) {
        	booking.setBookingStatus("Cancelled");
            flightBookingController.updateFlightBooking(booking);
        }

        // Finally, delete the carrier using CarrierController.
        carrierController.deleteCarrier(carrierId);
        response.sendRedirect("CarrierServlet?action=list");
    }
    
    // List all carriers and forward to the carrier list JSP.
    private void listCarriers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        List<CarrierDetails> listCarriers = carrierController.getAllCarriers();
        request.setAttribute("listCarriers", listCarriers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("carrierList.jsp");
        dispatcher.forward(request, response);
    }
}
