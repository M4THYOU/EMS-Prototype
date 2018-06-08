package webEMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.shape.Line;

/**Represents an individual company's access to their table in the database.
 * 
 * @author Matthew Wolfe
 * @version 1.0
 * @since 1.0
 *
 */
public class CompanyAccess {
	
	/**The current database connection*/
	Connection conn = null;
	
	/**The instance company's name*/
	String company;
	
	/**Constructs an instance of {@link CompanyAccess}.
	 * <p>
	 * First, this method creates a new {@link ArrayList} from {@link DBAccess#getCompanies()} which holds all companies in the database.<br>
	 * This ArrayList is then iterated over to check if the given company actually exists. If not, an {@link IllegalArgumentException} is<br>
	 * thrown. If there <i>is</i> a matching company, the method continues.
	 * <p>
	 * The {@link #company} property is then set as the given company.
	 * 
	 * @param db_file_location - the absolute location of the database file.
	 * @param company - the name of the company making the connection.
	 * 
	 */
	public CompanyAccess(String db_file_location, String company) {
		this.conn = DBAccess.connect(db_file_location, company);
		
		//Check if this company actually exists.
		boolean noMatch = true;
		ArrayList<String> companies = DBAccess.getCompanies();
		for (String name:companies) {
			if (name.equals(company)) {
				noMatch = false;
			}
		}
		if (noMatch) {
			throw new IllegalArgumentException("No company with that name exists.");
		}
		
		this.company = company;//check if company passed matches one in db.
	}
	
