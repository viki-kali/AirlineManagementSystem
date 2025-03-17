drop table flight;
CREATE TABLE Flight (
                    FlightID INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 4001,INCREMENT BY 1) PRIMARY KEY, 
                    CarrierID_Flight INTEGER, 
                    Origin VARCHAR(50) NOT NULL,
                    Destination VARCHAR(50) NOT NULL, 
                    Airfare DOUBLE NOT NULL CHECK (Airfare > 0), 
                    SeatCapacityEconomyClass INTEGER CHECK (SeatCapacityEconomyClass >= 20), 
                    SeatCapacityBusinessClass INTEGER CHECK (SeatCapacityBusinessClass >= 10), 
                    SeatCapacityExecutiveClass INTEGER CHECK (SeatCapacityExecutiveClass >= 10),
                    CONSTRAINT fk_carrierid FOREIGN KEY (CarrierID_Flight) REFERENCES Carrier(CarrierId) ON DELETE CASCADE
                    );
INSERT INTO Flight (CarrierID_Flight, Origin, Destination, Airfare, SeatCapacityEconomyClass, SeatCapacityBusinessClass, SeatCapacityExecutiveClass) VALUES
(3001,'Delhi','Mumbai',5000,150,20,10),
(3002, 'Delhi', 'Mumbai', 5200, 140, 20, 12),
(3003, 'Bangalore', 'Chennai', 4500, 160, 22, 10),
(3001, 'Kolkata', 'Delhi', 6200, 140, 18, 14),
(3002, 'Kolkata', 'Delhi', 6300, 150, 50, 12),
(3001,'Chennai','Banglore',3000,100,50,50),
(3002,'Gujrat','Assam',4000,150,70,70),
(3003,'Delhi','Banglore',2500,175,120,60);

select *from flight;