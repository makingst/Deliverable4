-- Basic SELECT statements for displaying every table
select '-- Basic SELECT statements for displaying every table' AS '';
SELECT * FROM COMPRISES;
SELECT * FROM CUSTOMER;
SELECT * FROM DEVICES;
SELECT * FROM EMPLOYEE;
SELECT * FROM HAS;
SELECT * FROM MANAGES;
SELECT * FROM ORDERS;
SELECT * FROM SECTION;

-- SELECT statement to display all customers without orders
select 'SELECT statement to display all employees without orders' AS '';
SELECT     CUSTOMER.LastName, CUSTOMER.FirstName, CUSTOMER.Email
FROM       CUSTOMER
LEFT JOIN  ORDERS ON CUSTOMER.Email = ORDERS.Email
WHERE      ORDERS.Email IS NULL;

-- SELECT statement to display all customers without orders
select 'SELECT statement to display all employees without orders' AS '';
SELECT     EMPLOYEE.LastName, EMPLOYEE.FirstName, EMPLOYEE.EmployeeID
FROM       EMPLOYEE
LEFT JOIN  ORDERS ON EMPLOYEE.EmployeeID = ORDERS.EmployeeID
WHERE      ORDERS.EmployeeID IS NULL;

-- SELECT statement to display all employees with orders
select 'SELECT statement to display all employees with orders' AS '';
SELECT     EMPLOYEE.LastName, EMPLOYEE.FirstName, EMPLOYEE.EmployeeID
FROM       EMPLOYEE
LEFT JOIN  ORDERS ON EMPLOYEE.EmployeeID = ORDERS.EmployeeID
WHERE      ORDERS.EmployeeID IS NOT NULL;

--SELECT statement to display all items in the Mobile Section and the the stock
select 'SELECT statement to display all items in the Mobile Section and the the stock' AS '';
SELECT DEVICES.Name, DEVICES.Stock, SECTION.Name
FROM   DEVICES, SECTION
INNER JOIN  COMPRISES ON  COMPRISES.SectionID = SECTION.SectionID
WHERE COMPRISES.SectionID = 265;

--SELECT statement to display all items in the Home Section and the the stock
select 'SELECT statement to display all items in the Home Section and the the stock' AS '';
SELECT DEVICES.Name, DEVICES.Stock, SECTION.Name
FROM   DEVICES, SECTION
INNER JOIN  COMPRISES ON  COMPRISES.SectionID = SECTION.SectionID
WHERE COMPRISES.SectionID = 560;

--SELECT statement to display all items in the Gaming Section and the the stock
select 'SELECT statement to display all items in the Gaming Section and the the stock' AS '';
SELECT DEVICES.Name, DEVICES.Stock, SECTION.Name
FROM   DEVICES, SECTION
INNER JOIN  COMPRISES ON  COMPRISES.SectionID = SECTION.SectionID
WHERE COMPRISES.SectionID = 511;

