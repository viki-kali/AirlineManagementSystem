drop table flightbooking;
CREATE TABLE FlightBooking ( 
            	    BookingID INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 6001,INCREMENT BY 1) PRIMARY KEY,
            	    FlightID_Booking INTEGER, 
            	    UserID_Booking INTEGER, 
            	    NoOfSeats INTEGER CHECK (NoOfSeats > 0), 
            	    SeatCategory VARCHAR(50) CHECK (SeatCategory IN ('Economy', 'Executive', 'Business')), 
            	    DateOfBooking DATE NOT NULL, 
            	    DateOfTravel DATE NOT NULL, 
            	    BookingStatus VARCHAR(50) CHECK (BookingStatus IN ('Booked', 'Travel Completed', 'Cancelled')), 
            	    BookingAmount DOUBLE CHECK (BookingAmount >= 0), 
            	    RefundAmount DOUBLE DEFAULT 0, 
            	    CONSTRAINT fk_flightidbook FOREIGN KEY (FlightID_Booking) REFERENCES Flight(FlightID) ON DELETE SET NULL, 
            	    CONSTRAINT fk_useridbook FOREIGN KEY (UserID_Booking) REFERENCES Users(userId) ON DELETE SET NULL 
            	);
select *from flightbooking;
update FLIGHTBOOKING set bookingstatus='Travel Completed' where bookingid=6002;


