package ser322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData; 
import java.sql.SQLException;
import java.io.File;
import java.io.FileWriter;  
import java.io.IOException;
import java.text.SimpleDateFormat;  
import java.util.Date;


public class Customer {

    //TODO: Add in a "Did you mean?" feature if time
    public static void search(Connection conn, PreparedStatement st, ResultSet rs, String name){

        String query = "SELECT DEVICES.Name, DEVICES.Stock, SECTION.Name" 
        + " FROM DEVICES, SECTION"
        + " WHERE DEVICES.SectionID = SECTION.SectionID and DEVICES.Name = ?;";
        
        try{
	        st = conn.prepareStatement(query);
            st.setString(1, name);
	        rs = st.executeQuery();

            System.out.print("Device Name\t");
            System.out.print("Stock\t");
            System.out.println("Section\t");
	        while (rs.next()) {
                if(rs.getString(1).equals(name)){
	        	    System.out.print(rs.getString(1) + "\t");
	        	    System.out.print(rs.getInt(2) + "\t");
                    System.out.println(rs.getString(3) + "\t");
                }
	        }   
            return; 
        }catch(Exception exc){
            System.out.println("query 2 is not currently working");
            exc.printStackTrace();
        }
    }

    //TODO: Method for listing all items in a section
    public static void comprises(Connection conn, PreparedStatement st, ResultSet rs, String section_name){

        String query = "SELECT SECTION.Name, DEVICES.Name, DEVICES.Stock FROM DEVICES, SECTION WHERE SECTION.SectionID = DEVICES.SectionID and SECTION.Name = ?;";
        
        try{
	        st = conn.prepareStatement(query);
            st.setString(1, section_name);
	        rs = st.executeQuery();

            System.out.print("Device Name\t\t");
            System.out.println("Stock\t");
	        while (rs.next()) {
                if(rs.getString(1).equals(section_name)){
	        	    System.out.print(rs.getString(2) + "\t\t");
                    System.out.println(rs.getInt(3) + "\t");
                }
	        }   
            return; 
        }catch(Exception exc){
            System.out.println("comprises is not currently working"); //TODO: clean
            exc.printStackTrace();
        }
    }


    //TODO: Method for signing up
    public static void signUp(Connection conn, PreparedStatement st, ResultSet rs, String email,
    long phone_number, String first_name, char middle_initial, String last_name, String street,
    String city, String state, int zip_code ) throws SQLException{
        
        conn.setAutoCommit(false);

        String query = "INSERT INTO CUSTOMER ("
        + " Email,"
        + " PhoneNumber,"
        + " FirstName,"
        + " MiddleInitial,"
        + " LastName,"
        + " Street,"
        + " City,"
        + " State,"
        + " ZipCode) VALUES ("
        + "?, ?, ?, ?, ?, ? ,?, ?, ?)";

        try{
	        st = conn.prepareStatement(query);
            st.setString(1, email);
            st.setString(2, "" + phone_number);
            st.setString(3, first_name);
            st.setString(4, "" + middle_initial);
            st.setString(5, last_name);
            st.setString(6, street);
            st.setString(7, city);
            st.setString(8, state);
            st.setString(9, "" + zip_code);
	        int result = st.executeUpdate();

            if(result != -1){
                System.out.println("SUCCESS");
            }else{
                System.out.println("FAILURE");
            }

            conn.commit();
            return; 
        }catch(Exception exc){
            System.out.println("signUp is not currently working");
            exc.printStackTrace();
        }
    }

    //TODO: Method for inserting and viewing an order
    public static void order(Connection conn, PreparedStatement st, ResultSet rs, long order_number, String date,
    long total, long employee_id, String email, long item_number ) throws SQLException{
        
        conn.setAutoCommit(false);

        String query = "INSERT INTO ORDERS ("
        + " OrderNumber,"
        + " Date,"
        + " Total,"
        + " EmployeeID,"
        + " Email,"
        + " ItemNumber) VALUES ("
        + "?, ?, ?, ?, ?, ?)";

        try{
	        st = conn.prepareStatement(query);
            st.setString(1, "" + order_number);
            st.setString(2, date);
            st.setString(3, "" + total);
            st.setString(4, "" + employee_id);
            st.setString(5, email);
            st.setString(6, "" + item_number);
	        int result = st.executeUpdate();

            if(result != -1){
                System.out.println("SUCCESS");
            }else{
                System.out.println("FAILURE");
            }

            conn.commit();
            return; 
        }catch(Exception exc){
            System.out.println("order is not currently working");
            exc.printStackTrace();
        }
    }

    //TODO: Method updating device

    //TODO: Method update customer information

    //TODO: Method update order

    //TODO: Method delete device

    //TODO: Method delete customer

    //TODO: Method delete order
    
	public static void main(String[] args){

		Connection conn = null;
        ResultSet rs = null;
        ResultSet xs = null;
        ResultSetMetaData rsmd = null;
	    Statement stmt = null;
        PreparedStatement st = null;
        DatabaseMetaData md = null;

		String _url = args[0];
		try {
			// Step 1: Load the JDBC driver
			Class.forName(args[3]);

			// Step 2: make a connection
			conn = DriverManager.getConnection(_url, args[1], args[2]);

            if(args[4].equals("search") && args.length > 5){
                try{
                    search(conn,st,rs,args[5]);
                }catch(Exception e){
                    System.out.println("Error message here"); //TODO: parse input
                }
            }else if(args[4].equals("comprises") && args.length > 5){
                try{
                    comprises(conn,st,rs,args[5]);
                }catch(Exception e){
                    System.out.println("Error message here"); //TODO: parse input
                }
            }else if(args[4].equals("signUp") && args.length > 5){
                try{
                    signUp(conn,st,rs,args[5],Long.parseLong(args[6]),args[7],
                    args[8].charAt(0),args[9],args[10], args[11], args[12], Integer.parseInt(args[13]));
                }catch(Exception e){
                    System.out.println("Error message here"); //TODO: parse input
                }
            }else if(args[4].equals("order") && args.length > 5){ //TODO: check date...with perhaps regex. Date class doesnt work
                try{  
                    order(conn,st,rs,Long.parseLong(args[5]),args[6],Long.parseLong(args[7]),
                    Long.parseLong(args[8]),args[9],Long.parseLong(args[10]));
                }catch(Exception e){
                    System.out.println("Error message here"); //TODO: parse input
                    e.printStackTrace();
                }
            
            }else{
                System.out.println("Inform user how to use this here"); //Fill this out here
                System.exit(0);
            }
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		finally {  // ALWAYS clean up your DB resources
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (st != null)
					stmt.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
    }
}
