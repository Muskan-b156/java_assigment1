import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class InsertDataWithScanner {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe"; // Update your DB URL
        String username = "system"; // Oracle DB username
        String password = "BCA5C"; // Oracle DB password

        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Insert into Department
            String insertDeptSQL = "INSERT INTO Department1 (Did, Dname) VALUES (?, ?)";
            PreparedStatement pstmtDept = conn.prepareStatement(insertDeptSQL);

            System.out.println("Enter number of departments to insert:");
            int deptCount = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            for (int i = 0; i < deptCount; i++) {
                System.out.println("Enter Department ID:");
                int did = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                System.out.println("Enter Department Name:");
                String dname = scanner.nextLine();

                pstmtDept.setInt(1, did);
                pstmtDept.setString(2, dname);
                pstmtDept.addBatch();
            }

            pstmtDept.executeBatch();
            System.out.println("Departments inserted successfully!");

            // Insert into Employee
            String insertEmpSQL = "INSERT INTO Employee1 (Eid, Ename, Salary, Address, Did) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtEmp = conn.prepareStatement(insertEmpSQL);

            System.out.println("Enter number of employees to insert:");
            int empCount = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            for (int i = 0; i < empCount; i++) {
                System.out.println("Enter Employee ID:");
                int eid = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                System.out.println("Enter Employee Name:");
                String ename = scanner.nextLine();

                System.out.println("Enter Employee Salary:");
                double salary = scanner.nextDouble();
                scanner.nextLine();  // Consume newline

                System.out.println("Enter Employee Address:");
                String address = scanner.nextLine();

                System.out.println("Enter Department ID for Employee:");
                int did = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                // Check if department exists
                String checkDeptSQL = "SELECT COUNT(*) FROM Department1 WHERE Did = ?";
                PreparedStatement checkDeptStmt = conn.prepareStatement(checkDeptSQL);
                checkDeptStmt.setInt(1, did);
                ResultSet rs = checkDeptStmt.executeQuery();
                rs.next();
                int deptExists = rs.getInt(1);

                if (deptExists > 0) {
                    // Department exists, insert employee
                    pstmtEmp.setInt(1, eid);
                    pstmtEmp.setString(2, ename);
                    pstmtEmp.setDouble(3, salary);
                    pstmtEmp.setString(4, address);
                    pstmtEmp.setInt(5, did);
                    pstmtEmp.addBatch();
                } else {
                    System.out.println("Department ID " + did + " does not exist. Skipping employee insertion for Employee ID " + eid);
                }
            }

            pstmtEmp.executeBatch();
            System.out.println("Employees inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
