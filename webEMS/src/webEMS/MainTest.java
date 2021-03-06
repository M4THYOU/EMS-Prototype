package webEMS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/*
 * To do list
 * 
 * * * *Add javadoc stuff to every class and method. ---------> Do this after every method is completed.* * * *
 * 
 * Create a method to add multiple employees at once. Take input from CSV file. Auto generated ID's or predetermined.
 * 
 * Add javadoc for each new method.
 * 
 */

public class MainTest {

	public static void main(String[] args) {

		
		//Connection must be made before a new company can be added.
		String location = "c:\\\\\\\\Users\\\\Student.A219-16\\\\Desktop\\\\matthew_stuff\\\\sqlite\\\\ems";
		DBAccess.connect(location);
		
		
		//Create new company or enable update of an existing one.
		Master company = new Master("test", false);
		//System.out.println(company.employeeCount());
		//Access company table using this.
		CompanyAccess compTB = new CompanyAccess(location, company.getName());//Authentication?
		//System.out.println(compTB.checkStatus(compTB.insertUserWeb(id, firstName, lastName, position, isManager, confirmation)));

		String initialStatus = company.userToDBWeb(company.newUser("Matthew", "Wolfe", "CEO", true), "null", compTB);
		String checkedStatus = compTB.checkStatus(initialStatus);
		String newStatus = compTB.insertionMessage(checkedStatus);
		System.out.println("INITIAL STATUS: " + initialStatus);
		System.out.println("CHECKED STATUS: " + checkedStatus);
		System.out.println("NEW STATUS: " + newStatus + "\n\n");
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What would you like to do? \"confirmed\" OR \"cancelled\": ");
		String response = keyboard.nextLine();
		System.out.println("\n");
		
		String initialStatus2 = company.userToDBWeb(company.newUser("Matthew", "Wolfe", "CEO", true), response, compTB);
		String checkedStatus2 = compTB.checkStatus(initialStatus2);
		String newStatus2 = compTB.insertionMessage(checkedStatus2);
		System.out.println("INITIAL STATUS: " + initialStatus2);
		System.out.println("CHECKED STATUS: " + checkedStatus2);
		System.out.println("NEW STATUS: " + newStatus2 + "\n\n");
		keyboard.close();

		//System.out.println(company.getAllEmployees("all", compTB));

		//company.userToDB(company.newUser("Matthew", "Wolfe", "CEO", true), compTB);
		//company.userToDB(company.newUser("Matt", "Brewer", "Janitor", false), compTB);
		
		
		/*//TESTING SEARCH
		String isManager = "null";
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter a search query: ");
		String searchQuery = keyboard.nextLine();
		System.out.println(company.getEmployee(searchQuery, isManager, compTB));
		
		keyboard.close();
		*/
		
		//compTB.insertUsers("testData.csv"); //For mass insertion of users.
		
		DBAccess.disconnect();
		
		
	}
	
	

}