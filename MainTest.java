
public class MainTest {

	public static void main(String[] args) {
		Master test = new Master("Cartridge of Inc.");
		
		System.out.println(test.employeeCount());
		test.newUser("Matthew Wolfe", "CEO", true);
		
		System.out.println(test.employeeCount());
		test.newUser("Sam Baker", "COO", false);
		
		System.out.println(test.employeeCount());

	}

}
