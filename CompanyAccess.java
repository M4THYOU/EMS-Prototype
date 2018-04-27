import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CompanyAccess {
	Connection conn = null;
	int company;
	
	CompanyAccess(String db_file_location, int companyId) {
		DBAccess.connect(db_file_location);
		
		this.company = companyId;
	}
	

	//This is repeated code!!! PLEASE IMPROVE
	public void newUser(String firstName, String lastName, String position, boolean isManager) {
		
	}
	
}
