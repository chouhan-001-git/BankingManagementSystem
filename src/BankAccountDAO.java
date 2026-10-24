import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDAO {

    public boolean createAccount(BankAccount account) {

        String sql = "INSERT INTO accounts " + "(account_number, account_holder, balance) " + "VALUES (?, ?, ?)";

        try(Connection connection = DBConnection.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, account.getAccountNumber());
            statement.setString(2, account.getAccountHolder());
            statement.setDouble(3, account.getBalance());

            int rowInserted = statement.executeUpdate();

            return rowInserted > 0;
        }catch (SQLException e){
            System.out.println("Error creating account: " + e.getMessage());

            return false;
        }
    }

    public List<BankAccount> getAllAccounts() {
        List<BankAccount> accounts = new ArrayList<>();

        String sql = "SELECT account_number, account_holder, balance "
                + "FROM accounts";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int number = resultSet.getInt("account_number");
                String name = resultSet.getString("account_holder");
                double balance = resultSet.getDouble("balance");

                BankAccount account =
                        new BankAccount(number, name, balance);

                accounts.add(account);
            }

        } catch (SQLException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        return accounts;
    }

    public boolean updateBalance(int accountNumber, double newBalance) {

        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, newBalance);
            statement.setInt(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }


    public boolean deposit(int accountNumber, double amount) {
        String sql = """
            UPDATE accounts
            SET balance = balance + ?
            WHERE account_number = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setInt(2, accountNumber);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error depositing money: " + e.getMessage());
            return false;
        }
    }

    public boolean withdraw(int accountNumber, double amount) {
        String sql = """
            UPDATE accounts
            SET balance = balance - ?
            WHERE account_number = ?
              AND balance >= ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setInt(2, accountNumber);
            statement.setDouble(3, amount);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error withdrawing money: " + e.getMessage());
            return false;
        }
    }


    public boolean saveTransaction(
            int accountNumber,
            String transactionType,
            double amount) {

        String sql = """
            INSERT INTO transactions
                (account_number, transaction_type, amount)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, accountNumber);
            statement.setString(2, transactionType);
            statement.setDouble(3, amount);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error saving transaction: " + e.getMessage()
            );
            return false;
        }
    }


    public List<String> getTransactionHistory(int accountNumber) {
        List<String> history = new ArrayList<>();

        String sql = """
            SELECT transaction_type, amount, transaction_time
            FROM transactions
            WHERE account_number = ?
            ORDER BY transaction_time DESC
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String type = resultSet.getString("transaction_type");
                    double amount = resultSet.getDouble("amount");
                    String time = resultSet.getString("transaction_time");

                    history.add(
                            type + " | Amount: " + amount + " | Time: " + time
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving transaction history: " + e.getMessage()
            );
        }

        return history;
    }


}
