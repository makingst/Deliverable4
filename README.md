# Installation Of The Database Enviroment

- For a quick overview of the structure of the database please view "team10_relmodel"
- Open an sql interface and utilize `SOURCE /path/to/team10_create.sql` first
- Then enter the new database created with `USE ELECTRONICS_STORE` and run the script
  `SOURCE /path/to/team10_insert.sql`
- Finally to see the data in the table run the script `SOURCE team10_select.sql` where there should comments describing the simple select statements beig run
-Make sure this is up to date after every new commit

# Usage Of Individual Java Classes

## ConnectDB.java

Helper class to open and close connections to the database

getConnection needs to be followed by
closeConnection at the end of a method

## Customer.java

Compile class:
javac -d classes -cp ./lib/json-simple-1.1.1.jar src/ser322/ConnectDB.java src/ser322/Customer.java

Excute Class: java -cp lib/json-simple-1.1.1.jar:lib/mysql-connector-java-5.1.45-bin.jar:classes ser322.Customer <parameters>

-Methods assume `db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");` currently

-SELECT queries return JSON while all other queries return Strings

-Error messages are vague and not all user data is properly handled
