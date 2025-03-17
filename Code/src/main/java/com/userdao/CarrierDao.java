package com.userdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import com.usercontroller.FlightBookingService;
import com.model.CarrierDetails;
import com.helper.DBHelper;

public class CarrierDao {
    public List<CarrierDetails> getCarriers() {
        List<CarrierDetails> carriers = new ArrayList<>();
        String sql = "SELECT * FROM Carrier";
        try (
            Connection conn = DBHelper.getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql)
        ) {
            while (rs.next()) {
                CarrierDetails cr = new CarrierDetails(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getInt(6),
                    rs.getInt(7),
                    rs.getInt(8),
                    rs.getInt(9),
                    rs.getInt(10),
                    rs.getInt(11),
                    rs.getInt(12)
                );
                carriers.add(cr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carriers;
    }
    
    public boolean addCarrier(CarrierDetails cr) {
        boolean result = false;
        String sql = "INSERT INTO Carrier(carrierName,discountPercentageThirtyDaysAdvanceBooking,discountPercentageSixtyDaysAdvanceBooking,discountPercentageNinetyDaysAdvanceBooking,bulkBookingDiscount,refundPercentageForTicketCancellation2DaysBeforeTravelDate,refundPercentageForTicketCancellation10DaysBeforeTravelDate,refundPercentageForTicketCancellation20DaysBeforeTravelDate,silverUserDiscount,goldUserDiscount,platinumUserDiscount) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (
            Connection conn = DBHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, cr.getCarrierName());
            ps.setInt(2, cr.getDiscountPercentageThirtyDaysAdvanceBooking());
            ps.setInt(3, cr.getDiscountPercentageSixtyDaysAdvanceBooking());
            ps.setInt(4, cr.getDiscountPercentageNinetyDaysAdvanceBooking());
            ps.setInt(5, cr.getBulkBookingDiscount());
            ps.setInt(6, cr.getRefundPercentageForTicketCancellation2DaysBeforeTravelDate());
            ps.setInt(7, cr.getRefundPercentageForTicketCancellation10DaysBeforeTravelDate());
            ps.setInt(8, cr.getRefundPercentageForTicketCancellation20DaysBeforeTravelDate());
            ps.setInt(9, cr.getSilverUserDiscount());
            ps.setInt(10, cr.getGoldUserDiscount());
            ps.setInt(11, cr.getPlatinumUserDiscount());
            
            if (ps.executeUpdate() > 0) {
                result = true;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        System.out.println("Carrier id generated is : " + rs.getInt(1));
                }
            } else {
                System.out.println("Carrier information is not saved. Please check the data and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public CarrierDetails getCarrier(int carrierId) {
        CarrierDetails c = null;
        String query = "SELECT * FROM Carrier where carrierId=?";
        try (
            Connection conn = DBHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, carrierId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new CarrierDetails(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getInt(12)
                    );
                } else {
                    System.out.println("Carrier not found with given CarrierId");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
    
    public boolean updateCarrier(CarrierDetails cr) {
        boolean res = false;
        String query = "UPDATE Carrier SET carrierName=?,discountPercentageThirtyDaysAdvanceBooking=?,discountPercentageSixtyDaysAdvanceBooking=?,discountPercentageNinetyDaysAdvanceBooking=?,bulkBookingDiscount=?,refundPercentageForTicketCancellation2DaysBeforeTravelDate=?,refundPercentageForTicketCancellation10DaysBeforeTravelDate=?,refundPercentageForTicketCancellation20DaysBeforeTravelDate=?,silverUserDiscount=?,goldUserDiscount=?,platinumUserDiscount=? WHERE carrierId=?";
        try (
            Connection conn = DBHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, cr.getCarrierName());
            ps.setInt(2, cr.getDiscountPercentageThirtyDaysAdvanceBooking());
            ps.setInt(3, cr.getDiscountPercentageSixtyDaysAdvanceBooking());
            ps.setInt(4, cr.getDiscountPercentageNinetyDaysAdvanceBooking());
            ps.setInt(5, cr.getBulkBookingDiscount());
            ps.setInt(6, cr.getRefundPercentageForTicketCancellation2DaysBeforeTravelDate());
            ps.setInt(7, cr.getRefundPercentageForTicketCancellation10DaysBeforeTravelDate());
            ps.setInt(8, cr.getRefundPercentageForTicketCancellation20DaysBeforeTravelDate());
            ps.setInt(9, cr.getSilverUserDiscount());
            ps.setInt(10, cr.getGoldUserDiscount());
            ps.setInt(11, cr.getPlatinumUserDiscount());
            ps.setInt(12, cr.getCarrierId());
            res = ps.executeUpdate() > 0;
            if (!res)
                System.out.println("Updating Carrier Details failed, check the data and try again.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean deleteCarrier(int carrierId) {
        boolean res = false;
        String query = "DELETE FROM Carrier where carrierId=?";
        try (
            Connection conn = DBHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, carrierId);
            res = ps.executeUpdate() > 0;
            if (res) {
                FlightBookingService fbs = new FlightBookingService();
                fbs.cancelTicketByAdmin();
            } else {
                System.out.println("No carrier found with given id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
