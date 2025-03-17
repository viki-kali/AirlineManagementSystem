package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.usercontroller.*;
import com.model.*;
/**
 * Servlet implementation class UserBookingsServlet
 */
@WebServlet("/UserBookingsServlet")
public class UserBookingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController us = new UserController();
	private FlightBookingService fbs = new FlightBookingService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBookingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html");
		RequestDispatcher rd = null;
		HttpSession session = request.getSession();
		String action =request.getParameter("action");
//		String userid = request.getParameter("userId");
		User user = (User)session.getAttribute("user");
		System.out.println(user);
		PrintWriter out = response.getWriter();
		if("allBookings".equals(action)) {
			List<FlightBooking> bookingList = fbs.getBookings(user.getUserId());
			request.setAttribute("bookingList", bookingList);
//			System.out.println(bookingList);
			if(bookingList==null || bookingList.size()==0) {
				request.setAttribute("message", "No flight tickets are booked yet...");
			}
			rd = request.getRequestDispatcher("MyBookings.jsp");
			
		}
		else if("upcomingBookings".equals(action)) {
			List<FlightBooking> bookingList = fbs.getCategoryBookings(user.getUserId(), "Booked");
			request.setAttribute("bookingList", bookingList);
//			System.out.println(bookingList);
			if(bookingList==null || bookingList.size()==0) {
				request.setAttribute("message", "No flight tickets for upcoming journey...");
			}
			rd = request.getRequestDispatcher("MyBookings.jsp");
		}
		else if("cancelledBookings".equals(action)) {
			List<FlightBooking> bookingList = fbs.getCategoryBookings(user.getUserId(), "Cancelled");
			request.setAttribute("bookingList", bookingList);
//			System.out.println(bookingList);
			if(bookingList==null || bookingList.size()==0) {
				request.setAttribute("message", "No flight tickets are Cancelled yet...");
			}
			rd = request.getRequestDispatcher("MyBookings.jsp");
		}
		else if("travelledBookings".equals(action)) {
			List<FlightBooking> bookingList = fbs.getCategoryBookings(user.getUserId(), "Travel Completed");
			request.setAttribute("bookingList", bookingList);
//			System.out.println(bookingList);
			if(bookingList==null || bookingList.size()==0) {
				request.setAttribute("message", "No trips are completed yet...");
			}
			rd = request.getRequestDispatcher("MyBookings.jsp");
		}
		else if("deleteConfirmation".equals(action)) {
			double refundAmount=fbs.calculateRefundAmount(user.getUserId(), Integer.parseInt(request.getParameter("flightBookingId")));
			DecimalFormat df = new DecimalFormat("#.##");
			refundAmount = Double.parseDouble(df.format(refundAmount));
			request.setAttribute("refundAmount", refundAmount);
			String ok="Ticket Cancelled and Refund Amount Initiated Successfully.";
			String cancel="Ticket Cancellation failed.";
			out.write("<script>");
			out.write("let response = confirm('The refund amount is : "+refundAmount+". Do you wish to cancel tickets?');");
			out.write("if(response){");
			out.write("window.location.href='UserBookingsServlet?action=deleteConfirmed&status="+ok+"&refundAmount="+refundAmount+"&flightBookingId="+request.getParameter("flightBookingId")+"';");
			out.write("}else{");
			out.write("window.location.href='UserBookingsServlet?action=allBookings&status="+cancel+"';");
			out.write("}");
			out.write("</script>");
		}
		else if("deleteConfirmed".equals(action)) {
			double refundedAmount = Double.parseDouble(request.getParameter("refundAmount"));
			fbs.cancelTicket(Integer.parseInt(request.getParameter("flightBookingId")), user.getUserId(), refundedAmount);
			out.write("<script>");
			out.write("window.location.href='UserBookingsServlet?action=allBookings&status="+request.getParameter("status")+"&userId="+user.getUserId()+"';");
			out.write("</script>");
		}
		if(rd!=null)rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
	}

}
