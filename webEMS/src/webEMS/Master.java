package webEMS;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * To Do List
 * 
 * Method to list employees in a company. Option of managers, employees, or both.
 * 
 */

/** Represents a company; contains {@link Employee} subclass.
 * 
 * @author Matthew Wolfe
 * @version 1.0
 * @since 1.0
 *
 */
public class Master {
	
	/**The name of the company.*/
	private String company;
	
	/**The number of employees in the company.*/
	private int employees;
	
	/** Initializes a company instance with the given String, company, as the company name.
	 * <p>
	 * The database is then scanned to see if a table with the same name as the given company already exists. If it does,<br>
	 * the method will <i>not</i> add another company to the database. If it does not, a new company will be added to the<br>
	 * database.
	 * <p>
	 * The instance's company name is set to the given company. The instance's employees is set to the number of<br>
	 * employees in an existing company table, or 0 if it is a new company.
	 * 
	 * 
	 * @param company - a string representing the company name.
	 */
	public Master(String company, boolean addNew){
		ArrayList<String> companies = DBAccess.getCompanies();
		
		boolean isNew = true;
		for (String name:companies) {
			if (name.equals(company)) {
				DBAccess.printMsg = "Accessing existing company, [" + company + "].\n";
				isNew = false;
			}
		}
		
		if (isNew && addNew) {//Add to database.
			DBAccess.newCompanyTable(company);
		}
		
		if (isNew && !addNew) {
			DBAccess.printMsg = "[" + company + "] not found.\n";
			return;
		}
		
		this.employees = DBAccess.countEmployees(company);
		this.company = company;
	}
	
	/**This method is to be used as the first argument in {@link #userToDB(Employee, CompanyAccess)}.
	 * <p>
	 * Creates a new employee of the instance company. Can also create a manager, if specified.
	 * <p>
	 * First, the {@link Master.Employee} constructor is called using the given arguments. The instance's employees is<br>
	 * then incremented.
	 * 
	 * @param firstName - the employee's first name.
	 * @param lastName - the employee's last name.
	 * @param position - the employee's job title at the company.
	 * @param isManager - whether or not the employee is a manager.
	 * @return The newly created {@link Employee} object.
	 */
	public Employee newUser(String firstName, String lastName, String position, boolean isManager) {
		Employee newEmp = new Master.Employee(
			firstName,
			lastName,
			position,
			this.company,
			isManager
		);
		
		return newEmp;
	}
	
	/**Returns the number of employees in the company.
	 * @return the number of employees in the company.
	 */
	public int employeeCount() {
		return employees;
	}
		
	/**Returns the company name.
	 * @return the company name.
	 */
	public String getName() {
		return this.company;
	}
	
	/**Inserts the given user into the database.
	 * 
	 * Calls {@link CompanyAccess#insertUser(int, String, String, String, boolean)}.
	 * 
	 * @param user - the return of {@link #newUser(String, String, String, boolean)}
	 * @param company - The access point to a specific company's table in the database.
	 */
	public void userToDB(Employee user, CompanyAccess company) {
		company.insertUser(user.employeeId, user.firstName, user.lastName, user.position, user.isManager);
	}
	
	public String userToDBWeb(Employee user, String confirmation, CompanyAccess company) {
		return company.insertUserWeb(user.employeeId, user.firstName, user.lastName, user.position, user.isManager, confirmation);
	}
	
	public ArrayList<String> getEmployee(String data, String isManager, CompanyAccess company) {
		return company.getEmployee(data, isManager);
	}
	
	public ArrayList<String> getAllEmployees(String dataType, CompanyAccess company) {
		return company.getAllEmployees(dataType);
	}
	
	
	
	
	/**Represents an employee; subclass of {@link Master}.
	 * 
	 * @author Matthew Wolfe
	 * @version 1.0
	 * @since 1.0
	 *
	 */
	private class Employee {
		
		/**The identification number of the employee.*/
		private int employeeId;
		
		/**The employee's first name.*/
		private String firstName;
		/**The employee's last name.*/
		private String lastName;
		/**The employee's job title.*/
		private String position;
		/**The company the employee works at.*/
		private String company;
		
		/**Whether or not the employee is also a manager.*/
		private boolean isManager;
		
		/**Initializes an employee of the parent company.
		 * <p>
		 * Gets called by {@link Master#newUser(String, String, String, boolean)}.
		 * 
		 * @param firstName - the employee's first name.
		 * @param lastName - the employee's last name.
		 * @param position - the employee's job title.
		 * @param company - the name of the company the employee works at.
		 * @param isManager - whether or not the employee is also a manager.
		 */
		Employee(String firstName, String lastName, String position, String company, boolean isManager) {
			this.employeeId = employees + 1;
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.position = position;
			
			this.company = company;
			
			this.isManager = isManager;
		}
		
	}
	
}