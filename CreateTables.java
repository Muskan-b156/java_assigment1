import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTables {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe"; // Update your DB URL
        String username = "system"; // Oracle DB username
        String password = "BCA5C"; // Oracle DB password

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = conn.createStatement()) {

            String createDepartmentTable = "CREATE TABLE Department1 ("
                    + "Did INT PRIMARY KEY, "
                    + "Dname VARCHAR(50))";

            String createEmployeeTable = "CREATE TABLE Employee1 ("
                    + "Eid INT PRIMARY KEY, "
                    + "Ename VARCHAR(100), "
                    + "Salary DECIMAL(10, 2), "
                    + "Address VARCHAR(100), "
                    + "Did INT, "
                    + "FOREIGN KEY (Did) REFERENCES Department(Did))";

            stmt.execute(createDepartmentTable);
            stmt.execute(createEmployeeTable);

            System.out.println("Tables created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}