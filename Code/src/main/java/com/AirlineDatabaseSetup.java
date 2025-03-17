package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AirlineDatabaseSetup {

    // JDBC connection URL for Derby
    private static final String JDBC_URL = "jdbc:derby:AirlineDB;create=true";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             Statement statement = connection.createStatement()) {

            // Create Users table
        	statement.executeUpdate("CREATE TABLE Users (" +
        		    "userId INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 10000,INCREMENT BY 1) PRIMARY KEY, " +
        		    "firstName VARCHAR(50) NOT NULL, " +
        		    "lastName VARCHAR(50) NOT NULL, " +
        		    "password VARCHAR(50) NOT NULL, " +
        		    "role VARCHAR(50) CHECK (role IN ('Admin', 'Customer')), " +
        		    "customerType VARCHAR(50) CHECK (customerType IN ('Silver', 'Gold', 'Platinum')), " +
        		    "phone VARCHAR(10) NOT NULL, " +
        		    "emailId VARCHAR(50) UNIQUE NOT NULL, " +
        		    "address VARCHAR(100), " +
        		    "dateOfBirth DATE"+
        			")");
        	
            // Create Carrier table
            statement.executeUpdate("CREATE TABLE Carrier ( " +
                    "CarrierId INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 3001,INCREMENT BY 1) PRIMARY KEY, " +
                    "CarrierName VARCHAR(50) NOT NULL, " +
                    "DiscountPercentageThirtyDaysAdvanceBooking INTEGER CHECK (DiscountPercentageThirtyDaysAdvanceBooking BETWEEN 0 AND 100), " +
                    "DiscountPercentageSixtyDaysAdvanceBooking INTEGER CHECK (DiscountPercentageSixtyDaysAdvanceBooking BETWEEN 0 AND 100), " +
                    "DiscountPercentageNinetyDaysAdvanceBooking INTEGER CHECK (DiscountPercentageNinetyDaysAdvanceBooking BETWEEN 0 AND 100), " +
                    "BulkBookingDiscount INTEGER CHECK (BulkBookingDiscount BETWEEN 0 AND 100), " +
                    "RefundPercentageForTicketCancellation2DaysBeforeTravelDate INTEGER CHECK (RefundPercentageForTicketCancellation2DaysBeforeTravelDate BETWEEN 0 AND 100), " +
                    "RefundPercentageForTicketCancellation10DaysBeforeTravelDate INTEGER CHECK (RefundPercentageForTicketCancellation10DaysBeforeTravelDate BETWEEN 0 AND 100), " +
                    "RefundPercentageForTicketCancellation20DaysBeforeTravelDate INTEGER CHECK (RefundPercentageForTicketCancellation20DaysBeforeTravelDate BETWEEN 0 AND 100), " +
                    "SilverUserDiscount INTEGER CHECK (SilverUserDiscount BETWEEN 0 AND 100), " +
                    "GoldUserDiscount INTEGER CHECK (GoldUserDiscount BETWEEN 0 AND 100), " +
                    "PlatinumUserDiscount INTEGER CHECK (PlatinumUserDiscount BETWEEN 0 AND 100) " +
                    ")");

            // Create Flight table
            statement.executeUpdate("CREATE TABLE Flight ( " +
                    "FlightID INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 4001,INCREMENT BY 1) PRIMARY KEY, " +
                    "CarrierID_Flight INTEGER, " +
                    "Origin VARCHAR(50) NOT NULL, " +
                    "Destination VARCHAR(50) NOT NULL, " +
                    "Airfare DOUBLE NOT NULL CHECK (Airfare > 0), " +
                    "SeatCapacityEconomyClass INTEGER CHECK (SeatCapacityEconomyClass >= 20), " +
                    "SeatCapacityBusinessClass INTEGER CHECK (SeatCapacityBusinessClass >= 10), " +
                    "SeatCapacityExecutiveClass INTEGER CHECK (SeatCapacityExecutiveClass >= 10), " +
                    "CONSTRAINT fk_carrierid FOREIGN KEY (CarrierID_Flight) REFERENCES Carrier(CarrierId) ON DELETE CASCADE " +
                    ")");

            // Create FlightSchedule table
            statement.executeUpdate("CREATE TABLE FlightSchedule ( " +
                    "FlightScheduleID INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 5001,INCREMENT BY 1) PRIMARY KEY, " +
                    "FlightID_Schedule INTEGER, " +
                    "DateOfTravel DATE NOT NULL, " +
                    "BusinessClassBookedCount INTEGER DEFAULT 0 CHECK (BusinessClassBookedCount >= 0), " +
                    "EconomyClassBookedCount INTEGER DEFAULT 0 CHECK (EconomyClassBookedCount >= 0), " +
                    "ExecutiveClassBookedCount INTEGER DEFAULT 0 CHECK (ExecutiveClassBookedCount >= 0), " +
                    "CONSTRAINT fk_flightid FOREIGN KEY (FlightID_Schedule) REFERENCES Flight(FlightID) ON DELETE CASCADE " +
                    ")");

            // Create FlightBooking table
            statement.executeUpdate("CREATE TABLE FlightBooking ( " +
            	    "BookingID INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 6001,INCREMENT BY 1) PRIMARY KEY, " +
            	    "FlightID_Booking INTEGER, " +
            	    "UserID_Booking INTEGER, " +
            	    "NoOfSeats INTEGER CHECK (NoOfSeats > 0), " +
            	    "SeatCategory VARCHAR(50) CHECK (SeatCategory IN ('Economy', 'Executive', 'Business')), " +
            	    "DateOfBooking DATE NOT NULL, " +
            	    "DateOfTravel DATE NOT NULL, " +
            	    "BookingStatus VARCHAR(50) CHECK (BookingStatus IN ('Booked', 'Travel Completed', 'Cancelled')), " +
            	    "BookingAmount DOUBLE CHECK (BookingAmount >= 0), " +
            	    "RefundAmount DOUBLE DEFAULT 0, " + // Fixed column name and added comma
            	    "CONSTRAINT fk_flightidbook FOREIGN KEY (FlightID_Booking) REFERENCES Flight(FlightID) ON DELETE SET NULL, " + // Changed to SET NULL
            	    "CONSTRAINT fk_useridbook FOREIGN KEY (UserID_Booking) REFERENCES Users(userId) ON DELETE SET NULL " +
            	")");


            // Insert data into Users table
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Admin', 'User', 'admin123', 'Admin', NULL, '1112223333', 'admin@example.com', '999 Admin St, New York, NY, USA, 10001', '1980-01-01')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('John', 'Doe', 'Pass12**', 'Customer', 'Gold', '1234567890', 'johndoe@example.com', '123 Main St, New York, NY, USA, 10001', '1990-05-15')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Jane', 'Smith', 'pass456', 'Admin', NULL, '9876543210', 'janesmith@example.com', '456 Elm St, Los Angeles, CA, USA, 90001', '1985-08-22')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Alice', 'Brown', 'alice123', 'Customer', 'Silver', '5556667777', 'alice@example.com', '789 Oak St, Chicago, IL, USA, 60601', '1992-12-10')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Bob', 'Miller', 'bobpass', 'Customer', 'Platinum', '1122334455', 'bob@example.com', '101 Pine St, Houston, TX, USA, 77001', '1988-06-25')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Charlie', 'Wilson', 'charliepw', 'Customer', 'Gold', '9988776655', 'charlie@example.com', '222 Maple St, San Francisco, CA, USA, 94101', '1995-09-14')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('David', 'Taylor', 'davidpw', 'Customer', 'Silver', '7766554433', 'david@example.com', '333 Cedar St, Seattle, WA, USA, 98101', '1983-11-05')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Emma', 'Thomas', 'emmapw', 'Customer', 'Platinum', '6655443322', 'emma@example.com', '444 Birch St, Denver, CO, USA, 80201', '1991-03-20')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Frank', 'Harris', 'frankpw', 'Customer', 'Gold', '5544332211', 'frank@example.com', '555 Aspen St, Boston, MA, USA, 02101', '1987-07-30')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Grace', 'Martin', 'gracepw', 'Customer', 'Silver', '4433221100', 'grace@example.com', '666 Redwood St, Miami, FL, USA, 33101', '1994-12-08')");
            statement.executeUpdate("INSERT INTO Users (firstName, lastName, password, role, customerType, phone, emailId, address, dateOfBirth) VALUES ('Henry', 'Clark', 'henrypw', 'Customer', 'Platinum', '3322110099', 'henry@example.com', '777 Oakwood St, Atlanta, GA, USA, 30301', '1986-06-18')");
            // Insert data into Carrier table
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline A', 5, 10, 15, 50, 70, 90, 10, 5, 10, 15)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline B', 6, 12, 18, 45, 65, 85, 12, 6, 12, 18)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline C', 4, 8, 12, 55, 75, 95, 8, 4, 8, 12)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline D', 7, 14, 21, 35, 55, 80, 15, 7, 14, 21)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline E', 5, 9, 13, 50, 70, 90, 10, 5, 9, 13)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline F', 8, 16, 24, 30, 50, 75, 18, 8, 16, 24)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline G', 9, 18, 27, 25, 45, 70, 20, 9, 18, 27)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline H', 3, 6, 9, 60, 80, 100, 5, 3, 6, 9)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline I', 6, 12, 18, 40, 60, 85, 12, 6, 12, 18)");
            statement.executeUpdate("INSERT INTO Carrier (CarrierName, DiscountPercentageThirtyDaysAdvanceBooking, DiscountPercentageSixtyDaysAdvanceBooking, DiscountPercentageNinetyDaysAdvanceBooking, RefundPercentageForTicketCancellation2DaysBeforeTravelDate, RefundPercentageForTicketCancellation10DaysBeforeTravelDate, RefundPercentageForTicketCancellation20DaysBeforeTravelDate, BulkBookingDiscount, SilverUserDiscount, GoldUserDiscount, PlatinumUserDiscount) VALUES ('Airline J', 10, 20, 30, 20, 40, 65, 25, 10, 20, 30)");
            // Insert data into Flight table
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3001, 'New York', 'Los Angeles', 350, 150, 50, 20)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3001, 'Chicago', 'Miami', 250, 180, 40, 25)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3001, 'San Francisco', 'Houston', 400, 160, 45, 15)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3004, 'Seattle', 'Miami', 200, 140, 30, 10)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3002, 'Boston', 'Las Vegas', 300, 170, 35, 12)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3002, 'Dallas', 'Orlando', 275, 160, 40, 18)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3007, 'Atlanta', 'Phoenix', 320, 190, 50, 22)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3009, 'Washington DC', 'San Diego', 380, 180, 45, 20)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3009, 'Philadelphia', 'Miami', 310, 165, 40, 18)");
            statement.executeUpdate("INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES (3010, 'Detroit', 'Charlotte', 260, 155, 35, 12)");
            // Insert data into FlightSchedule table
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4001, '2025-05-01', 10, 50, 5)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4001, '2025-05-02', 0, 0, 0)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4001, '2025-05-03', 0, 0, 0)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4001, '2025-05-04', 0, 0, 0)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4002, '2025-05-05', 8, 60, 6)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4003, '2025-05-10', 12, 55, 4)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4004, '2025-05-15', 15, 45, 8)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4005, '2025-05-20', 9, 70, 3)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4006, '2025-05-25', 11, 65, 7)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4007, '2025-05-30', 13, 40, 9)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4008, '2025-05-05', 14, 75, 5)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4009, '2025-05-10', 7, 55, 6)");
            statement.executeUpdate("INSERT INTO FlightSchedule (FlightID_Schedule, DateOfTravel, BusinessClassBookedCount, EconomyClassBookedCount, ExecutiveClassBookedCount) VALUES (4010, '2025-05-15', 10, 50, 8)");
            // Insert data into FlightBooking table
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4001, 10001, 2, 'Economy', '2025-02-01', '2025-03-01', 'Booked', 700)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4001, 10002, 1, 'Business', '2025-02-02', '2025-03-01', 'Booked', 500)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4001, 10003, 3, 'Executive', '2025-02-03', '2025-03-01', 'Booked', 1200)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4004, 10004, 2, 'Economy', '2025-02-05', '2025-03-15', 'Cancelled', 400)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4005, 10005, 4, 'Business', '2025-02-07', '2025-03-20', 'Booked', 2000)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4006, 10006, 1, 'Executive', '2025-02-10', '2025-03-25', 'Travel Completed', 400)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4007, 10007, 3, 'Economy', '2025-02-12', '2025-03-30', 'Cancelled', 900)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4008, 10008, 2, 'Business', '2025-02-14', '2025-04-05', 'Cancelled', 1000)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4009, 10009, 5, 'Executive', '2025-02-16', '2025-04-10', 'Booked', 2000)");
            statement.executeUpdate("INSERT INTO FlightBooking (FlightID_Booking, UserID_Booking, NoOfSeats, SeatCategory, DateOfBooking, DateOfTravel, BookingStatus, BookingAmount) VALUES (4010, 10010, 2, 'Economy', '2025-02-18', '2025-04-15', 'Travel Completed', 600)");
            System.out.println("Database, tables, and data created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
