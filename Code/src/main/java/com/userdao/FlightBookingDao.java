package com.userdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.usercontroller.CarrierService;
import com.usercontroller.FlightScheduleService;
import com.usercontroller.FlightService;
import com.controller.UserController;
import com.model.CarrierDetails;
import com.model.FlightBooking;
import com.model.FlightSchedule;
import com.helper.DBHelper;

public class FlightBookingDao {

    public List<FlightBooking> getBookings(int userId) {
        List<FlightBooking> bookings = new ArrayList<>();
        String query = "SELECT * FROM FlightBooking WHERE userId_booking=? ORDER BY dateOfTravel desc";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightBooking f = new FlightBooking(
                        rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getString(8),
                        rs.getDouble(9), rs.getDouble(10)
                    );
                    bookings.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public FlightBooking getBooking(int userId, int flightBookingId) {
        FlightBooking fb = null;
        String query = "SELECT * FROM FlightBooking WHERE userId_booking=? and BookingId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, flightBookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fb = new FlightBooking(
                        rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getString(8),
                        rs.getDouble(9), rs.getDouble(10)
                    );
                } else {
                    System.out.println("No booking found with the given CarrierId");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fb;
    }

    public boolean bookTicket(FlightBooking fb) {
        boolean result = false;
        if (!checkTickets(fb))
            return false;
        int id = 0;
        String insertQuery = "INSERT INTO FlightBooking(flightId_booking, userId_booking, noOfSeats, seatCategory, dateOfBooking, dateOfTravel, bookingAmount) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DBHelper.getConnection()) {
            // Insert the booking record
            try (PreparedStatement ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, fb.getFlightID());
                ps.setInt(2, fb.getCustomerID());
                System.out.println(fb.getCustomerID());
                ps.setInt(3, fb.getNoOfSeats());
                ps.setString(4, fb.getSeatCategory());
                ps.setDate(5, fb.getDateOfBooking());
                ps.setDate(6, fb.getDateOfTravel());
                ps.setDouble(7, fb.getBookingAmount());
                if (ps.executeUpdate() > 0) {
                    result = updateBooking(fb);
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next())
                            id = rs.getInt(1);
                    }
                    System.out.println("Flight booking id generated: " + id);
                } else {
                    System.out.println("Flight booking id not generated");
                }
            }
            // Update booking status to 'Booked'
            if (result) {
                String updateStatus = "UPDATE FlightBooking SET bookingStatus='Booked' WHERE BookingId=?";
                try (PreparedStatement ps = conn.prepareStatement(updateStatus)) {
                    ps.setInt(1, id);
                    result = ps.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public double calculateRefundAmount(int userId, int flightBookingId) {
        FlightBooking flightBooking = getBooking(userId, flightBookingId);
        CarrierService cs = new CarrierService();
        FlightService fs = new FlightService();
        double dis = 0;
        LocalDate travelDate = flightBooking.getDateOfTravel().toLocalDate();
        LocalDate today = LocalDate.now();
        long daysDiff = ChronoUnit.DAYS.between(today, travelDate);
        if (daysDiff <= 2) {
            dis = cs.getCarrier(fs.getFlight(flightBooking.getFlightID()).getCarrierId())
                    .getRefundPercentageForTicketCancellation2DaysBeforeTravelDate();
        } else if (daysDiff <= 10) {
            dis = cs.getCarrier(fs.getFlight(flightBooking.getFlightID()).getCarrierId())
                    .getRefundPercentageForTicketCancellation10DaysBeforeTravelDate();
        } else if (daysDiff <= 20) {
            dis = cs.getCarrier(fs.getFlight(flightBooking.getFlightID()).getCarrierId())
                    .getRefundPercentageForTicketCancellation20DaysBeforeTravelDate();
        } else {
            dis = 95;
        }
        dis = dis / 100.0;
        double refundAmount = flightBooking.getBookingAmount();
        if (dis != 0)
            refundAmount = refundAmount * dis;
        refundAmount = Math.round(refundAmount * 100) / 100.0;
        return refundAmount;
    }

    public boolean cancelTicket(int bookingId, int userId, double refundAmount) {
        boolean res = false;
        String query = "UPDATE FlightBooking SET bookingStatus='Cancelled', refundAmount=? where BookingId=? and userId_booking=? and bookingStatus='Booked'";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, refundAmount);
            ps.setInt(2, bookingId);
            ps.setInt(3, userId);
            res = ps.executeUpdate() > 0;
            // Update the seat count for the cancelled ticket
            updateSeats(getBooking(userId, bookingId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private void updateSeats(FlightBooking fb) {
  
        String query = "";
        switch (fb.getSeatCategory()) {
            case "Economy":
                query = "UPDATE FlightSchedule SET economyClassBookedCount = economyClassBookedCount - ? WHERE flightId_schedule=? AND dateOfTravel=?";
                break;
            case "Business":
                query = "UPDATE FlightSchedule SET businessClassBookedCount = businessClassBookedCount - ? WHERE flightId_schedule=? AND dateOfTravel=?";
                break;
            case "Executive":
                query = "UPDATE FlightSchedule SET executiveClassBookedCount = executiveClassBookedCount - ? WHERE flightId_schedule=? AND dateOfTravel=?";
                break;
        }
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fb.getNoOfSeats());
            ps.setInt(2, fb.getFlightID());
            ps.setDate(3, fb.getDateOfTravel());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cancelTicketsByAdmin() {
        boolean res = false;
        String selectQuery = "SELECT bookingAmount FROM FlightBooking WHERE flightId_booking IS NULL AND bookingStatus='Booked'";
        String updateQuery = "UPDATE FlightBooking SET bookingStatus='Cancelled' WHERE flightId_booking IS NULL AND bookingStatus='Booked'";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement()) {
            double amount = 0;
            try (ResultSet rs = stmt.executeQuery(selectQuery)) {
                while (rs.next())
                    amount += rs.getDouble(1);
            }
            if (amount != 0) {
                int updateCount = stmt.executeUpdate(updateQuery);
                res = updateCount > 0;
                System.out.println("Tickets cancelled Successfully\nAmount refunded to customers for cancellation is: " + amount);
            } else {
                System.out.println("No tickets are booked for this carrier/flight");
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private boolean updateBooking(FlightBooking fb) {
        boolean res = false;
        String query = "";
        switch (fb.getSeatCategory()) {
            case "Economy":
                query = "UPDATE FlightSchedule SET economyClassBookedCount = economyClassBookedCount + ? WHERE flightId_schedule=? AND dateOfTravel=?";
                break;
            case "Business":
                query = "UPDATE FlightSchedule SET businessClassBookedCount = businessClassBookedCount + ? WHERE flightId_schedule=? AND dateOfTravel=?";
                break;
            case "Executive":
                query = "UPDATE FlightSchedule SET executiveClassBookedCount = executiveClassBookedCount + ? WHERE flightId_schedule=? AND dateOfTravel=?";
                break;
        }
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fb.getNoOfSeats());
            ps.setInt(2, fb.getFlightID());
            ps.setDate(3, fb.getDateOfTravel());
            res = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean checkTickets(FlightBooking fb) {
        String query = "SELECT seatCapacityBusinessClass, businessClassBookedCount, seatCapacityEconomyClass, economyClassBookedCount, seatCapacityExecutiveClass, executiveClassBookedCount " +
                       "FROM FlightSchedule fs JOIN Flight f ON fs.flightId_schedule = f.flightId " +
                       "WHERE fs.flightId_schedule=? and fs.dateOfTravel=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fb.getFlightID());
            ps.setDate(2, fb.getDateOfTravel());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    switch (fb.getSeatCategory()) {
                        case "Economy":
                            int economy = fb.getNoOfSeats();
                            if ((rs.getInt(3) - rs.getInt(4) - economy) < 0)
                                return false;
                            break;
                        case "Executive":
                            int executive = fb.getNoOfSeats();
                            if ((rs.getInt(5) - rs.getInt(6) - executive) < 0)
                                return false;
                            break;
                        case "Business":
                            int business = fb.getNoOfSeats();
                            if ((rs.getInt(1) - rs.getInt(2) - business) < 0)
                                return false;
                            break;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean findAllFlights() {
        String query = "SELECT f.flightId, fs.dateOfTravel, f.origin, f.destination, " +
                       "(f.seatCapacityBusinessClass - fs.businessClassBooked) AS Business_Seats_Available, " +
                       "(f.seatCapacityEconomyClass - fs.economyClassBooked) AS Economy_Seats_Available, " +
                       "(f.seatCapacityExecutiveClass - fs.executiveClassBooked) AS Executive_Seats_Available, " +
                       "f.airFare AS Basic_AirFare " +
                       "FROM Flight f JOIN FlightSchedule fs ON f.flightId = fs.flightId_schedule";
        boolean res = false;
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            res = printDetails(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean findFlights(String origin, String destination, Date dateOfTravel) {
        String query = "SELECT f.flightId, fs.dateOfTravel, f.origin, f.destination, " +
                       "(f.seatCapacityBusinessClass - fs.businessClassBooked) AS Business_Seats_Available, " +
                       "(f.seatCapacityEconomyClass - fs.economyClassBooked) AS Economy_Seats_Available, " +
                       "(f.seatCapacityExecutiveClass - fs.executiveClassBooked) AS Executive_Seats_Available, " +
                       "f.airFare AS Basic_AirFare " +
                       "FROM Flight f JOIN FlightSchedule fs ON f.flightId = fs.flightId_schedule " +
                       "WHERE f.origin=? AND f.destination=? AND fs.dateOfTravel=?";
        boolean res = false;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, origin);
            ps.setString(2, destination);
            ps.setDate(3, dateOfTravel);
            try (ResultSet rs = ps.executeQuery()) {
                res = printDetails(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private boolean printDetails(ResultSet rs) {
        boolean res = false;
        System.out.println("FlightId\tDateOfTravel\tOrigin\t\tDestination\tAvailable_Business_Seats\tAvailable_Economy_Seats\tAvailable_Executive_Seats\tBasic_AirFare");
        try {
            while (rs.next()) {
                res = true;
                System.out.println(rs.getInt(1) + "\t\t" + rs.getDate(2) + "\t" + rs.getString(3)
                        + "\t\t" + rs.getString(4) + "\t\t" + rs.getInt(5) + "\t\t\t\t" + rs.getInt(6)
                        + "\t\t\t\t" + rs.getInt(7) + "\t\t\t" + rs.getDouble(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<FlightBooking> getCategoryBookings(int userId, String bookingStatus) {
        List<FlightBooking> bookings = new ArrayList<>();
        String query = "SELECT * FROM FlightBooking WHERE userId_booking=? AND bookingStatus=? ORDER BY dateOfTravel desc";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, bookingStatus);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FlightBooking f = new FlightBooking(
                        rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
                        rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getString(8),
                        rs.getDouble(9), rs.getDouble(10)
                    );
                    bookings.add(f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<FlightSchedule> searchFlights(String origin, String destination, Date dateOfTravel) {
        List<FlightSchedule> schedules = new ArrayList<>();
        String query = "Select fs.flightScheduleId from flightSchedule fs join flight f on f.flightId=fs.flightid_schedule where f.origin=? and f.destination=? and fs.dateOfTravel=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, origin);
            ps.setString(2, destination);
            ps.setDate(3, dateOfTravel);
            try (ResultSet rs = ps.executeQuery()) {
                FlightScheduleService fss = new FlightScheduleService();
                while (rs.next()) {
                    schedules.add(fss.getScheduledFlight(rs.getInt(1)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public double[] getBookingAmount(FlightBooking fb) {
        double[] amount = new double[3];
        String query = "SELECT airFare FROM Flight WHERE flightId=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, fb.getFlightID());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double airFare = rs.getDouble(1);
                    switch (fb.getSeatCategory()) {
                        case "Business":
                            airFare *= 2;
                            break;
                        case "Executive":
                            airFare *= 3;
                            break;
                    }
                    airFare = fb.getNoOfSeats() * airFare;
                    CarrierService cs = new CarrierService();
                    UserController uc = new UserController();
                    FlightService f = new FlightService();
                    CarrierDetails c = cs.getCarrier(f.getFlight(fb.getFlightID()).getCarrierId());
                    amount[0] = airFare;
                    int dis = 0;
                    LocalDate travelDate = fb.getDateOfTravel().toLocalDate();
                    LocalDate today = LocalDate.now();
                    long daysDiff = ChronoUnit.DAYS.between(today, travelDate);
                    if (daysDiff <= 30)
                        dis += c.getDiscountPercentageThirtyDaysAdvanceBooking();
                    else if (daysDiff <= 60)
                        dis += c.getDiscountPercentageSixtyDaysAdvanceBooking();
                    else
                        dis += c.getDiscountPercentageNinetyDaysAdvanceBooking();

                    if (fb.getNoOfSeats() >= 10)
                        dis += c.getBulkBookingDiscount();
                    String customerType = uc.getUser(fb.getCustomerID()).getCustomerType();
                    if (customerType == null) {
                    	customerType="Silver";
                    }
                    switch(customerType) {
                    case "Silver":
                        dis += c.getSilverUserDiscount();
                        break;
                    case "Gold":
                        dis += c.getGoldUserDiscount();
                        break;
                    case "Platinum":
                        dis += c.getPlatinumUserDiscount();
                        break;
                    default:
                        // Handle other cases or leave empty
                        break;
                }
                    amount[1] = dis;
                    double finalAmount = airFare * (1 - ((double) dis / 100));
                    amount[2] = finalAmount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }
}
