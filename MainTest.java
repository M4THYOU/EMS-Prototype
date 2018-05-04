import java.util.ArrayList;
import java.util.Iterator;

/*
 * To do list
 * 
 * 
 * Allow instance of CompanyAccess to only change 1 table
 * 
 * Master newUser() should take (as an arg) an instance of CompanyAccess and only be able to add a user for matching companyId.
 * Don't do that. Don't want them to be too coupled.
 */

public class MainTest {

	public static void main(String[] args) {
		
		//Test stuff here

		
		//Connection must be made before a new company can be added.
		String location = "C:/Users/Student.A219-16/Desktop/matthew_stuff/sqlite/ems";
		DBAccess.connect(location);
		
		
		//Create new company or enable update of an existing one.
		Master company = new Master("Cartridge of Inc.");

		
		
		
		
		
		//Access company table using this.
		CompanyAccess compTB = new CompanyAccess(location, "Cartridge of Inc.");//Authentication?
		
		

		//company.userToDB(company.newUser("Matthew", "Wolfe", "CEO", true), compTB);
		
		company.userToDB(company.newUser("Matt", "Brewer", "CFO", false), compTB);//Throws error. Thinks every new user is has id "1"
		//Get employee count every time an existing one is initialized. 
		//SELECT count(*) FROM [Cartridge of Inc.];//Counts entries in a given database.
		
		DBAccess.disconnect();
		
		
	}
	
	

}
