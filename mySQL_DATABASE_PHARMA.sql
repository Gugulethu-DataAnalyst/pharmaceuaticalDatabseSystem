/*Creating a database for the assignment*/
CREATE DATABASE healthfirst_pims;

/*Validating the existance of database*/
SHOW DATABASES;
SELECT DATABASE();

/*Now using the database to create tables and adding data*/
USE healthfirst_pims;

/*Creating user table*/
CREATE TABLE `userTable`(
	`userID` INT PRIMARY KEY auto_increment,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    role ENUM('Admin','Cashier') NOT NULL,
    `full_name` VARCHAR(100) NOT NULL
);
/*Viewing the table*/
Select * FROM userTable;

Insert Into userTable(`username`,`password`, role,`full_name`)
VALUES
('gugulethud@Pharma','#80christalclear','Admin','Gugulethu Dladla');

SELECT * FROM userTable;

/*Creating table 2: Suppliers*/
CREATE TABLE suppliers(
	`supplier_ID` INT PRIMARY KEY auto_increment,
    `name` VARCHAR(100) NOT NULL,
    `contact_person` VARCHAR(100),
    `phone` VARCHAR(20),
    `email` VARCHAR(100),
    `address` TEXT    
);

SELECT * FROM suppliers;

/*Adding values into the tables for us to have sample data*/
INSERT INTO suppliers
(name, contact_person, phone, email, address)
Values
('Medicare+','Phindile','0723457890','phindile@Medicare.co.za','15 Main Street, Johannesburg'),
('ZAA Pharmaceuticals','Marc Shrute','0831234455','shrute@pharma.co.za','10 Market Street, Pretoria');

/*View the updated tables with new sample data*/
SELECT * FROM suppliers;
describe suppliers;
SHOW tables;
/*So far we have two tables in our database - user table, and the supplier table*/

/*Now the third table will be created, which will be the medicine table which
will store the elements of the medicines, including the supplier ID */

CREATE TABLE medicines(
	`medicine_ID` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL,
    `company` VARCHAR(100) NOT NULL,
    `medicine_type` VARCHAR(50) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `quantity_in_stock` INT NOT NULL,
    `reorder_level` INT NOT NULL,
    `expiry_date` DATE NOT NULL,
    `supplier_id` INT NOT NULL,
    
    /*creating the foreign key*/
    FOREIGN KEY (`supplier_ID`)
		references suppliers(`supplier_ID`)
);

SELECT * FROM medicines;

/*ADDING SAMPLE DATA FOR THE MEDICINE TABLE*/
INSERT INTO medicines
(name, company, medicine_type, price, quantity_in_stock, reorder_level, expiry_date, supplier_ID)
VALUES
('Panado', 'Bayers','Tablets',56.00, 100,20,'2027-09-01',1),
('Aspirin', 'Adcock Ingrams','Tablets', 25.00, 50, 10,'2027-09-01', 1);

INSERT INTO medicines
(name, company, medicine_type, price, quantity_in_stock, reorder_level, expiry_date, supplier_ID)
VALUES
('Cough Syrup', 'BP pharma','syrup',76.00, 15, 8,'2027-08-12',2),
('Vitamin C', 'HealthPlus','Tablets', 195.00, 50, 10,'2027-07-18', 2);


SELECT * FROM medicines;

/*Creating a view of the table that joins the medicine table with the supplier table*/
SELECT
	m.medicine_id,
    m.name,
    m.company,
    m.medicine_type,
    m.price,
    m.quantity_in_sTock,
    m.reorder_level,
    m.expiry_date,
    s.name AS supplier
FROM medicines m
JOIN suppliers s
	ON m.supplier_ID = s.supplier_ID;


/*SALES TABLE*/
CREATE TABLE sales(
	`sale_id` INT PRIMARY KEY  AUTO_INCREMENT,
    `sale_data` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `total_amount` DECIMAL(10,2),
    `userID` INT NOT NULL,
    
    /*Foreign key*/
    FOREIGN KEY(`userID`)
		REFERENCES userTable(userID)
);

SELECT * FROM sales;

/*Creating a sales item table*/
CREATE TABLE sales_Item(
	`sale_item_id` INT PRIMARY KEY AUTO_INCREMENT,
    `sale_id` INT NOT NULL,
    `medicine_id` INT NOT NULL,
    `quantity_sold` INT NOT NULL,
    `price_at_sale` DECIMAL(10,2) NOT NULL,
    
    
    FOREIGN KEY (sale_id)
        REFERENCES sales(sale_id),

    FOREIGN KEY (medicine_id)
        REFERENCES medicines(medicine_id)
);

SELECT * FROM sales_Item;



