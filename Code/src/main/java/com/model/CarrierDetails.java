package com.model;

public class CarrierDetails {

    private int CarrierId;
    private String CarrierName;
    private int DiscountPercentageThirtyDaysAdvanceBooking;
    private int DiscountPercentageSixtyDaysAdvanceBooking;
    private int DiscountPercentageNinetyDaysAdvanceBooking;
    private int BulkBookingDiscount;
    private int RefundPercentageForTicketCancellation2DaysBeforeTravelDate;
    private int RefundPercentageForTicketCancellation10DaysBeforeTravelDate;
    private int RefundPercentageForTicketCancellation20DaysBeforeTravelDate;
    private int SilverUserDiscount;
    private int GoldUserDiscount;
    private int PlatinumUserDiscount;
    
	public CarrierDetails(int carrierId, String carrierName, int discountPercentageThirtyDaysAdvanceBooking,
			int discountPercentageSixtyDaysAdvanceBooking, int discountPercentageNinetyDaysAdvanceBooking,
			int bulkBookingDiscount, int refundPercentageForTicketCancellation2DaysBeforeTravelDate,
			int refundPercentageForTicketCancellation10DaysBeforeTravelDate,
			int refundPercentageForTicketCancellation20DaysBeforeTravelDate, int silverUserDiscount,
			int goldUserDiscount, int platinumUserDiscount) {
		super();
		CarrierId = carrierId;
		CarrierName = carrierName;
		DiscountPercentageThirtyDaysAdvanceBooking = discountPercentageThirtyDaysAdvanceBooking;
		DiscountPercentageSixtyDaysAdvanceBooking = discountPercentageSixtyDaysAdvanceBooking;
		DiscountPercentageNinetyDaysAdvanceBooking = discountPercentageNinetyDaysAdvanceBooking;
		BulkBookingDiscount = bulkBookingDiscount;
		RefundPercentageForTicketCancellation2DaysBeforeTravelDate = refundPercentageForTicketCancellation2DaysBeforeTravelDate;
		RefundPercentageForTicketCancellation10DaysBeforeTravelDate = refundPercentageForTicketCancellation10DaysBeforeTravelDate;
		RefundPercentageForTicketCancellation20DaysBeforeTravelDate = refundPercentageForTicketCancellation20DaysBeforeTravelDate;
		SilverUserDiscount = silverUserDiscount;
		GoldUserDiscount = goldUserDiscount;
		PlatinumUserDiscount = platinumUserDiscount;
	}
	public int getCarrierId() {
		return CarrierId;
	}
	public void setCarrierId(int carrierId) {
		CarrierId = carrierId;
	}
	public String getCarrierName() {
		return CarrierName;
	}
	public void setCarrierName(String carrierName) {
		CarrierName = carrierName;
	}
	public int getDiscountPercentageThirtyDaysAdvanceBooking() {
		return DiscountPercentageThirtyDaysAdvanceBooking;
	}
	public void setDiscountPercentageThirtyDaysAdvanceBooking(int discountPercentageThirtyDaysAdvanceBooking) {
		DiscountPercentageThirtyDaysAdvanceBooking = discountPercentageThirtyDaysAdvanceBooking;
	}
	public int getDiscountPercentageSixtyDaysAdvanceBooking() {
		return DiscountPercentageSixtyDaysAdvanceBooking;
	}
	public void setDiscountPercentageSixtyDaysAdvanceBooking(int discountPercentageSixtyDaysAdvanceBooking) {
		DiscountPercentageSixtyDaysAdvanceBooking = discountPercentageSixtyDaysAdvanceBooking;
	}
	public int getDiscountPercentageNinetyDaysAdvanceBooking() {
		return DiscountPercentageNinetyDaysAdvanceBooking;
	}
	public void setDiscountPercentageNinteyDaysAdvanceBooking(int discountPercentageNinetyDaysAdvanceBooking) {
		DiscountPercentageNinetyDaysAdvanceBooking = discountPercentageNinetyDaysAdvanceBooking;
	}
	public int getBulkBookingDiscount() {
		return BulkBookingDiscount;
	}
	public void setBulkBookingDiscount(int bulkBookingDiscount) {
		BulkBookingDiscount = bulkBookingDiscount;
	}
	public int getRefundPercentageForTicketCancellation2DaysBeforeTravelDate() {
		return RefundPercentageForTicketCancellation2DaysBeforeTravelDate;
	}
	public void setRefundPercentageForTicketCancellation2DaysBeforeTravelDate(
			int refundPercentageForTicketCancellation2DaysBeforeTravelDate) {
		RefundPercentageForTicketCancellation2DaysBeforeTravelDate = refundPercentageForTicketCancellation2DaysBeforeTravelDate;
	}
	public int getRefundPercentageForTicketCancellation10DaysBeforeTravelDate() {
		return RefundPercentageForTicketCancellation10DaysBeforeTravelDate;
	}
	public void setRefundPercentageForTicketCancellation10DaysBeforeTravelDate(
			int refundPercentageForTicketCancellation10DaysBeforeTravelDate) {
		RefundPercentageForTicketCancellation10DaysBeforeTravelDate = refundPercentageForTicketCancellation10DaysBeforeTravelDate;
	}
	public int getRefundPercentageForTicketCancellation20DaysBeforeTravelDate() {
		return RefundPercentageForTicketCancellation20DaysBeforeTravelDate;
	}
	public void setRefundPercentageForTicketCancellation20DaysBeforeTravelDate(
			int refundPercentageForTicketCancellation20DaysBeforeTravelDate) {
		RefundPercentageForTicketCancellation20DaysBeforeTravelDate = refundPercentageForTicketCancellation20DaysBeforeTravelDate;
	}
	public int getSilverUserDiscount() {
		return SilverUserDiscount;
	}
	public void setSilverUserDiscount(int silverUserDiscount) {
		SilverUserDiscount = silverUserDiscount;
	}
	public int getGoldUserDiscount() {
		return GoldUserDiscount;
	}
	public void setGoldUserDiscount(int goldUserDiscount) {
		GoldUserDiscount = goldUserDiscount;
	}
	public int getPlatinumUserDiscount() {
		return PlatinumUserDiscount;
	}
	public void setPlatinumUserDiscount(int platinumUserDiscount) {
		PlatinumUserDiscount = platinumUserDiscount;
	}
	@Override
	public String toString() {
		return "CarrierDetails [CarrierId=" + CarrierId + ", CarrierName=" + CarrierName
				+ ", DiscountPercentageThirtyDaysAdvanceBooking=" + DiscountPercentageThirtyDaysAdvanceBooking
				+ ", DiscountPercentageSixtyDaysAdvanceBooking=" + DiscountPercentageSixtyDaysAdvanceBooking
				+ ", DiscountPercentageNinteyDaysAdvanceBooking=" + DiscountPercentageNinetyDaysAdvanceBooking
				+ ", BulkBookingDiscount=" + BulkBookingDiscount
				+ ", RefundPercentageForTicketCancellation2DaysBeforeTravelDate="
				+ RefundPercentageForTicketCancellation2DaysBeforeTravelDate
				+ ", RefundPercentageForTicketCancellation10DaysBeforeTravelDate="
				+ RefundPercentageForTicketCancellation10DaysBeforeTravelDate
				+ ", RefundPercentageForTicketCancellation20DaysBeforeTravelDate="
				+ RefundPercentageForTicketCancellation20DaysBeforeTravelDate + ", SilverUserDiscount="
				+ SilverUserDiscount + ", GoldUserDiscount=" + GoldUserDiscount + ", PlatinumUserDiscount="
				+ PlatinumUserDiscount + "]";
	}

	
}
