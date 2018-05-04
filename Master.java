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
	private int employees;
	
	//DOES NOT WORK! > Doesn't view the database, every creation IS the first company...
	//Should add itself to database too.
	public Master(String company){
		ArrayList<String> companies = DBAccess.getCompanies();//This is the only coupled code in this module.
		
		boolean isNew = true;
		for (String name:companies) {
			if (name.equals(company)) {
				System.out.println("Accessing existing company, [" + company + "].");
				isNew = false;
			}
		}
		
		if (isNew) {//Add to database.
			DBAccess.newCompanyTable(company);
		}
		
		this.company = company;
	}
	
	//Create new employee OR manager of a company.
	public Employee newUser(String firstName, String lastName, String position, boolean isManager) {
		Employee newEmp = new Master.Employee(
			firstName,
			lastName,
			position,
			this.company,
			isManager
		);
		
		this.employees++;
		
		return newEmp;
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
	
	//public Employee getEmployee(int employeeId) {}
	
	public void userToDB(Employee user, CompanyAccess company) {
		company.insertUser(user.employeeId, user.firstName, user.lastName, user.position, user.isManager);
	}
	
	
	
	
	
	
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
			
			this.company = company;
			
			this.isManager = isManager;
		}
		
		/*
		public Employee getEmployee(employeeId) {
			return 
		}
		*///Set up db.
	}
	
}


