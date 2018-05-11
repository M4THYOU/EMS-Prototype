package com.jambalayasystems.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class CompanyAccess {
	Connection conn = null;
	String company;
	
	CompanyAccess(String db_file_location, String company) {
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
		ArrayList<String> data = getEmployees("fullName,position");
		
		dataLoop: for (String user:data) {
			String[] nameAndPosition = user.split(",");
			String existingName = nameAndPosition[0];
			String existingPosition = nameAndPosition[1];
			
			String newName = firstName + " " + lastName;
			String newPosition = position;
			
			if (newName.equals(existingName)) {
				Scanner keyboard = new Scanner(System.in);
				char confirmContinue = '0';
				
				
				//BUG TEST ME. IT HAS BUGS
				while ((confirmContinue != 'y') || (confirmContinue != 'n')) {
					if (newPosition.equals(existingPosition)) {
						System.out.println("An employee with the same first name, last name, and position already exists.");
						System.out.print("Are you sure you want to add another? (y/n): ");
						confirmContinue = keyboard.next().charAt(0);
					} else {
						System.out.println("WARNING - An employee with the same first and last name already exists.");
						System.out.print("Are you sure you want to continue? (y/n): ");
						confirmContinue = keyboard.next().charAt(0);
					}
					
					
				
					if (confirmContinue == 'y') {
						break dataLoop;
					} else if (confirmContinue == 'n') {
						System.out.println("Employee not added.\n");
						keyboard.close();
						return;
					} else {
						System.out.println("Invalid input.\n");
					}
				}
				keyboard.close();
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
	
	private ArrayList<String> getEmployees(String data) {
		/*
		 * Valid Args for data:
		 * * "id"
		 * * "fullName"
		 * * "firstName"
		 * * "lastName"
		 * * "position"
		 * * "fullName,position"
		 * * "isManager"
		 * * "all"
		 */
		String sql = "SELECT * FROM [" + this.company + "]";
		
		ArrayList<String> rows = new ArrayList<String>();
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				
				if (data.equals("id")) {
					rows.add(Integer.toString(rs.getInt(1)));
				} else if (data.equals("fullName")) {
					String firstName = rs.getString(2);
					String lastName = rs.getString(3);
					String fullName = firstName + " " + lastName;
					
					rows.add(fullName);
				} else if (data.equals("firstName")) {
					rows.add(rs.getString(2));
				} else if (data.equals("lastName")) {
					rows.add(rs.getString(3));
				} else if (data.equals("position")) {
					rows.add(rs.getString(4));
				} else if (data.equals("fullName,position")) {
					String firstName = rs.getString(2);
					String lastName = rs.getString(3);
					String fullName = firstName + " " + lastName;
					String position = rs.getString(4);
					
					String fullNamePosition = fullName + "," + position;
					
					rows.add(fullNamePosition);
				} else if (data.equals("isManager")) {
					int rawIsManager = rs.getByte(5);
					
					boolean isManager;
					if (rawIsManager == 1) {
						isManager = true;//if they ARE a manager
					} else {
						isManager = false;//if they ARE NOT a manager
					}

					rows.add(Boolean.toString(isManager));
				} else if (data.equals("all")) {
					String all = rs.getInt(1) + "," + rs.getString(2) + "," + rs.getString(3) + "," + rs.getString(4) + "," + rs.getByte(5);
					rows.add(all);
				} else {
					throw new IllegalArgumentException("Invalid data type, " + "\""+ data + "\".");
				}				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rows;
	}
	
}
