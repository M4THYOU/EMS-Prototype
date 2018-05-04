import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyAccess {
	Connection conn = null;
	String company;
	
	CompanyAccess(String db_file_location, String company) {
		this.conn = DBAccess.connect(db_file_location);
		
		//Check if this company actually exists.
		boolean noMatch = true;
		ArrayList<String> companies = DBAccess.getCompanies();
		for (String name:companies) {
			if (name.equals(company)) {
				noMatch = false;
			}
		}
		if (noMatch) {
			throw new IllegalArgumentException("No company with that name exists.");
		}
		
		this.company = company;//check if company passed matches one in db.
	}
	
	//!!!!!!!!!!!!!!!!TEST ME TOMORROW!!!!!!!!!!!!!!!!!!!! -->> Gets called through master method "userToDB".
	//Ensure that this can only change this instance's company.
	public void insertUser(int id, String firstName, String lastName, String position, boolean isManager) {
		String sql = "INSERT INTO [" + this.company + "](id, firstName, lastName, position, isManager) VALUES(?,?,?,?,?)";
		
		//Get numerical value of isManager
		int manager;
		if (isManager) {
			manager = 1;//if they ARE a manager
		} else {
			manager = 0;//if they ARE NOT a manager
		}
		
		System.out.println(this.conn);
		
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, id);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, position);
			pstmt.setInt(5, manager);
			pstmt.executeUpdate();
			System.out.println(firstName + " " + lastName + " added to [" + this.company + "].");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
