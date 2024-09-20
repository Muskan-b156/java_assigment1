import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SelectScrollableResultSet {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";  // Update this to your Oracle DB username
        String password = "BCA5C";  // Update this to your Oracle DB password

        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            String query = "SELECT Ename, Salary FROM Employee1";
            ResultSet rs = stmt.executeQuery(query);

            // Prompt user for action
            System.out.println("Enter 1 to display the first employee, 2 for the last employee, 3 for both:");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Scroll to the first row
                    rs.first();
                    System.out.println("First Employee: " + rs.getString("Ename") + ", Salary: " + rs.getDouble("Salary"));
                    break;
                case 2:
                    // Scroll to the last row
                    rs.last();
                    System.out.println("Last Employee: " + rs.getString("Ename") + ", Salary: " + rs.getDouble("Salary"));
                    break;
                case 3:
                    // Scroll to the first row
                    rs.first();
                    System.out.println("First Employee: " + rs.getString("Ename") + ", Salary: " + rs.getDouble("Salary"));

                    // Scroll to the last row
                    rs.last();
                    System.out.println("Last Employee: " + rs.getString("Ename") + ", Salary: " + rs.getDouble("Salary"));
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}