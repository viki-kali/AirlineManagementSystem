package com.usertest;

import com.usercontroller.*;
import com.userdao.*;
import com.model.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class FlightBookingServiceTest {
    private FlightBookingDao flightBookingDao;
    private FlightBooking flightBooking;
    private FlightBookingService fbs;

    @Before
    public void setUp() {
    	fbs = new FlightBookingService();
        flightBookingDao = new FlightBookingDao(); // Ensure this points to a test database
        flightBooking = new FlightBooking(0,4001,10004,5,"Economy",Date.valueOf(LocalDate.now()),Date.valueOf("2025-05-01"),"",0,0);

    }

  @Test
  public void searchTest() {
	  List<FlightSchedule> lis = fbs.searchFlights("Detroit","Charlotte" , Date.valueOf("2025-05-15"));
//	  System.out.println(lis);
	  assertNotNull(lis);

  }
  @Test
  public void checkTicketTest() {
	  boolean checkTickets = fbs.checkTickets(flightBooking);
	  assertTrue(checkTickets);
  }
  @Test
  public void getBookedFlightsTest() {
	  List<FlightBooking> lis = fbs.getBookings(10003);
	  if(lis.size()==0)
		  lis=null;
//	  System.out.println(lis);
	  assertNotNull(lis);
  }
//  @Test
//  public void bookFLightTicket() {
//	  double[] calculateTicketAmount = fbs.getBookingAmount(flightBooking);
//	  flightBooking.setBookingAmount(calculateTicketAmount[2]);
//	  boolean book = fbs.bookTicket(flightBooking);
//	  assertTrue(book);
//  }
//  @Test
//  public void cancelTicketTest() {
//	  flightBooking.setBookingID(6102);
//	  double refundAmount = fbs.calculateRefundAmount(flightBooking.getCustomerID(), flightBooking.getBookingID());
//	  System.out.println(refundAmount);
//	  boolean cancel = fbs.cancelTicket(flightBooking.getBookingID(), flightBooking.getCustomerID(), refundAmount);
//	  assertTrue(cancel);
//  }
  @Test
  public void getCategoryBookingTest() {
	  String category = "Cancelled";
	  List<FlightBooking> lis = fbs.getCategoryBookings(flightBooking.getCustomerID(), category);
//	  System.out.println(lis);
	  if(lis.size()==0)
		  lis = null;
	  assertNotNull(lis);
  }
  
  

    @After
    public void tearDown() {
        // Clean up test data if necessary
    }
}
