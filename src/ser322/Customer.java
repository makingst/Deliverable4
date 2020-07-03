package ser322;

import ser322.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData; 
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Customer {

    public ArrayList<String> devices = new ArrayList<String>();
    public ArrayList<String> stocks = new ArrayList<String>();
    public ArrayList<String> sections = new ArrayList<String>();


    public JSONObject search(String name){

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        JSONObject obj = new JSONObject();

        String query = "SELECT DEVICES.Name, DEVICES.Stock, SECTION.Name" 
        + " FROM DEVICES, SECTION"
        + " WHERE DEVICES.SectionID = SECTION.SectionID and DEVICES.Name = ?;";
        
        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, name);
	        db.rs = db.st.executeQuery();

	        while (db.rs.next()) {
                if(db.rs.getString(1).equals(name)){
                   devices.add(db.rs.getString(1));
                    stocks.add("" + db.rs.getInt(2));
                    sections.add(db.rs.getString(3));
                }
	        }
            
            obj.put("DEVICES.Name", devices.toString());
            obj.put("DEVICES.Stock", stocks.toString());
            obj.put("SECTION.Name", sections.toString());

            System.out.println(obj.toJSONString());

            devices.clear();
            stocks.clear();
            sections.clear();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("query 2 is not currently working");
            exc.printStackTrace();
            db.closeConnection();  
            System.exit(0); 
        }

        return obj;
    }

    public JSONObject comprises(String section_name){

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        JSONObject obj = new JSONObject();

        String query = "SELECT SECTION.Name, DEVICES.Name, DEVICES.Stock FROM DEVICES, SECTION WHERE SECTION.SectionID = DEVICES.SectionID and SECTION.Name = ?;";
        
        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, section_name);
	        db.rs = db.st.executeQuery();

	        while (db.rs.next()) {
                if(db.rs.getString(1).equals(section_name)){
                   devices.add(db.rs.getString(2));
                    stocks.add("" + db.rs.getInt(3));
                }
	        }   

            obj.put("DEVICES.Name", devices.toString());
            obj.put("DEVICES.Stock", stocks.toString());

            System.out.println(obj.toJSONString());

            devices.clear();
            stocks.clear();
 
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("comprises is not currently working"); //TODO: clean
            exc.printStackTrace();
            db.closeConnection();   
            System.exit(0);
        }

        return obj;
    }

    public String signUp(String email,
    long phone_number, String first_name, char middle_initial, String last_name, String street,
    String city, String state, int zip_code ) throws SQLException{
        
        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        String res = "Null";
       
        db.conn.setAutoCommit(false);

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
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, email);
            db.st.setString(2, "" + phone_number);
            db.st.setString(3, first_name);
            db.st.setString(4, "" + middle_initial);
            db.st.setString(5, last_name);
            db.st.setString(6, street);
            db.st.setString(7, city);
            db.st.setString(8, state);
            db.st.setString(9, "" + zip_code);
	        int result = db.st.executeUpdate();

            if(result != -1){
                res = "SUCCESS";
                System.out.println(res);
            }else{
                res = "FAILURE";
                System.out.println(res);
            }

            db.conn.commit();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("signUp is not currently working");
            exc.printStackTrace();
            db.closeConnection();   
        }

        return res;
   }

    public String order(long order_number, String date,
    long total, long employee_id, String email, long item_number ) throws SQLException{

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        String res = "Null";
        
        db.conn.setAutoCommit(false);

        String query = "INSERT INTO ORDERS ("
        + " OrderNumber,"
        + " Date,"
        + " Total,"
        + " EmployeeID,"
        + " Email,"
        + " ItemNumber) VALUES ("
        + "?, ?, ?, ?, ?, ?)";

        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, "" + order_number);
            db.st.setString(2, date);
            db.st.setString(3, "" + total);
            db.st.setString(4, "" + employee_id);
            db.st.setString(5, email);
            db.st.setString(6, "" + item_number);
	        int result = db.st.executeUpdate();

            if(result != -1){
                res = "SUCCESS";
                System.out.println(res);
            }else{
                res = "FAILURE";
                System.out.println(res);
            }

            db.conn.commit();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("order is not currently working");
            exc.printStackTrace();
            db.closeConnection();   
        }

        return res;
    }

    /*updating a customers email is a bit complicated since 
    its a primary key that is refered to pretty often
    therefore we should change it if we have time,but until then an "updated" email
    should be treated as in insert*/
    public String updateCustomer(String email,
    long phone_number, String first_name, char middle_initial, String last_name, String street,
    String city, String state, int zip_code ) throws SQLException{

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        String res = "Null";
        
        db.conn.setAutoCommit(false);

        String query = "UPDATE CUSTOMER SET"
        + " PhoneNumber = ?,"
        + " FirstName = ?,"
        + " MiddleInitial = ?,"
        + " LastName = ?,"
        + " Street = ?,"
        + " City = ?,"
        + " State = ?,"
        + " ZipCode = ? WHERE Email = ?;";

        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, "" + phone_number);
            db.st.setString(2, first_name);
            db.st.setString(3, "" + middle_initial);
            db.st.setString(4, last_name);
            db.st.setString(5, street);
            db.st.setString(6, city);
            db.st.setString(7, state);
            db.st.setString(8, "" + zip_code);
            db.st.setString(9, email);
	        int result = db.st.executeUpdate();

            if(result != -1){
                res = "SUCCESS";
                System.out.println(res);
            }else{
                res = "FAILURE";
                System.out.println(res);
            }

            db.conn.commit();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("updateCustomer is not currently working");
            exc.printStackTrace();
            db.closeConnection();   
        }

        return res;
    }


    public String updateOrder(long order_number, String date,
    long total, long employee_id, String email, long item_number ) throws SQLException{

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        String res = "Null";

        db.conn.setAutoCommit(false);

        String query = "UPDATE ORDERS SET"
        + " Date = ?,"
        + " Total = ?,"
        + " EmployeeID = ?,"
        + " Email = ?,"
        + " ItemNumber = ? WHERE OrderNumber = ?;";

        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, date);
            db.st.setString(2, "" + total);
            db.st.setString(3, "" + employee_id);
            db.st.setString(4, email);
            db.st.setString(5, "" + item_number);
            db.st.setString(6, "" + order_number);
	        int result = db.st.executeUpdate();

            if(result != -1){
                res = "SUCCESS";
                System.out.println(res);
            }else{
                res = "FAILURE";
                System.out.println(res);
            }

            db.conn.commit();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("updateOrder is not currently working");
            exc.printStackTrace();
            db.closeConnection();   
        }

        return res;
    }


    //This will also delete the customers order...perhaps give user a warning
    public String deleteCustomer(String email) throws SQLException{

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        String res = "Null";
        
        db.conn.setAutoCommit(false);

        String query = " DELETE FROM CUSTOMER WHERE Email = ?;";

        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, email);
	        int result = db.st.executeUpdate();

            if(result != -1){
                res = "SUCCESS";
                System.out.println(res);
            }else{
                System.out.println(res);
            }

            db.conn.commit();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("deleteCustomer is not currently working");
            exc.printStackTrace();
            db.closeConnection();   
        }

        return res;
    }

    public String deleteOrder(String order_number) throws SQLException{

        ConnectDB db = new ConnectDB();
        db.getConnection("jdbc:mysql://localhost/ELECTRONICS_STORE", "root", "root", "com.mysql.jdbc.Driver");
        String res = "Null";
        
        db.conn.setAutoCommit(false);

        String query = " DELETE FROM ORDERS WHERE OrderNumber = ?;";

        try{
	        db.st = db.conn.prepareStatement(query);
            db.st.setString(1, order_number);
	        int result = db.st.executeUpdate();

            if(result != -1){
                res = "SUCCESS";
                System.out.println(res);
            }else{
                System.out.println(res);
            }

            db.conn.commit();
            db.closeConnection();   
        }catch(Exception exc){
            System.out.println("deleteOrder is not currently working");
            exc.printStackTrace();
            db.closeConnection();   
        }

        return res;
    }
    
	public static void main(String[] args){

        Customer customer = new Customer();

        if(args[0].equals("search")){
            try{
                customer.search(args[1]);
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
                e.printStackTrace();
            }
        }else if(args[0].equals("comprises")){
            try{
                customer.comprises(args[1]);
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
            }
        }else if(args[0].equals("signUp") && args.length > 5){
            try{
                customer.signUp(args[1],Long.parseLong(args[2]),args[3],
                args[4].charAt(0),args[5],args[6], args[7], args[8], Integer.parseInt(args[9]));
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
            }
        }else if(args[0].equals("order")){ //TODO: check date...with perhaps regex. Date class doesnt work
            try{  
                customer.order(Long.parseLong(args[1]),args[2],Long.parseLong(args[3]),
                Long.parseLong(args[4]),args[5],Long.parseLong(args[6]));
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
                e.printStackTrace();
            }
        }else if(args[0].equals("updateCustomer")){
            try{
                customer.updateCustomer(args[1],Long.parseLong(args[2]),args[3],
                args[4].charAt(0),args[5],args[6], args[7], args[8], Integer.parseInt(args[9]));
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
            }
        }else if(args[0].equals("updateOrder")){ //TODO: check date...with perhaps regex. Date class doesnt work
            try{  
                customer.updateOrder(Long.parseLong(args[1]),args[2],Long.parseLong(args[3]),
                Long.parseLong(args[4]),args[5],Long.parseLong(args[6]));
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
                e.printStackTrace();
            }
        }else if(args[0].equals("deleteCustomer")){
            try{
                customer.deleteCustomer(args[1]);
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
            }
        }else if(args[0].equals("deleteOrder")){
            try{
                customer.deleteOrder(args[1]);
            }catch(Exception e){
                System.out.println("Error message here"); //TODO: parse input
            }
        }else{
            System.out.println("Inform user how to use this here"); //TODO:Fill this out here
            System.exit(0);
        }
    }
}

