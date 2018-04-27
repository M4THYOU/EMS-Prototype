import java.util.ArrayList;
import java.util.Iterator;

/*
 * To do list
 * 
 * 
 * Allow instance of CompanyAccess to only change 1 table
 * 
 * Master newUser() should take an instance of CompanyAccess and only be able to add a user for matching companyId.
 * 
 */

public class MainTest {

	public static void main(String[] args) {
		
		//Connection must be made before a new company can be added.
		DBAccess companyTable = new DBAccess("C:/Users/Student.A219-16/Desktop/matthew_stuff/sqlite/ems");
		Master company = new Master("Cartridge of Inc.");
	
		company.newUser("Matthew", "Wolfe", "CEO", true);
		company.newUser("Sam", "Baker", "COO", false);
		
		companyTable.newCompanyTable(company.getId());
		
		DBAccess.disconnect();
		
		
	}
	
	

}
