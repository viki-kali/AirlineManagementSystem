drop table carrier;
CREATE TABLE Carrier ( 
                    CarrierId INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 3001,INCREMENT BY 1) PRIMARY KEY, 
                    CarrierName VARCHAR(50) NOT NULL,
                    DiscountPercentageThirtyDaysAdvanceBooking INTEGER CHECK (DiscountPercentageThirtyDaysAdvanceBooking BETWEEN 0 AND 100),
                    DiscountPercentageSixtyDaysAdvanceBooking INTEGER CHECK (DiscountPercentageSixtyDaysAdvanceBooking BETWEEN 0 AND 100),
                    DiscountPercentageNinetyDaysAdvanceBooking INTEGER CHECK (DiscountPercentageNinetyDaysAdvanceBooking BETWEEN 0 AND 100),
                    BulkBookingDiscount INTEGER CHECK (BulkBookingDiscount BETWEEN 0 AND 100),
                    RefundPercentageForTicketCancellation2DaysBeforeTravelDate INTEGER CHECK (RefundPercentageForTicketCancellation2DaysBeforeTravelDate BETWEEN 0 AND 100),
                    RefundPercentageForTicketCancellation10DaysBeforeTravelDate INTEGER CHECK (RefundPercentageForTicketCancellation10DaysBeforeTravelDate BETWEEN 0 AND 100),
                    RefundPercentageForTicketCancellation20DaysBeforeTravelDate INTEGER CHECK (RefundPercentageForTicketCancellation20DaysBeforeTravelDate BETWEEN 0 AND 100),
                    SilverUserDiscount INTEGER CHECK (SilverUserDiscount BETWEEN 0 AND 100),
                    GoldUserDiscount INTEGER CHECK (GoldUserDiscount BETWEEN 0 AND 100),
                    PlatinumUserDiscount INTEGER CHECK (PlatinumUserDiscount BETWEEN 0 AND 100)
                    );

INSERT INTO Carrier(CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, BulkBookingDiscount, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate,  SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES
('Air Asia',5,10,15,3,50,65,80,2,3,4),
('IndiGo', 8, 12, 18, 4, 40, 55, 65, 6, 12, 18),
('SpiceJet', 7, 10, 15, 3, 45, 58, 68, 7, 14, 20);

select *from CARRIER;