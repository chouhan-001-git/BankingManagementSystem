import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_db";

    private static final String USER = System.getenv("DB_USER");

    // MySQL Password
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        if(USER == null || PASSWORD == null){
            throw new SQLException(
                    "DB_USER or DB_PASSWORD environment variable is missing."
            );
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
