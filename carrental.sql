CREATE DATABASE rental;
use rental;

-- Set the database to single-user mode to disconnect other users
ALTER DATABASE rental
SET SINGLE_USER WITH ROLLBACK IMMEDIATE;

drop database rental;


DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS Car_History;
DROP TABLE IF EXISTS Collateral;
DROP TABLE IF EXISTS Insurance;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Rental_Branch;

DROP VIEW Revenue_Summary;

-- Rental Branch Table
CREATE TABLE Rental_Branch (
    Branch_id VARCHAR(5) NOT NULL PRIMARY KEY CHECK (Branch_Id LIKE 'bid%'),
    B_Region VARCHAR(20) NOT NULL,
    B_Zone VARCHAR(20),
    B_Woreda INT NOT NULL,
    CONSTRAINT check_branch_zone CHECK (B_Zone NOT LIKE '%[^a-zA-Z0-9 ]%')
);

--indexes for rental branch
CREATE INDEX idx_rental_branch_region ON Rental_Branch (B_Region);
CREATE INDEX idx_rental_branch_zone ON Rental_Branch (B_Zone);


-- Employee Table
CREATE TABLE Employee (
    Emp_id VARCHAR(6),
    E_F_name VARCHAR(25) NOT NULL,
    E_M_name VARCHAR(25) NOT NULL,
    E_L_name VARCHAR(25) NOT NULL,
    E_region VARCHAR(25) NOT NULL,       
    E_woreda VARCHAR(25) NOT NULL,
    E_houseno VARCHAR(25) NOT NULL,
    E_role VARCHAR(15) NOT NULL DEFAULT 'employee',
    Branch_id VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES Rental_Branch(Branch_Id),
    email VARCHAR(25) NOT NULL,
	PhoneNo VARCHAR(15) NOT NULL DEFAULT '+0000000000' CHECK (PhoneNo LIKE '+%[0-9]%'),
    CONSTRAINT check_emp_id CHECK (emp_id LIKE 'emp%'),
    CONSTRAINT check_F_name CHECK (E_F_name NOT LIKE '%[^a-zA-Z]%'),
    CONSTRAINT check_M_name CHECK (E_M_name NOT LIKE '%[^a-zA-Z]%'),
    CONSTRAINT check_L_name CHECK (E_L_name NOT LIKE '%[^a-zA-Z]%'),
    CONSTRAINT check_email CHECK (email LIKE '%@%.%'),
    PRIMARY KEY(emp_id)
);


--Indexes for Employee 
CREATE INDEX idx_Emp_id ON Employee(Emp_id);

-- Customer Table
CREATE TABLE Customer (
    Customer_Id VARCHAR(20) PRIMARY KEY  CHECK (Customer_Id LIKE 'cid%'),
    C_FName VARCHAR(20) NOT NULL,
    C_LName VARCHAR(20) NOT NULL,
    C_Phone_No VARCHAR(14) NOT NULL,
    C_Email VARCHAR(50) NOT NULL,
    C_Region VARCHAR(20) NOT NULL,
    C_Zone VARCHAR(20) NOT NULL,
    C_House_No VARCHAR(20),
    C_Drivers_LN VARCHAR(20) UNIQUE NOT NULL,
    CONSTRAINT check_C_FName CHECK (C_FName NOT LIKE '%[^a-zA-Z]%'),
    CONSTRAINT check_C_LName CHECK (C_LName NOT LIKE '%[^a-zA-Z]%'),
    CONSTRAINT check_C_Email CHECK (C_Email LIKE '%@%.%')
);
--Customer indexes
CREATE INDEX idx_Car_Id ON Customer(Customer_id);


-- Car Table
CREATE TABLE Car (
    Car_Id VARCHAR(20) PRIMARY KEY CHECK (Car_Id LIKE 'caid%'),
	Branch_Id VARCHAR(5) NOT NULL FOREIGN KEY REFERENCES Rental_Branch(Branch_Id),
    Model VARCHAR(30),
    Plate_No VARCHAR(20) UNIQUE NOT NULL,
    IsAvailable CHAR(1) DEFAULT '1' ,
    CONSTRAINT check_car_model CHECK (Model NOT LIKE '%[^a-zA-Z0-9 -]%'),
	CONSTRAINT check_car_IsAvaliable CHECK (IsAvailable IN ('1', '0')),
);

