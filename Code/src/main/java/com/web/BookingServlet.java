package com.web;

import com.controller.FlightBookingController;
import com.model.FlightBooking;
import com.usercontroller.FlightBookingService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FlightBookingController bookingController;
    
    @Override
    public void init() throws ServletException {
        bookingController = new FlightBookingController();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Determine the action; default to "list"
    	
        String action = request.getParameter("action");
        System.out.println(action);
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
        case "deleteConfirmed":del(request,response);break;
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertBooking(request, response);
                break;
            case "delete":
                deleteBooking(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateBooking(request, response);
                break;
            case "listmybooking":
                listBookingCustomer(request, response);
                break;
            default:
                listBooking(request, response);
                break;
        }
        
    }
    
    private void del(HttpServletRequest request, HttpServletResponse response) throws IOException {
		FlightBookingService fbs = new FlightBookingService();
		PrintWriter out = response.getWriter();
    	double refundedAmount = Double.parseDouble(request.getParameter("refundAmount"));
    	int customerId = Integer.parseInt(request.getParameter("userId"));
    	String bookingID = request.getParameter("bookingId");
		if(fbs.cancelTicket(Integer.parseInt(bookingID), customerId, refundedAmount)) {
		out.write("<script>");
		out.write("window.location.href='BookingServlet?action=list&status="+request.getParameter("status")+"';");
		out.write("</script>");
		}
		else {
			System.out.println("Not cancelled");
		}
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    // Forwards to a JSP form for creating a new booking.
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        request.getRequestDispatcher("flightBookingForm.jsp").forward(request, response);
        
    }
    
    // Reads form data, creates a FlightBooking, and delegates booking creation.
    private void insertBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int flightID = Integer.parseInt(request.getParameter("flightID"));
        int customerID = Integer.parseInt(request.getParameter("customerID"));
        int noOfSeats = Integer.parseInt(request.getParameter("noOfSeats"));
        String seatCategory = request.getParameter("seatCategory");
        Date dateOfBooking = Date.valueOf(request.getParameter("dateOfBooking")); // Format: yyyy-mm-dd
        Date dateOfTravel = Date.valueOf(request.getParameter("dateOfTravel"));
        String bookingStatus = request.getParameter("bookingStatus");
        double bookingAmount = Double.parseDouble(request.getParameter("bookingAmount"));
        double refundAmount = Double.parseDouble(request.getParameter("refundAmount"));
        
        FlightBooking newBooking = new FlightBooking(0, flightID, customerID, noOfSeats,
                seatCategory, dateOfBooking, dateOfTravel, bookingStatus, bookingAmount, refundAmount);
        
        bookingController.createBooking(newBooking);
        response.sendRedirect("BookingServlet?action=list");
        
    }
    
    // Deletes a booking based on its ID.
    private void deleteBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
    	FlightBookingService fbs = new FlightBookingService();
        int bookingID = Integer.parseInt(request.getParameter("id"));
        int customerId = Integer.parseInt(request.getParameter("userId"));
        
        
		double refundAmount=fbs.calculateRefundAmount(customerId,bookingID);
		request.setAttribute("refundAmount", refundAmount);
		System.out.println(refundAmount);
		String ok="Ticket Cancelled and Refund Amount Initiated Successfully.";
		String cancel="Ticket Cancellation failed.";
		out.write("<script>");
		out.write("let response = confirm('The refund amount is : "+refundAmount+". Do you wish to cancel tickets?');");
		out.write("if(response){");
		out.write("window.location.href='BookingServlet?action=deleteConfirmed&status="+ok+"&refundAmount="+refundAmount+"&bookingId="+bookingID+"&userId="+customerId+"';");
		out.write("}else{");
		out.write("window.location.href='BookingServlet?action=list&status="+cancel+"';");
		out.write("}");
		out.write("</script>");
//		
//		else if("deleteConfirmed".equals(action)) {
//			double refundedAmount = Double.parseDouble(request.getParameter("refundAmount"));
//			fbs.cancelTicket(Integer.parseInt(request.getParameter("flightBookingId")), user.getUserId(), refundedAmount);
//			out.write("<script>");
//			out.write("window.location.href='UserBookingsServlet?action=allBookings&status="+request.getParameter("status")+"&userId=10000';");
//			out.write("</script>");
//		}
//		response.sendRedirect("BookingServlet?action=list");
        
    }
    
    // Retrieves a booking by ID and forwards to a JSP for editing.
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int bookingID = Integer.parseInt(request.getParameter("id"));
        FlightBooking existingBooking = bookingController.getFlightBooking(bookingID);
        request.setAttribute("booking", existingBooking);
        request.getRequestDispatcher("flightBookingForm.jsp").forward(request, response);
        
    }
    
    // Updates an existing booking with data from the form.
    private void updateBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int bookingID = Integer.parseInt(request.getParameter("id"));
        int flightID = Integer.parseInt(request.getParameter("flightID"));
        int customerID = Integer.parseInt(request.getParameter("customerID"));
        int noOfSeats = Integer.parseInt(request.getParameter("noOfSeats"));
        String seatCategory = request.getParameter("seatCategory");
        Date dateOfBooking = Date.valueOf(request.getParameter("dateOfBooking"));
        Date dateOfTravel = Date.valueOf(request.getParameter("dateOfTravel"));
        String bookingStatus = request.getParameter("bookingStatus");
        double bookingAmount = Double.parseDouble(request.getParameter("bookingAmount"));
        double refundAmount = Double.parseDouble(request.getParameter("refundAmount"));
        
        FlightBooking booking = new FlightBooking(bookingID, flightID, customerID, noOfSeats,
                seatCategory, dateOfBooking, dateOfTravel, bookingStatus, bookingAmount, refundAmount);
        
        bookingController.updateFlightBooking(booking);
        response.sendRedirect("BookingServlet?action=list");
        
    }
    
    // Lists bookings for a specific customer. The customer ID is assumed to be provided as a parameter.
    private void listBookingCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int customerID = Integer.parseInt(request.getParameter("customerID"));
        List<FlightBooking> allBookings = bookingController.getAllFlightBookings();
        List<FlightBooking> customerBookings = new ArrayList<>();
        for (FlightBooking booking : allBookings) {
            if (booking.getCustomerID() == customerID) {
                customerBookings.add(booking);
            }
        }
        request.setAttribute("bookings", customerBookings);
        request.getRequestDispatcher("booking-list-customer.jsp").forward(request, response);
    }
    
    // Retrieves all bookings and forwards to a JSP for listing.
    private void listBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setAttribute("status", request.getParameter("status"));
        List<FlightBooking> listBooking = bookingController.getAllFlightBookings();
        request.setAttribute("listBookings", listBooking);
        request.getRequestDispatcher("flightBookingList.jsp").forward(request, response);
        
    }
}
