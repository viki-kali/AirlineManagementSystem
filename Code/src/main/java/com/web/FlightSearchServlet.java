package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import com.usercontroller.FlightBookingService;
import com.usercontroller.FlightScheduleService;
import com.model.FlightBooking;
import com.model.FlightSchedule;
import com.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class FlightSearchServlet
 */
@WebServlet("/FlightSearchServlet")
public class FlightSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FlightScheduleService fss = new FlightScheduleService();
    private FlightBookingService fbs = new FlightBookingService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlightSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if("search".equals(action)) {
			String origin = request.getParameter("origin");
			String destination = request.getParameter("destination");
			String date = request.getParameter("dateOfTravel");
			Date dateOfTravel = Date.valueOf(date);
			List<FlightSchedule> fs = fbs.searchFlights(origin,destination,dateOfTravel);
			request.setAttribute("scheduledFlights", fs);
			System.out.println(fs);
			if(fs.size()==0)
				request.setAttribute("message", "No Scheduled Flights Found");
			RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);
			
		}
		else if("preBooking".equals(action)) {
			int fsid = Integer.parseInt(request.getParameter("fsid"));
			FlightSchedule fs = fss.getScheduledFlight(fsid);
			request.setAttribute("selectedSchedule",fs);
			request.getRequestDispatcher("bookingForm.jsp").forward(request, response);
		}
		else if("checkAvailability".equals(action)) {
//			Enumeration<String> lis = request.getParameterNames();
//			while(lis.hasMoreElements()) {
//				String name = lis.nextElement();
//				System.out.println(name+" "+request.getParameter(name));
//			}
//			System.out.println("checking");
			FlightBooking booking = new FlightBooking(0,Integer.parseInt(request.getParameter("flightId")),user.getUserId(),Integer.parseInt(request.getParameter("noOfSeats")),request.getParameter("category"),Date.valueOf(LocalDate.now()),Date.valueOf(request.getParameter("date")),"",0,0);
			if(fbs.checkTickets(booking)) {
				double[] amount = fbs.getBookingAmount(booking);
				DecimalFormat df = new DecimalFormat("#.##");
				amount[2] = Double.parseDouble(df.format(amount[2]));
				booking.setBookingAmount(amount[2]);
				session.setAttribute("flightBooking", booking);
//				System.out.println("amount is "+amount[0]+" "+amount[1]+" "+amount[2]);
				out.write("<script>");
				out.write("var res = confirm('Price : "+amount[0]+"\\nDiscount : "+amount[1]+"%\\nFinal Price : "+amount[2]+"');");
				out.write("if(res){");
				out.write("window.location.href='FlightSearchServlet?action=bookingConfirmed';");
				out.write("}else{");
				out.write("window.location.href='home.jsp';");
				out.write("}");
				out.write("</script>");
			}
			else {
				out.write("<script>");
				out.write("alert('Tickets are not available for the entered seat count, Please try again later');");
				out.write("window.location.href='home.jsp';");
				out.write("</script>");
			}
		}
		else if("bookingConfirmed".equals(action)) {
			FlightBooking fb = (FlightBooking)session.getAttribute("flightBooking");
			session.setAttribute("flightBooking", null);
			String bookStatus = "";
			if(fbs.bookTicket(fb)) {
//				out.print("ticket booked successfully");
//				request.setAttribute("bookStatus", "Ticket booked successfully");
				bookStatus="Ticket booked successfully";
			}
			else {
//				out.print("ticket booking failed");
//				request.setAttribute("bookStatus", "ticket booking failed");
				bookStatus = "ticket booking failed";
			}
			out.write("<script>");
			out.write("window.location.href='UserBookingsServlet?action=allBookings&bookStatus="+bookStatus+"';");
			out.write("</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
	}

}