--Indexes For Car
CREATE INDEX idx_car_model ON Car (Model);
CREATE INDEX idx_car_isavailable ON Car (IsAvailable);


-- Insurance Table
CREATE TABLE Insurance (
    Insurance_Id VARCHAR(20) PRIMARY KEY CHECK (Insurance_Id LIKE 'iid%'),
    Car_Id VARCHAR(20) FOREIGN KEY REFERENCES Car(Car_Id),
    Insurance_Provider VARCHAR(20),
    Policy_Number VARCHAR(20),
    I_Expiry_Date DATE
);

--Indexes for Insurance 
CREATE INDEX idx_insurance_car_id ON Insurance (Car_Id);


-- Reservation Table
CREATE TABLE Reservation (
    Reservation_Id VARCHAR(20) PRIMARY KEY CHECK (Reservation_Id LIKE 'rid%'),
    Car_Id VARCHAR(20) NOT NULL CHECK (Car_Id LIKE 'caid%') FOREIGN KEY REFERENCES Car(Car_Id),
    Customer_Id VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Customer(Customer_Id),
	Emp_Id VARCHAR(6) NOT NULL FOREIGN KEY REFERENCES Employee(Emp_Id),
    Reservation_Status TINYINT DEFAULT 0, -- 0: Canceled, 1: Pending, 2: Confirmed
	Collateral_Id VARCHAR(20) NULL, --Initally nullable for status being canceled and pending
    PickUp_Date DATE NOT NULL,
    Dropoff_Date DATE NOT NULL,
    Pickup_Location VARCHAR(20) NOT NULL,
    Dropoff_Location VARCHAR(20) NOT NULL,
    Payment_Id VARCHAR(20) NULL, -- Initially nullable
    Insurance_Id VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Insurance(Insurance_Id),
    CONSTRAINT check_reservation_status CHECK (Reservation_Status IN (0, 1, 2)),
    CONSTRAINT check_pickup_location CHECK (Pickup_Location NOT LIKE '%[^a-zA-Z0-9 ]%'),
    CONSTRAINT check_dropoff_location CHECK (Dropoff_Location NOT LIKE '%[^a-zA-Z0-9 ]%')
);

--Indexes for Reservation
CREATE INDEX idx_reservation_car_id ON Reservation (Car_Id);
CREATE INDEX idx_reservation_customer_id ON Reservation (Customer_Id);
CREATE INDEX idx_reservation_pickup_date ON Reservation (PickUp_Date);
CREATE INDEX idx_reservation_status ON Reservation (Reservation_Status);


-- Collateral Table
CREATE TABLE Collateral (
    Collateral_Id VARCHAR(20) PRIMARY KEY CHECK (Collateral_Id LIKE 'col%'),
    Customer_Id VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Customer(Customer_Id),
    Car_Id VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Car(Car_Id),
	Reservation_Id VARCHAR(20) FOREIGN KEY REFERENCES Reservation(Reservation_Id),
    Collateral_Type VARCHAR(50) NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    Date_Received DATE 
);

-- Payment Table
CREATE TABLE Payment (
    Payment_Id VARCHAR(20) PRIMARY KEY CHECK (Payment_Id LIKE 'pid%'),
    Reservation_Id VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Reservation(Reservation_Id),
    Price_Per_Car DECIMAL(10, 2) NOT NULL,
    No_Cars INT NOT NULL,
	Payment_Type VARCHAR(8),
    Total_Price DECIMAL(10, 2),
    Payment_Method VARCHAR(20),
    Payment_Date DATE,
    CONSTRAINT check_payment_method CHECK (Payment_Method IN ('Card', 'Cash', 'Other')),
	CONSTRAINT check_payment_type CHECK (Payment_Type IN ('Full', 'Partial')),
    CONSTRAINT check_total_price CHECK (Total_Price >= 0)
);

-- After both tables are created, add the foreign key to Reservation
ALTER TABLE Reservation
ADD CONSTRAINT fk_Collateral_id FOREIGN KEY (Collateral_Id) REFERENCES Collateral(Collateral_Id);

ALTER TABLE Reservation
ADD CONSTRAINT fk_payment_id FOREIGN KEY (Payment_Id) REFERENCES Payment(Payment_Id);

--Indexes for payment
CREATE INDEX idx_payment_reservation_id ON Payment (Reservation_Id);


