package ser322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData; 
import java.sql.SQLException;

public class ConnectDB{

    protected Connection conn;
    protected ResultSet rs;
    protected ResultSet xs;
    protected ResultSetMetaData rsmd;
	protected Statement stmt;
    protected PreparedStatement st;
    protected DatabaseMetaData md;

    protected ConnectDB(){
	    Connection conn = null;
        ResultSet rs = null;
        ResultSet xs = null;
        ResultSetMetaData rsmd = null;
	    Statement stmt = null;
        PreparedStatement st = null;
        DatabaseMetaData md = null;
    }

    public Connection getConnection(String url, String user_name, 
    String password, String driver){

        try {
	    	// Step 1: Load the JDBC driver
	    	Class.forName(driver);
	    	// Step 2: make a connection
	    	conn = DriverManager.getConnection(url, user_name, password);

	    }catch (Exception exc){
	    	exc.printStackTrace();
	    }
        return conn;
    }

    public void closeConnection(){
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
    }
}