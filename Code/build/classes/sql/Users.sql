drop table users;
CREATE TABLE Users(
userId INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 10000,INCREMENT BY 1) PRIMARY KEY,
firstName VARCHAR(50) NOT NULL,
lastName VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
role VARCHAR(50) CHECK (role IN ('Admin', 'Customer')),
customerType VARCHAR(50) CHECK (customerType IN ('Silver', 'Gold', 'Platinum')),
phone VARCHAR(10) NOT NULL, 
emailId VARCHAR(50) UNIQUE NOT NULL, 
address VARCHAR(100), 
dateOfBirth DATE
);
INSERT INTO Users(firstName,lastName,password,role,customerType,phone,emailId,address,dateOfBirth) VALUES
('admin','A','admin123','Admin','Silver','9876543210','ramesh198@gmail.com','Ameerpet Beside metro station','2001-02-02'),
('Suresh','B','Suresh@123','Customer','Silver','9876453210','suresh198@gmail.com','Ameerpet Beside metro station','2001-01-12'),
('Ramya','C','Ramya@123','Customer','Silver','9876543960','ramya198@gmail.com','Ameerpet Beside metro station','2001-09-03');

select * from users;