-- Car History Table
CREATE TABLE Car_History (
    History_Id VARCHAR(20) PRIMARY KEY CHECK (History_Id LIKE 'hid%'),
    Car_Id VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Car(Car_Id),
    Description VARCHAR(50),
    Record_Type VARCHAR(20),
    CONSTRAINT check_record_type CHECK (Record_Type IN ('Service', 'Damage', 'Maintenance', 'Other'))
);

EXEC sp_help 'car_history';

--Table Population
-- Populating Rental_Branch Table
INSERT INTO Rental_Branch (Branch_id, B_Region, B_Zone, B_Woreda) 
VALUES 
('bid01', 'Addis Ababa', 'Bole', 1),
('bid02', 'Oromia', 'Adama', 2),
('bid03', 'Amhara', 'Bahir Dar', 3);


-- Populating Employee Table
INSERT INTO Employee (Emp_id, E_F_name, E_M_name, E_L_name, E_region, E_woreda, E_houseno, E_role, Branch_id, email) 
VALUES
('emp01', 'John', 'Doe', 'Smith', 'Addis Ababa', '1', '101', 'Manager', 'bid01', 'john.doe@example.com'),
('emp02', 'Jane', 'Anne', 'Brown', 'Oromia', '2', '202', 'employee', 'bid02', 'jane.anne@example.com'),
('emp03', 'Mike', 'Lee', 'Johnson', 'Amhara', '3', '303', 'employee', 'bid03', 'mike.lee@example.com'),
('emp04', 'Marta', 'Dereje', 'Kebede', 'Amhara', 'Bahir Dar', 'H330', 'Manager', 'bid03', 'marta@rental.com'),
('emp05', 'Bekele', 'Haile', 'Tamrat', 'Oromia', 'Adama', 'H221', 'employee', 'bid02', 'bekele@rental.com'),
('emp06', 'Sena', 'Abebe', 'Tadesse', 'Addis Ababa', '4', '404', 'employee', 'bid01', 'sena@rental.com'),
('emp07', 'Kebede', 'Tewodros', 'Tesfaye', 'Oromia', '5', '505', 'employee', 'bid02', 'kebede@rental.com'),
('emp08', 'Hana', 'Mulu', 'Solomon', 'Amhara', '6', '606', 'employee', 'bid03', 'hana@rental.com'),
('emp09', 'Yared', 'Tesfaye', 'Biruk', 'Addis Ababa', '7', '707', 'employee', 'bid01', 'yared@rental.com'),
('emp10', 'Fikirte', 'Alem', 'Girma', 'Oromia', '8', '808', 'employee', 'bid02', 'fikirte@rental.com');


-- Populating Customer Table
INSERT INTO Customer (Customer_Id, C_FName, C_LName, C_Phone_No, C_Email, C_Region, C_Zone, C_House_No, C_Drivers_LN) VALUES
('cid01', 'Kassahun', 'Getachew', '0911234567', 'kassahun@gmail.com', 'Addis Ababa', 'Bole', 'H001', 'DL1234'),
('cid02', 'Hanna', 'Tesfaye', '0912234567', 'hanna@gmail.com', 'Addis Ababa', 'Bole', 'H002', 'DL1235'),
('cid03', 'Meron', 'Assefa', '0913234567', 'meron@gmail.com', 'Oromia', 'Adama', 'H003', 'DL1236'),
('cid04', 'Dereje', 'Tadesse', '0914234567', 'dereje@gmail.com', 'Amhara', 'Bahir Dar', 'H004', 'DL1237'),
('cid05', 'Tigist', 'Gebre', '0915234567', 'tigist@gmail.com', 'Addis Ababa', 'Bole', 'H005', 'DL1238'),
('cid06', 'Samuel', 'Yohannes', '0916234567', 'samuel@gmail.com', 'Oromia', 'Adama', 'H006', 'DL1239'),
('cid07', 'Lensa', 'Birhanu', '0917234567', 'lensa@gmail.com', 'Amhara', 'Bahir Dar', 'H007', 'DL1240'),
('cid08', 'Robel', 'Desta', '0918234567', 'robel@gmail.com', 'Addis Ababa', 'Bole', 'H008', 'DL1241'),
('cid09', 'Betelhem', 'Abebe', '0919234567', 'betelhem@gmail.com', 'Oromia', 'Adama', 'H009', 'DL1242'),
('cid10', 'Fikir', 'Lemma', '0920234567', 'fikir@gmail.com', 'Amhara', 'Bahir Dar', 'H010', 'DL1243');

