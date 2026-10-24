import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null;

        Bank bank = new Bank();

        BankAccountDAO accountDAO = new BankAccountDAO();

        bank.loadAccounts(accountDAO.getAllAccounts());
        
        int choice;

        do {
            System.out.println("\n===== BANKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transaction History");
            System.out.println("6. Display All Accounts");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");


            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
                System.out.println("Enter your choice: ");
            }

            choice = scanner.nextInt();

            switch (choice) {

                case 1: {
                    System.out.print("Enter account number: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid account number.");
                        scanner.next();
                        break;
                    }
                    int number = scanner.nextInt();

                    System.out.print("Enter account holder name: ");
                    String name = scanner.next();

                    System.out.print("Enter initial balance: ");
                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid balance.");
                        scanner.next();
                        break;
                    }
                    double initialBalance = scanner.nextDouble();

                    try {
                        account =
                                new BankAccount(number, name, initialBalance);

                        boolean saved = accountDAO.createAccount(account);

                        if (saved) {
                            boolean added = bank.addAccount(account);

                            if (added) {
                                System.out.println("Account created and saved successfully.");
                            } else {
                                System.out.println(
                                        "Account saved in database, but not added to memory."
                                );
                            }
                        } else {
                            System.out.println("Account could not be saved.");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }

                case 2:
                case 3:{
                    System.out.print("Enter account number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid account number.");
                        scanner.next();
                        break;
                    }

                    int accountNumber = scanner.nextInt();
                    account = bank.findAccount(accountNumber);

                    if (account == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    System.out.print("Enter amount: ");

                    if (!scanner.hasNextDouble()) {
                        System.out.println("Invalid amount.");
                        scanner.next();
                        break;
                    }

                    double amount = scanner.nextDouble();

                    if (amount <= 0) {
                        System.out.println("Amount must be greater than zero.");
                        break;
                    }

                    double oldBalance = account.getBalance();
                    boolean success;

                    if (choice == 2) {
                        success = accountDAO.deposit(accountNumber, amount);
                    } else {
                        if (amount > oldBalance) {
                            System.out.println("Insufficient balance.");
                            break;
                        }

                        success = accountDAO.withdraw(accountNumber, amount);
                    }

                    if (success) {
                        double newBalance;

                        if (choice == 2) {
                            newBalance = oldBalance + amount;
                        } else {
                            newBalance = oldBalance - amount;
                        }

                        // Update the in-memory account only after DB success.
                        if (choice == 2) {
                            account.deposit(amount);
                            String transactionType =
                                    (choice == 2) ? "DEPOSIT" : "WITHDRAWAL";

                            boolean historySaved = accountDAO.saveTransaction(
                                    accountNumber,
                                    transactionType,
                                    amount
                            );

                            if (!historySaved) {
                                System.out.println(
                                        "Warning: Balance updated, but transaction history could not be saved."
                                );
                            }
                        } else {
                            account.withdraw(amount);
                            String transactionType =
                                    (choice == 2) ? "DEPOSIT" : "WITHDRAWAL";

                            boolean historySaved = accountDAO.saveTransaction(
                                    accountNumber,
                                    transactionType,
                                    amount
                            );

                            if (!historySaved) {
                                System.out.println(
                                        "Warning: Balance updated, but transaction history could not be saved."
                                );
                            }
                        }

                        System.out.printf("Transaction successful!%n");
                        System.out.printf("Updated balance: %.2f%n", newBalance);
                    } else {
                        System.out.println(
                                "Transaction failed. Check the account or database."
                        );
                    }

                    break;
                }
                case 4:
                case 5: {
                    System.out.print("Enter account number: ");

                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid account number.");
                        scanner.next();
                        break;
                    }

                    int accountNumber = scanner.nextInt();


                    account = bank.findAccount(accountNumber);

                    if (account == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    List<String> history =
                            accountDAO.getTransactionHistory(accountNumber);

                    if (history.isEmpty()) {
                        System.out.println("No transaction history found.");
                    } else {
                        System.out.println("\n===== TRANSACTION HISTORY =====");

                        for (String transaction : history) {
                            System.out.println(transaction);
                        }
                    }

                    break;
                }


                case 6:
                    bank.displayAllAccounts();
                    System.out.println(
                            "Total accounts: " + bank.getTotalAccounts()
                    );
                    break;

                case 7:
                    System.out.println("Thank you for using our banking system!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        }while(choice != 7);
            scanner.close();


    }
}