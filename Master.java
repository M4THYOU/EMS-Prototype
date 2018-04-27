import java.util.ArrayList;
import java.util.Iterator;

/*
 * To Do List
 * 
 * Method to list employees in a company. Option of managers, employees, or both.
 * 
 */


public class Master {
	
	private String company;
	private int companyId;
	private int employees;
	
	//DOES NOT WORK! > Doesn't view the database, every creation IS the first company...
	//Should add itself to database too.
	public Master(String company){
		
		this.companyId = newCompanyId(DBAccess.getCompanies());
		this.company = company;
	}
	
	//Used when creating a new company. Assigns this new company the next available ID.
	private static int newCompanyId(ArrayList<String> companies) {
		int value;
		
		try {
			value = Integer.parseInt(companies.get(companies.size()-1)) + 1;
		} catch (IndexOutOfBoundsException e) {
			value = 1;
			System.out.println("First Company");
		}
		
		return value;
	}
	
	//Create new employee OR manager of a company.
	public void newUser(String firstName, String lastName, String position, boolean isManager) {
		new Master.Employee(
			firstName,
			lastName,
			position,
			this.company,
			isManager
		);
	}
	
	//Returns company ID.
	public int getId() {
		return this.companyId;
	}
	
	//Returns number of employees in a company.
	public int employeeCount() {
		return employees;
	}
		
	//Returns the company name.
	public String getName() {
		return this.company;
	}
	
	/*
	//Returns array of employees in a company. Option of managers, employees, or both.
	public Employee[] getUsers() {
		ArrayList<Employee> users = new ArrayList<Employee>();
		
	}
	*///Gonna set up database first.
	
	
	
	
	
	
	
	private class Employee {
		private int employeeId;
		
		private String firstName;
		private String lastName;
		private String position;
		private String company;
		
		private boolean isManager;
		
		//Add to database after creation
		Employee(String firstName, String lastName, String position, String company, boolean isManager) {
			this.employeeId = employees + 1;
			
			this.firstName = firstName;
			this.lastName = lastName;
			this.position = position;
			
			this.isManager = isManager;
			
			employees++;
		}
	}
	
}