-- Populating Car Table 
INSERT INTO Car (Car_Id, Branch_Id, Model, Plate_No, IsAvailable) VALUES
('caid01', 'bid01', 'Toyota Corolla', 'ABC123', '0'),
('caid02', 'bid02', 'Nissan Sunny', 'DEF456', '0'),
('caid03', 'bid03', 'Hyundai Elantra', 'GHI789', '1'),
('caid04', 'bid01', 'Kia Sportage', 'JKL012', '0'),
('caid05', 'bid02', 'Mazda', 'MNO345', '0'),
('caid06', 'bid03', 'Honda Civic', 'PQR678', '1'),
('caid07', 'bid01', 'Suzuki Swift', 'STU901', '0'),
('caid08', 'bid02', 'Ford Ranger', 'VWX234', '0'),
('caid09', 'bid03', 'Chevrolet Malibu', 'YZA567', '1'),
('caid10', 'bid01', 'Volkswagen Polo', 'BCD890', '0'),
('caid11', 'bid02', 'BMW X', 'EFG123', '0'),
('caid12', 'bid03', 'Mercedes Benz C Class', 'HIJ456', '0'),
('caid13', 'bid01', 'Subaru Outback', 'KLM789', '1'),
('caid14', 'bid02', 'Audi A4', 'NOP012', '0'),
('caid15', 'bid03', 'Jeep Compass', 'QRS345', '0'),
('caid16', 'bid01', 'Ford Focus', 'TUV678', '0'),
('caid17', 'bid02', 'Tesla Model 3', 'WXY901', '0'),
('caid18', 'bid03', 'Toyota Rav4', 'ZAB234', '1'),
('caid19', 'bid01', 'Honda CR-V', 'CDE567', '1'),
('caid20', 'bid02', 'Hyundai Tucson', 'FGH890', '0');


-- Populating Insurance Table
INSERT INTO Insurance (Insurance_Id, Car_Id, Insurance_Provider, Policy_Number, I_Expiry_Date) VALUES
('iid01', 'caid01', 'ABC Insurance', 'POL123', '2025-06-01'),
('iid02', 'caid02', 'XYZ Insurance', 'POL456', '2025-07-01'),
('iid03', 'caid04', 'LMN Insurance', 'POL789', '2025-08-01'),
('iid04', 'caid05', 'PQR Insurance', 'POL012', '2025-09-01'),
('iid05', 'caid06', 'DEF Insurance', 'POL345', '2025-10-01'),
('iid06', 'caid07', 'GHI Insurance', 'POL678', '2025-11-01'),
('iid07', 'caid08', 'JKL Insurance', 'POL901', '2025-12-01'),
('iid08', 'caid09', 'MNO Insurance', 'POL234', '2026-01-01'),
('iid09', 'caid10', 'STU Insurance', 'POL567', '2026-02-01'),
('iid10', 'caid11', 'VWX Insurance', 'POL890', '2026-03-01'),
('iid11', 'caid12', 'YZA Insurance', 'POL321', '2026-04-01'),
('iid12', 'caid13', 'BCD Insurance', 'POL654', '2026-05-01');

INSERT INTO Reservation (Reservation_Id, Car_Id, Customer_Id, Emp_Id, Reservation_Status, Collateral_Id, PickUp_Date, Dropoff_Date, Pickup_Location, Dropoff_Location, Payment_Id, Insurance_Id) 
VALUES
('rid01', 'caid01', 'cid01', 'emp01', 2, NULL, '2024-06-01', '2024-06-10', 'Bole', 'Bole', NULL, 'iid01'),
('rid02', 'caid02', 'cid02', 'emp02', 2, NULL, '2024-06-05', '2024-06-15', 'Adama', 'Adama', NULL, 'iid02'),
('rid03', 'caid04', 'cid03', 'emp03', 2, NULL, '2024-06-03', '2024-06-13', 'Bahir Dar', 'Bahir Dar', NULL, 'iid03'),
('rid04', 'caid05', 'cid04', 'emp04', 2, NULL, '2024-06-06', '2024-06-16', 'Bole', 'Adama', NULL, 'iid04'),
('rid05', 'caid06', 'cid05', 'emp05', 2, NULL, '2024-06-07', '2024-06-17', 'Bole', 'Bole', NULL, 'iid05'),
('rid06', 'caid07', 'cid06', 'emp06', 2, NULL, '2024-06-08', '2024-06-18', 'Adama', 'Bole', NULL, 'iid06'),
('rid07', 'caid08', 'cid07', 'emp07', 2, NULL, '2024-06-09', '2024-06-19', 'Bahir Dar', 'Bole', NULL, 'iid07'),
('rid08', 'caid09', 'cid08', 'emp08', 2, NULL, '2024-06-10', '2024-06-20', 'Bole', 'Adama', NULL, 'iid08'),
('rid09', 'caid10', 'cid09', 'emp09', 2, NULL, '2024-06-11', '2024-06-21', 'Adama', 'Bahir Dar', NULL, 'iid09'),
('rid10', 'caid11', 'cid10', 'emp10', 2, NULL, '2024-06-12', '2024-06-22', 'Bole', 'Adama', NULL, 'iid10'),
('rid11', 'caid12', 'cid01', 'emp01', 1, NULL, '2024-06-25', '2024-07-05', 'Bole', 'Bole', NULL, 'iid11'),
('rid12', 'caid13', 'cid02', 'emp04', 1, NULL, '2024-06-26', '2024-07-06', 'Adama', 'Bahir Dar', NULL, 'iid12');
-- Populating Reservation Table
-- Rented (Full Payment, Collateral Provided)


