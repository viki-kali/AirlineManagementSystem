DROP TABLE FLIGHTSCHEDULE;
CREATE TABLE FlightSchedule ( 
                    FlightScheduleID INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 5001,INCREMENT BY 1) PRIMARY KEY,
                    FlightID_Schedule INTEGER, 
                    DateOfTravel DATE NOT NULL, 
                    BusinessClassBookedCount INTEGER DEFAULT 0 CHECK (BusinessClassBookedCount >= 0), 
                    EconomyClassBookedCount INTEGER DEFAULT 0 CHECK (EconomyClassBookedCount >= 0),
                    ExecutiveClassBookedCount INTEGER DEFAULT 0 CHECK (ExecutiveClassBookedCount >= 0), 
                    CONSTRAINT fk_flightid FOREIGN KEY (FlightID_Schedule) REFERENCES Flight(FlightID) ON DELETE CASCADE 
                    );

INSERT INTO FlightSchedule(FlightID_Schedule,dateOfTravel) VALUES
(4001,'2025-03-03'),
(4002,'2025-03-03'),
(4003,'2025-03-05'),
(4004,'2025-03-05'),
(4005,'2025-03-09');
INSERT INTO FlightSchedule(FlightID_Schedule,dateOfTravel) VALUES
(4004,'2025-04-03'),
(4004,'2025-03-07'),
(4004,'2025-03-08'),
(4004,'2025-03-18');
INSERT INTO FlightSchedule(FlightID_Schedule,dateOfTravel) VALUES
(4001,'2025-02-03');

INSERT INTO FlightSchedule(FlightID_Schedule,dateOfTravel) VALUES
(4001,'2025-04-03');

select f.origin,f.destination,fs.dateoftravel from flight f join FLIGHTSCHEDULE fs on f.flightid = fs.flightid_schedule;
select *from flightschedule;

