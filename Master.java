import java.util.ArrayList;

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
	
	private static int numberOfCompanies = 0;
	
	public Master(String company){
		this.companyId = newCompanyId();
		numberOfCompanies++;//last line of constructor.
	}
	
	//Used when creating a new company. Assigns this new company the next available ID.
	private static int newCompanyId() {
		return numberOfCompanies + 1;
	}
	
	//Create new employee OR manager of a company.
	public void newUser(String name, String position, boolean isManager) {
		if (isManager) {
			new Master.Manager(
				name,
				position,
				this.company
			);
		} else {
			new Master.Employee(
				name,
				position,
				this.company
			);
		}
	}
	
	//Returns number of employees in a company.
	public int employeeCount() {
		return employees;
	}
	
	/*
	//Returns array of employees in a company. Option of managers, employees, or both.
	public Employee[] getUsers() {
		ArrayList<Employee> users = new ArrayList<Employee>();
		
	}
	*///Gonna set up database first.
	
	
	
	
	
	
	
	private class Employee {
		private int employeeId;
		
		private String name;
		private String position;
		private String company;
		
		Employee(String name, String position, String company) {
			this.employeeId = employees + 1;
			
			this.name = name;
			this.position = position;
			
			employees++;
		}
	}

	
	
	
	
	
	private class Manager extends Employee {
		private boolean manager;
		Manager(String name, String position, String company) {
			super(name, position, company);
			this.manager = true;
		}
	}
	
}