	/**Inserts an employee into the respective company table. To be called by {@link Master#userToDB(com.jambalayasystems.main.Master.Employee, CompanyAccess)}
	 * <p>
	 * First, this method converts the isManager boolean value into the appropriate int value for the database to store. Next, an<br>
	 * ArrayList is created to store every employee in the repective company table using the {@link #getEmployees(String)} method. This<br>
	 * ArrayList is then iterated over to see if an employee with the same first and last name already exists. If so, get user<br>
	 * confirmation to see if they really want to add the user. If not, continue adding the user to the database.
	 * 
	 * @param id - The identification number of the employee.
	 * @param firstName - the employee's first name.
	 * @param lastName - the employee's last name.
	 * @param position - the employee's job title.
	 * @param company - the name of the company the employee works at.
	 * @param isManager - whether or not the employee is also a manager.
	 * 
	 */
	public void insertUser(int id, String firstName, String lastName, String position, boolean isManager) {
		String sql = "INSERT INTO [" + this.company + "](id, firstName, lastName, position, isManager) VALUES(?,?,?,?,?)";
		
		//Get numerical value of isManager
		int manager;
		if (isManager) {
			manager = 1;//if they ARE a manager
		} else {
			manager = 0;//if they ARE NOT a manager
		}
		

		
		
		//Check if user already exists
		ArrayList<String> data = getAllEmployees("fullName,position");
		
		dataLoop: for (String user:data) {
			String[] nameAndPosition = user.split(",");
			String existingName = nameAndPosition[0];
			String existingPosition = nameAndPosition[1];
			
			String newName = firstName + " " + lastName;
			String newPosition = position;
			
			if (newName.equals(existingName)) {
				Scanner keyboard = new Scanner(System.in);
				char confirmContinue = '0';
				
				
				while ((confirmContinue != 'y') || (confirmContinue != 'n')) {
					if (newPosition.equals(existingPosition)) {
						System.out.println("An employee with the same first name(" + firstName +
										   "), last name(" + lastName +
										   "), and position(" + position +
										   ") already exists.");
						System.out.print("Are you sure you want to add another? (y/n): ");
						confirmContinue = keyboard.next().charAt(0);
					} else {
						System.out.println("WARNING - An employee with the same first and last name, "
										   + firstName + " " + lastName + ", already exists.");
						System.out.print("Are you sure you want to continue? (y/n): ");
						confirmContinue = keyboard.next().charAt(0);
					}
					
					
				
					if (confirmContinue == 'y') {
						break dataLoop;
					} else if (confirmContinue == 'n') {
						System.out.println("Employee not added.\n");
						//keyboard.close();
						return;
					} else {
						System.out.println("Invalid input.\n");
					}
				}
				//keyboard.close();
			}
			
		}
		
		
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, position);
			pstmt.setInt(5, manager);
			pstmt.executeUpdate();
			System.out.println(firstName + " " + lastName + " added to [" + this.company + "].\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//THIS SHOULD AUTO GENERATE IDS
	//C:/Users/Student.A219-16/Desktop/matthew_stuff/sqlite/ems"
	
	//# of employees = DBAccess.countEmployees(company); where company is a string.
	//each ID is employees + 1;
	public void insertUsers(String csvFileLocation) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFileLocation));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				int employeeCount = DBAccess.countEmployees(company);

				String[] employee = line.split(",");
				
				int id = employeeCount + 1;
				String firstName = employee[0];
				String lastName = employee[1];
				String position = employee[2];
				Boolean isManager = Boolean.parseBoolean(employee[3].toLowerCase());
				
				insertUser(id, firstName, lastName, position, isManager);
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Returns an ArrayList of Strings with information about every employee in the company. The exact data type of this information<br>
	 * depends on the <i>data</i> specified.
	 * <p>
	 * First, this method creates an empty ArrayList. The value of <i>data</i> is then checked. Whichever value it is determines the<br>
	 * information set added to the (currently empty) returned ArrayList.
	 * 
	 * @param data - A String representing the data set to be returned for each employee. The valid arguments for <i>data</i> are as follows:
	 * <br>
	 * <ul>
	 * 		<li>"<i>id</i>" - the individual identification number of each employee.</li>
	 * 		<li>"<i>fullName</i>" - the firstName and lastName of each employee, separated by a space. For example, "Matthew Wolfe".</li>
	 * 		<li>"<i>firstName</i>" - the first name of each employee.</li>
	 * 		<li>"<i>lastName</i>" - the last name of each employee.</li>
	 * 		<li>"<i>position</i>" - the job title of each employee.</li>
	 * 		<li>"<i>fullName,position</i>" - the fullName and position of each employee,  separated by a comma. For example, 
	 * 										 "Matthew Wolfe,CEO".</li>
	 * 		<li>"<i>isManager</i>" - whether or not each employee is a manager or not. Either "true" or "false".</li>
	 * 		<li>"<i>all</i>" - Returns every set of information for each employee, separated by a comma. For example, 
	 * 						   "1,Matthew,Wolfe,CEO,1". The final int represents isManager. 1==true, 0==false.</li>
	 * </ul>
	 * @return The ArrayList of Strings containing the specified data of all users.
	 */
	ArrayList<String> getAllEmployees(String dataType) {
		String sql = "SELECT * FROM [" + this.company + "]";
		
		ArrayList<String> rows = new ArrayList<String>();
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				
				if (dataType.equals("id")) {
					rows.add(Integer.toString(rs.getInt(1)));
				} else if (dataType.equals("fullName")) {
					String firstName = rs.getString(2);
					String lastName = rs.getString(3);
					String fullName = firstName + " " + lastName;
					
					rows.add(fullName);
				} else if (dataType.equals("firstName")) {
					rows.add(rs.getString(2));
				} else if (dataType.equals("lastName")) {
					rows.add(rs.getString(3));
				} else if (dataType.equals("position")) {
					rows.add(rs.getString(4));
				} else if (dataType.equals("fullName,position")) {
					String firstName = rs.getString(2);
					String lastName = rs.getString(3);
					String fullName = firstName + " " + lastName;
					String position = rs.getString(4);
					
					String fullNamePosition = fullName + "," + position;
					
					rows.add(fullNamePosition);
				} else if (dataType.equals("isManager")) {
					int rawIsManager = rs.getByte(5);
					
					boolean isManager;
					if (rawIsManager == 1) {
						isManager = true;//if they ARE a manager
					} else {
						isManager = false;//if they ARE NOT a manager
					}

					rows.add(Boolean.toString(isManager));
				} else if (dataType.equals("all")) {
					String all = rs.getInt(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getByte(5);
					rows.add(all);
				} else {
					throw new IllegalArgumentException("Invalid data type, " + "\""+ dataType + "\".");
				}				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows;
	}
	
	//TO BE USED BY getEmployee(String data, String searchType) for searching the database. ***OVERLOAD ME FOR EACH TYPE OF SEARCH***
	//id is a unique identifier, so return type does not have to be an ArrayList as there can only be one matching id.
	private String searchDB(int id) {
		String sql = "SELECT * FROM [" + this.company + "]";
		
		ArrayList<String> rows = new ArrayList<String>();
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
				
			while (rs.next()) {	
			
				String all = rs.getInt(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getByte(5);
				rows.add(all);			
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (String data:rows) {
			
			//Convert 'all' string into just the id, as an int.
			String[] dataSet = data.split(",");
			int currentId = Integer.parseInt(dataSet[0]);
			
			if (id == currentId) {
				return data;
			}
		}
		
		//Will only get this far if no match is found.
		return "No Match Found.";
	}
	
	//not id search
	private ArrayList<String> searchDB(String namePosition, String isManager, String searchType) {
		/*
		 * Valid Args for searchType:
		 * * "fullName"
		 * * "firstName"
		 * * "lastName"
		 * * "position"
		 * * "fullName,position"
		 * 
		 * Valid Args for isManager:
		 * * "true"
		 * * "false"
		 * * "null"
		 */
		String sql = "SELECT * FROM [" + this.company + "]";
		
		ArrayList<String> allRows = new ArrayList<String>();
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
				
			while (rs.next()) {	
				String all = rs.getInt(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getByte(5);
				allRows.add(all);			
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> searchResults = new ArrayList<String>();
		boolean matchFound = false;
		for (String data:allRows) {
			
			String[] dataSet = data.split(",");
			
			//Convert all potential matches and search query to lower case to make sure there is no case sensitivity.
			String searchQuery = namePosition.toLowerCase();
			String currentFirstName = dataSet[1].toLowerCase();
			String currentLastName = dataSet[2].toLowerCase();
			String currentPosition = dataSet[3].toLowerCase();
			int currentIsManagerInt = Integer.parseInt(dataSet[4]);
			
			boolean currentIsManager = ((currentIsManagerInt == 1) ? true:false);
			
			String options;
			switch (searchType) {
			
			case "fullName":
				options = currentFirstName + " " + currentLastName;
				break;
				
			case "firstName":
				options = currentFirstName;
				break;
				
			case "lastName":
				options = currentLastName;
				break;
				
			case "position":
				options = currentPosition;
				break;
				
			case "fullName,position":
				options = currentFirstName + " " + currentLastName + "," + currentPosition;
				break;
				
			default:
				throw new IllegalArgumentException("Invalid searchType type, " + "\""+ searchType + "\".");
				
			}
			
			boolean isManagerBoolean;
			boolean checkManager = true;
			
			switch (isManager) {
			
			case "true":
				isManagerBoolean = true;
				break;
				
			case "false":
				isManagerBoolean = false;
				break;
				
			case "null":
				isManagerBoolean = false;
				checkManager = false;
				break;
				
			default:
				throw new IllegalArgumentException("Invalid isManager type, " + "\""+ isManager + "\".");
			
			}
			
			if (checkManager) {
				
				if (options.contains(searchQuery) && (currentIsManager == isManagerBoolean)) {
					searchResults.add(data);
					if (!matchFound) {//Don't run this every time a match is found, only on the first match found.
						matchFound = true;
					}
				}
				
			} else {
				
				if (options.contains(searchQuery)) {
					searchResults.add(data);
					if (!matchFound) {//Don't run this every time a match is found, only on the first match found.
						matchFound = true;
					}
				}
				
			}
		}
		
		//Will only get this far if no match is found.
		if (!matchFound) {
			searchResults.add("No Match Found.");
		}
		
		return searchResults;
	}
	
	public ArrayList<String> getEmployee(String data, String isManager) {
		try {
			int intData = Integer.parseInt(data);//Check if the passed "all" data is an int.
				
			//Convert the single returned id into an ArrayList with only 1 item.
			ArrayList<String> singleId = new ArrayList<String>();
			singleId.add(searchDB(intData));//id search does not care about isManager, id is specific enough.
			return singleId;
		} catch (NumberFormatException e) {
			return searchDB(data, isManager, "fullName,position");
		}
	}
	
}
