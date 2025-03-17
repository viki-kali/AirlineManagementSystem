package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.helper.DBHelper;
import com.model.CarrierDetails;

public class CarrierDAO {

    // Create a new carrier record in the database.
    public void createCarrier(CarrierDetails carrier) throws SQLException {
        String sql = "INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, " +
                     "DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, " +
                     "BulkBookingDiscount, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, " +
                     "RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, " +
                     "SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, carrier.getCarrierName());
            ps.setInt(2, carrier.getDiscountPercentageThirtyDaysAdvanceBooking());
            ps.setInt(3, carrier.getDiscountPercentageSixtyDaysAdvanceBooking());
            ps.setInt(4, carrier.getDiscountPercentageNinetyDaysAdvanceBooking());
            ps.setInt(5, carrier.getBulkBookingDiscount());
            ps.setInt(6, carrier.getRefundPercentageForTicketCancellation2DaysBeforeTravelDate());
            ps.setInt(7, carrier.getRefundPercentageForTicketCancellation10DaysBeforeTravelDate());
            ps.setInt(8, carrier.getRefundPercentageForTicketCancellation20DaysBeforeTravelDate());
            ps.setInt(9, carrier.getSilverUserDiscount());
            ps.setInt(10, carrier.getGoldUserDiscount());
            ps.setInt(11, carrier.getPlatinumUserDiscount());

            ps.executeUpdate();

            // Retrieve and set the auto-generated CarrierId
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    carrier.setCarrierId(generatedId);
                }
            }
        }
    }

    // Retrieve a carrier by its unique ID.
    public CarrierDetails getCarrierById(int carrierId) throws SQLException {
        String sql = "SELECT * FROM Carrier WHERE CarrierId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, carrierId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CarrierDetails(
                        rs.getInt("CarrierId"),
                        rs.getString("CarrierName"),
                        rs.getInt("DiscountPercentageThirtyDaysAdvanceBooking"),
                        rs.getInt("DiscountPercentageSixtyDaysAdvanceBooking"),
                        rs.getInt("DiscountPercentageNinetyDaysAdvanceBooking"),
                        rs.getInt("BulkBookingDiscount"),
                        rs.getInt("RefundPercentageForTicketCancellation2DaysBeforeTravelDate"),
                        rs.getInt("RefundPercentageForTicketCancellation10DaysBeforeTravelDate"),
                        rs.getInt("RefundPercentageForTicketCancellation20DaysBeforeTravelDate"),
                        rs.getInt("SilverUserDiscount"),
                        rs.getInt("GoldUserDiscount"),
                        rs.getInt("PlatinumUserDiscount")
                    );
                }
            }
        }
        return null;
    }

    // Retrieve all carriers.
    public List<CarrierDetails> getAllCarriers() throws SQLException {
        List<CarrierDetails> carriers = new ArrayList<>();
        String sql = "SELECT * FROM Carrier";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CarrierDetails carrier = new CarrierDetails(
                    rs.getInt("CarrierId"),
                    rs.getString("CarrierName"),
                    rs.getInt("DiscountPercentageThirtyDaysAdvanceBooking"),
                    rs.getInt("DiscountPercentageSixtyDaysAdvanceBooking"),
                    rs.getInt("DiscountPercentageNinetyDaysAdvanceBooking"),
                    rs.getInt("BulkBookingDiscount"),
                    rs.getInt("RefundPercentageForTicketCancellation2DaysBeforeTravelDate"),
                    rs.getInt("RefundPercentageForTicketCancellation10DaysBeforeTravelDate"),
                    rs.getInt("RefundPercentageForTicketCancellation20DaysBeforeTravelDate"),
                    rs.getInt("SilverUserDiscount"),
                    rs.getInt("GoldUserDiscount"),
                    rs.getInt("PlatinumUserDiscount")
                );
                carriers.add(carrier);
            }
        }
        return carriers;
    }

    // Update an existing carrier.
    public boolean updateCarrier(CarrierDetails carrier) throws SQLException {
        String sql = "UPDATE Carrier SET CarrierName = ?, DiscountPercentageThirtyDaysAdvanceBooking = ?, " +
                     "DiscountPercentageSixtyDaysAdvanceBooking = ?, DiscountPercentageNinetyDaysAdvanceBooking = ?, " +
                     "BulkBookingDiscount = ?, RefundPercentageForTicketCancellation2DaysBeforeTravelDate = ?, " +
                     "RefundPercentageForTicketCancellation10DaysBeforeTravelDate = ?, RefundPercentageForTicketCancellation20DaysBeforeTravelDate = ?, " +
                     "SilverUserDiscount = ?, GoldUserDiscount = ?, PlatinumUserDiscount = ? WHERE CarrierId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, carrier.getCarrierName());
            ps.setInt(2, carrier.getDiscountPercentageThirtyDaysAdvanceBooking());
            ps.setInt(3, carrier.getDiscountPercentageSixtyDaysAdvanceBooking());
            ps.setInt(4, carrier.getDiscountPercentageNinetyDaysAdvanceBooking());
            ps.setInt(5, carrier.getBulkBookingDiscount());
            ps.setInt(6, carrier.getRefundPercentageForTicketCancellation2DaysBeforeTravelDate());
            ps.setInt(7, carrier.getRefundPercentageForTicketCancellation10DaysBeforeTravelDate());
            ps.setInt(8, carrier.getRefundPercentageForTicketCancellation20DaysBeforeTravelDate());
            ps.setInt(9, carrier.getSilverUserDiscount());
            ps.setInt(10, carrier.getGoldUserDiscount());
            ps.setInt(11, carrier.getPlatinumUserDiscount());
            ps.setInt(12, carrier.getCarrierId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Delete a carrier by its ID.
    public boolean deleteCarrier(int carrierId) throws SQLException {
        String sql = "DELETE FROM Carrier WHERE CarrierId = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, carrierId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
