import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class StudentManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentDB";
    private static final String USER = "root"; // replace with your MySQL username
    private static final String PASS = "mohithsa09"; // replace with your MySQL password

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Check if the Students table exists
            ResultSet checkTable = stmt.executeQuery("SHOW TABLES LIKE 'Students'");
            if (!checkTable.next()) {
                System.out.println("The Students table does not exist.");
                return;
            }

            // Query to retrieve all records from the Students table
            String sql = "SELECT * FROM Students";
            ResultSet rs = stmt.executeQuery(sql);

            // Display student records
            System.out.println("Student Records:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String major = rs.getString("Major");
                System.out.printf("ID: %d, Name: %s, Age: %d, Major: %s%n", id, name, age, major);
            }

            // Retrieve and display metadata
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println("\nTable Metadata:");
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                String columnType = rsmd.getColumnTypeName(i);
                System.out.printf("Column %d: %s (%s)%n", i, columnName, columnType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}