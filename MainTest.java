package com.jambalayasystems.main;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To do list
 * 
 * Add javadoc stuff to every class and method.
 * 
 * Finish Master's getUsers() and getEmployee(int id) methods
 * Add option for fullName search to getEmployee().
 * 
 * Create a method to add multiple employees at once.
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