-- Scheduled (ቀብድ paid, No collateral)

-- Populating Collateral Table
INSERT INTO Collateral (Collateral_Id, Customer_Id, Car_Id, Reservation_Id, Collateral_Type, Amount, Date_Received) 
VALUES 
('col01', 'cid01', 'caid01', 'rid01', 'Cash', 500.00, '2024-06-01'),
('col02', 'cid02', 'caid02', 'rid02', 'ID Document', 0.00, '2024-06-05'),
('col03', 'cid03', 'caid04', 'rid03', 'Credit Card Hold', 1000.00, '2024-06-03'),
('col04', 'cid04', 'caid05', 'rid04', 'Cash', 700.00, '2024-06-06'),
('col05', 'cid05', 'caid06', 'rid05', 'Home Land Map', 0.00, '2024-06-07'),
('col06', 'cid06', 'caid07', 'rid06', 'Credit Card Hold', 1200.00, '2024-06-08'),
('col07', 'cid07', 'caid08', 'rid07', 'Cash', 900.00, '2024-06-09'),
('col08', 'cid08', 'caid09', 'rid08', 'Business License', 0.00, '2024-06-10'),
('col09', 'cid09', 'caid10', 'rid09', 'Credit Card Hold', 1500.00, '2024-06-11'),
('col10', 'cid10', 'caid11', 'rid10', 'Cash', 800.00, '2024-06-12');

--Updating the reservation table after populating the collateral table 

UPDATE reserve
SET reserve.Collateral_Id = c.Collateral_Id
FROM Reservation reserve
INNER JOIN Collateral c ON reserve.Reservation_Id = c.Reservation_Id
WHERE reserve.Collateral_Id IS NULL;

--***********************----

-- Populating Payment Table
-- Populating Payment Table
INSERT INTO Payment (Payment_Id, Reservation_Id, Price_Per_Car, No_Cars, Payment_Type, Total_Price, Payment_Method, Payment_Date)
VALUES 
('pid01', 'rid01', 500.00, 1, 'Full', 500.00, 'Card', '2024-06-01'),
('pid02', 'rid02', 1000.00, 1, 'Full', 1000.00, 'Card', '2024-06-05'),
('pid03', 'rid03', 1200.00, 1, 'Full', 1200.00, 'Card', '2024-06-03'),
('pid04', 'rid04', 700.00, 1, 'Full', 700.00, 'Card', '2024-06-06'),
('pid05', 'rid05', 900.00, 1, 'Full', 900.00, 'Card', '2024-06-07'),
('pid06', 'rid06', 1100.00, 1, 'Full', 1100.00, 'Card', '2024-06-08'),
('pid07', 'rid07', 800.00, 1, 'Full', 800.00, 'Card', '2024-06-09'),
('pid08', 'rid08', 1300.00, 1, 'Full', 1300.00, 'Card', '2024-06-10'),
('pid09', 'rid09', 1500.00, 1, 'Full', 1500.00, 'Card', '2024-06-11'),
('pid10', 'rid10', 900.00, 1, 'Full', 900.00, 'Card', '2024-06-12'),
('pid11', 'rid11', 800.00, 1, 'Partial', 800.00, 'Card', '2024-06-25'),
('pid12', 'rid12', 1000.00, 1, 'Partial', 1000.00, 'Card', '2024-06-26');

