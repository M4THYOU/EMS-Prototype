package com.jambalayasystems.main;
//Connecting to DB and general needs.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Getting table names
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.ArrayList;

/**Represents the system's administrator access to the database. Total free access.
 * 
 * @author Matthew Wolfe
 * @version 1.0
 * @since 1.0
 *
 */
public class DBAccess {

	/**The current database connection*/
	static Connection conn = null;
	
	
	
	
	//STATIC METHODS
	//Connects to database.
	/** Create initial connection to the database and sets the static property {@link #conn}.
	 * <p>
	 * Gets the string location of the database. and prefixes it with "jdbc:sqlite:". Then calls {@link DriverManager#getConnection(String)}.
	 * 
	 * @param db_file_location - the absolute location of the database file.
	 * @return The newly created {@link Connection} instance.
	 */
	static Connection connect(String db_file_location) {
		conn = null;
		
		try {
			String url = "jdbc:sqlite:" + db_file_location;
			
			conn = DriverManager.getConnection(url);
			System.out.println("Connected to " + url + "\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return conn;
	}
	
	/**An overloaded {@link #connect(String)} method. For use by {@link CompanyAccess}.
	 * <p>
	 * Gets the string location of the database. and prefixes it with "jdbc:sqlite:". Then calls {@link DriverManager#getConnection(String)}.
	 * <br>Additionally, this method prints out which company this connection has been made for.
	 * 
	 * @param db_file_location - the absolute location of the database file.
	 * @param company - the name of the company making the connection.
	 * @return The newly created {@link Connection} instance.
	 */
	static Connection connect(String db_file_location, String company) {
		conn = null;
		
		try {
			String url = "jdbc:sqlite:" + db_file_location;
			
			conn = DriverManager.getConnection(url);
			System.out.println("COMPANY ACCESS - [" + company + "] Connected to " + url + "\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return conn;
	}
	
	/**Disconnects from the database if a connection exists.*/
	static public void disconnect() {
		try {
			if (conn != null) {
				conn.close();
				System.out.println("Disconnected.\n");
			} else {
				System.out.println("No database to disconnect from.\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	/**Add a new company to the database.
	 * <p>
	 * First, this method creates a new {@link ArrayList} from {@link DBAccess#getCompanies()} which holds all companies in the database.<br>
	 * The ArrayList is then iterated over to see if the new company name already matches any existing one. If so, an <br>
	 * {@link IllegalArgumentException} is thrown. If there is no matching company, the method continues.
	 * <p>
	 * A new company table is added to the SQLite database. The columns are as follows:<br>
	 * <b>id</b> - the primary key; identifies each individual employee. Type: integer<br>
	 * <b>firstName</b> - the employee's first name. Type: varchar(255)<br>
	 * <b>lastName</b> - the employee's last name. Type: varchar(255)<br>
	 * <b>position</b> - the employee's job titel at the company. Type: varchar(255)<br>
	 * <b>isManager</b> - whether or not the employee is a manager. Type: char(1), represents a boolean.<br>
	 * <p>
	 * The statement is then executed with {@link Statement#execute(String)}.
	 * 
	 * @param name - the String name of the company.
	 */
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
			System.out.println(name + " successfully inserted.\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No connection found.\n");
		}
		
	}
	
	/**Returns an {@link ArrayList} of every company in the database.
	 * <p>
	 * First, this method creates an empty ArrayList of Strings. The database metadata is then accessed, which contains each table name.<br>
	 * Each table name, which is also a company name, is added to the ArrayList.
	 * 
	 * @return The ArrayList of Strings containing all of the company names in the database.
	 */
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
	
	/**Counts the number of employees entered into a company's table.
	 * 
	 * @param company - the String name of the company.
	 * @return An int representing the number of employees in a company's table.
	 */
	public static int countEmployees(String company) {
		String sql = "SELECT count(*) FROM [" + company + "]";
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		
		
	}
	
}


//sqlite> create table tbl1(one varchar(10), two smallint); -->creates a table. Takes name of column and datatype (in a pair )as args
//sqlite> insert into tbl1 values('hello!',10);				-->adds new entry into database
//sqlite> insert into tbl1 values('goodbye', 20);			-->adds new entry into database

//Booleans are stored as 0 (False) or 1 (True)




