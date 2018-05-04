//Connecting to DB and general needs.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Getting table names
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.ArrayList;

public class DBAccess {

	static Connection conn = null;
	
	
	
	
	//STATIC METHODS
	//Connects to database.
	static Connection connect(String db_file_location) {
		conn = null;
		
		try {
			String url = "jdbc:sqlite:" + db_file_location;
			
			conn = DriverManager.getConnection(url);
			System.out.println("Connected to " + url);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return conn;
	}
	
	//Disconnects from database.
	static public void disconnect() {
		try {
			if (conn != null) {
				conn.close();
				System.out.println("Disconnected.");
			} else {
				System.out.println("No database to disconnect from.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	//Add a new company to the database.
	public static void newCompanyTable(String name) {
		
		ArrayList<String> companies = DBAccess.getCompanies();
		
		for (String whichCompany:companies) {
			if (whichCompany.equals(name)) {
				throw new IllegalArgumentException("A company with that name already exists.");
			}
		}
		
		//WATCH OUT FOR SQL INJECTION!!!!!!!!!!!!!!!!!
		String sql = "CREATE TABLE IF NOT EXISTS [" + name + "] (\n"
				+ " id integer PRIMARY KEY,\n"
				+ " firstName varchar(255) NOT NULL,\n"
				+ " lastName varchar(255) NOT NULL,\n"
				+ " position varchar(255) NOT NULL,\n"
				+ " isManager char(1) NOT NULL\n"
				+ " );";
		
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
			System.out.println(name + " successfully inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No connection found.");
		}
		
	}
	
	//Returns an arraylist of every company in the database.
	public static ArrayList<String> getCompanies() {
		ArrayList<String> companies = new ArrayList<String>();
		
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet results = meta.getTables(null, null, "%", null);
			while (results.next()) {
					companies.add(results.getString(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies;
		
	}
	
}


//sqlite> create table tbl1(one varchar(10), two smallint); -->creates a table. Takes name of column and datatype (in a pair )as args
//sqlite> insert into tbl1 values('hello!',10);				-->adds new entry into database
//sqlite> insert into tbl1 values('goodbye', 20);			-->adds new entry into database

//Booleans are stored as 0 (False) or 1 (True)