--Updating the reservation table after populating the payment table 

UPDATE reserve
SET reserve.Payment_Id = p.Payment_Id
FROM Reservation reserve
INNER JOIN Payment p ON reserve.Reservation_Id = p.Reservation_Id
WHERE reserve.Payment_Id IS NULL;

--***********************----


-- Populating Car_History Table
INSERT INTO Car_History (History_Id, Car_Id, Description, Record_Type) 
VALUES 
('hid01', 'caid01', 'Regular Maintenance', 'Maintenance'),
('hid02', 'caid02', 'Minor Accident', 'Damage'),
('hid03', 'caid03', 'Annual Service', 'Service');


-- View that shows which branch an employee works at
CREATE VIEW Employee_Branch AS
SELECT 
    Employee.Emp_id AS EmployeeID,
    CONCAT(Employee.E_F_name, ' ', Employee.E_L_name) AS FullName,
    Rental_Branch.B_Region AS Region,
    Rental_Branch.B_Zone AS Zone,
    Employee.E_role AS Role
FROM 
    Employee
JOIN 
    Rental_Branch ON Employee.Branch_id = Rental_Branch.Branch_id;

-- View that shows car reservation status
CREATE VIEW Customer_Reservation_Status AS
SELECT
    C.Customer_Id,
    CONCAT(C.C_FName, ' ', C.C_LName) AS CustomerName,
    R.Reservation_Id,
    R.PickUp_Date,
    R.Dropoff_Date,
    R.Pickup_Location,
    R.Dropoff_Location,
    P.Total_Price AS TotalPaid,
    R.Reservation_Status,
    Ca.Model AS CarModel,
    Ca.Plate_No AS CarPlateNumber
FROM 
    Customer C
JOIN 
    Reservation R ON C.Customer_Id = R.Customer_Id
JOIN 
    Payment P ON R.Payment_Id = P.Payment_Id
JOIN 
    Car Ca ON R.Car_Id = Ca.Car_Id;

-- View that shows the revenue from car rentals
CREATE VIEW Total_Revenue_Summary AS 
SELECT 
    Payment_Type AS PaymentType,
    COUNT(Payment_Id) AS NumberOfPayments,
    SUM(Total_Price) AS TotalRevenue,
    AVG(Total_Price) AS AverageRevenuePerPayment
FROM 
    Payment
GROUP BY 
    Payment_Type;

-- Drop old revenue summary view

-- Revenue summary for a specific branch
CREATE VIEW Branch_Revenue_Summary AS
SELECT
    RB.Branch_id,
    RB.B_Region AS BranchRegion,
    RB.B_Zone AS BranchZone,
    SUM(P.Total_Price) AS TotalRevenue
FROM 
    Rental_Branch RB
JOIN 
    Car Ca ON RB.Branch_id = Ca.Branch_Id
JOIN 
    Reservation R ON Ca.Car_Id = R.Car_Id
JOIN 
    Payment P ON R.Payment_Id = P.Payment_Id
GROUP BY 
    RB.Branch_id, RB.B_Region, RB.B_Zone;

-- View of customers' reservation history
CREATE VIEW Customer_Reservation_History AS
SELECT 
    Customer.Customer_Id,
    CONCAT(Customer.C_FName, ' ', Customer.C_LName) AS CustomerName,
    Reservation.Reservation_Id,
    Reservation.Emp_Id,
    Reservation.PickUp_Date,
    Reservation.Dropoff_Date,
    Reservation.Pickup_Location,
    Reservation.Dropoff_Location,
    Payment.Total_Price AS TotalPaid,
    Payment.Payment_Method,
    Reservation.Reservation_Status
FROM 
    Customer
JOIN 
    Reservation ON Customer.Customer_Id = Reservation.Customer_Id
LEFT JOIN 
    Payment ON Reservation.Reservation_Id = Payment.Reservation_Id;

-- View that shows the expiration date of car insurance
CREATE VIEW Insurance_Expiry AS
SELECT 
    Car.Car_Id,
    Car.Model AS CarModel,
    Insurance.Insurance_Provider,
    Insurance.Policy_Number,
    Insurance.I_Expiry_Date AS ExpirationDate
FROM 
    Car
JOIN 
    Insurance ON Car.Car_Id = Insurance.Car_Id;

