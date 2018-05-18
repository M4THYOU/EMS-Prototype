package com.jambalayasystems.main;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To do list
 * 
 * * * *Add javadoc stuff to every class and method. ---------> Do this after every method is completed.* * * *
 * 
 * THIS REQUIRES A GENRAL USE METHOD IN COMPANY ACCESS WHICH RETURNS INDIVIDUAL EMPLOYEES (as a string).
 * Finish Master's nameSearch(string name), positionSearch(string position), and getEmployee(int id) methods
 *  * Combine all of these into one advancedSearch(string query, boolean isManager) -> isManager is a "filter".
 * 
 * Create a method to add multiple employees at once. Take input from CSV file. Auto generated ID's or predetermined.
 * 
 */

public class MainTest {

	public static void main(String[] args) {

		
		//Connection must be made before a new company can be added.
		String location = "C:/Users/Student.A219-16/Desktop/matthew_stuff/sqlite/ems";
		DBAccess.connect(location);
		
		
		//Create new company or enable update of an existing one.
		Master company = new Master("Cartridge of Inc.");

		
		//Access company table using this.
		CompanyAccess compTB = new CompanyAccess(location, "Cartridge of Inc.");//Authentication?
		
		

		//company.userToDB(company.newUser("Matthew", "Wolfe", "CEO", true), compTB);
		//company.userToDB(company.newUser("Matt", "Brewer", "Janitor", false), compTB);
		
		DBAccess.disconnect();
		
		
	}
	
	

}
