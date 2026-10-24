import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {

            if (connection != null && !connection.isClosed()) {
                System.out.println("MySQL connection successful!");
                System.out.println("Connected to banking_db.");
            }

        } catch (SQLException e) {
            System.out.println("MySQL connection failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