-- View for employee performance report
CREATE VIEW Employee_Performance_Report AS
SELECT
    E.Branch_id,
    E.Emp_id,
    CONCAT(E.E_F_name, ' ', E.E_L_name) AS Employee_Name,
    COUNT(R.Reservation_Id) AS NumberOfReservations,
    SUM(P.Total_Price) AS TotalRevenue
FROM 
    Employee E
JOIN 
    Reservation R ON R.Emp_Id = E.Emp_id
JOIN 
    Payment P ON R.Reservation_Id = P.Reservation_Id
GROUP BY 
    E.Branch_id, E.Emp_id, E.E_F_name, E.E_L_name;

-- View for car report
CREATE VIEW Car_Report AS 
SELECT 
    C.Branch_Id,
    C.Car_Id,
    CH.History_Id,
    C.IsAvailable,
    C.Model,
    C.Plate_No,
    CH.Description,
    CH.Record_Type
FROM 
    Car C
LEFT JOIN 
    Car_History CH ON CH.Car_Id = C.Car_Id;

-- Drop old car report
DROP VIEW Car_Report;




SELECT * FROM Employee;
SELECT * FROM Customer;
SELECT * FROM Car;
SELECT * FROM Insurance;
SELECT * FROM Collateral;
SELECT * FROM Reservation;
SELECT * FROM Payment;
SELECT * FROM Car_History;



-- Select for the views Of Reports 
SELECT * FROM Employee_Branch;
SELECT * FROM Customer_Reservation_Status;
SELECT * FROM Total_Revenue_Summary;
SELECT * FROM Customer_Reservation_History;
SELECT * FROM Insurance_Expiry;
SELECT * FROM Branch_Revenue_Summary;
SELECT * FROM Employee_Performance_Report;
SELECT * FROM car_Report;



-- ==========================================================
-- FUNCTION: Search for Customer by ID
-- ==========================================================
CREATE FUNCTION Customer_Info(@Cid VARCHAR(5))
RETURNS TABLE
AS
RETURN
    SELECT * 
    FROM Customer
    WHERE Customer_Id = @Cid;

-- Test the function
SELECT * FROM Customer_Info('cid01');  -- Replace 'cid01' with a valid Customer ID

-- ==========================================================
-- FUNCTION: Search for Employee by Name
-- ==========================================================
CREATE FUNCTION Search_EmployeeByName(@Name VARCHAR(100))
RETURNS TABLE
AS
RETURN
    SELECT * 
    FROM Employee
    WHERE E_F_name LIKE '%' + @Name + '%'
       OR E_L_name LIKE '%' + @Name + '%';

-- Test the function
SELECT * FROM Search_EmployeeByName('John');  -- Replace 'John' with a valid name

-- ==========================================================
-- FUNCTION: Search for Employee by ID
-- ==========================================================
CREATE FUNCTION Search_EmployeeById(@Id VARCHAR(100))
RETURNS TABLE
AS
RETURN
    SELECT * 
    FROM Employee
    WHERE Emp_id = @Id;

-- Test the function
SELECT * FROM Search_EmployeeById('emp01');  -- Replace 'emp01' with a valid Employee ID

-- ==========================================================
-- FUNCTION: Search for Car by ID
-- ==========================================================
CREATE FUNCTION Car_Info(@Sid VARCHAR(5))
RETURNS TABLE
AS
RETURN
    SELECT * 
    FROM car_Report c
    WHERE c.car_id = @Sid;

-- Test the function
SELECT * FROM Car_Info('caid02');  -- Replace 'caid02' with a valid Car ID

-- ==========================================================
-- FUNCTION: Search for Reservation by ID
-- ==========================================================
CREATE FUNCTION Search_ReservationById(@ReservationId VARCHAR(20))
RETURNS TABLE
AS
RETURN
    SELECT * 
    FROM Reservation
    WHERE Reservation_Id = @ReservationId;

-- Test the function
SELECT * FROM Search_ReservationById('rid01');  -- Replace 'rid01' with a valid Reservation ID

-- ==========================================================
-- FUNCTION: Search for Car History by Car ID
-- ==========================================================
CREATE FUNCTION Search_CarHistoryById(@CarId VARCHAR(5))
RETURNS TABLE
AS
RETURN
    SELECT * 
    FROM Car_History
    WHERE Car_Id = @CarId;

-- Test the function
SELECT * FROM Search_CarHistoryById('caid03');  -- Replace 'caid02' with a valid Car ID


