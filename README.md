# pharmaceuaticalDatabseSystem

#1. Project Overview

The healthFirst Pharmacy inventory Management System (PIMS) is a java desktop application developed to assist a pharmacy with managing medicine,
suppliers, users, inventoy and sales transaction. The system provides separates access for Administrators and Cahsier through a role-based login.
The application uses:
java
Java Swing
MYSQL
JDBC
DAO Design Pattern

------------------

#2. System Features
First lets discuss the Administrators dashboard. The Administrators dashboard provides access to the following functions:

2.1 Manage Medicine
Add new medicine
View Medicine
Update Medicine information
Delete medicine
Manage medicine quantities
Set reorder levels
store expirry dates
Assign supplier to medicine

2.2 Manage supplier:
Administrators can:
Add suppliers
View supplier information
Update supplier information
Delete suppliers

2.3 Manage users
Administarors can manage systems users and their roles
the system then supports the following roles:
Admin and Cashier

2.4 Reports
The administrators dashboard includes the report section for viewing pharmacy related information

----------------------------

#3. Cashier Features
Point of Sale 
the point of sale module allows the cashier to process medicine purchases.
the cashier can perform  multiple tasks:
Enter a medicine ID
Searching for medicine name
View for the medicine
Check available stock
Enter the quantity required
Add the medicine to the cart
Remove items from the cart
Clear the cart
Complete the checkout process

When checkout is completed:
A sale is recorded in the database
Sale items are recorded
The total amount is calculated
Medicine stock is updated
The cashier responsible for the transaction is recorded

---------------------

#4. Stock Check
The Stock Check module allows the cashier to view available medicines.
The cashier can:
View all medicines.
Search for medicines.
View medicine prices.
Check available stock quantities.
View reorder levels.
View expiry dates.
Refresh the medicine list.

----------------------

#5. Billing
The Billing module displays completed pharmacy transactions.
The cashier can view:
Sale ID
Sale date
Cashier name
Total sale amount
The Refresh button can be used to load the latest transaction history.

-----------------------------

#6. System Requirements
The following software is required to run the application:
Java Development Kit (JDK) 17 or later
MySQL Server
MySQL Workbench
MySQL Connector/J
Java IDE such as Visual Studio Code

-----------------------------

#7. Database Connection
The application connects to MySQL using the DatabaseConnection.java class.
Example connection structure:


private static final String URL =
        "jdbc:mysql://localhost:3306/healthfirst_pims";
private static final String USER = "root";
private static final String PASSWORD =
        "#######";//I will not disclose my password

---------------------------------

#8. How to Run the Application
Follow these steps:
Step 1: Start MySQL
Ensure that the MySQL Server is running.

Step 2: Create the Database
Run the provided SQL scripts to create:
The database
Tables
Relationships
Sample users
Sample suppliers
Sample medicines

Step 3: Configure Database Connection
Open:
DatabaseConnection.java
Update the database connection details if necessary.

Step 4: Add MySQL Connector/J
Ensure the MySQL Connector/J JAR file is included in the Java project libraries.

Step 5: Run the Application
Run
PIMSApplication.java
The login window should appear.
