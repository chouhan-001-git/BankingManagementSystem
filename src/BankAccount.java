import java.util.ArrayList;

public class BankAccount {

    private int accountNumber;
    private String accountHolder;
    private double balance;

    private ArrayList<String> transactions;

    // Constructor
    public BankAccount(int accountNumber, String accountHolder, double initialBalance){
        if(initialBalance < 0){
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;

        transactions = new ArrayList<>();
        transactions.add(String.format("Account opened with balance: " + initialBalance));
    }

    // Deposit Money
    public void deposit(double amount){
        if(amount <= 0){
            System.out.println("Deposit amount must be positive.");
            return;
        }

        balance += amount;
        System.out.println("Deposit successful.");

        transactions.add(String.format("Deposited: " + amount));
    }

    // Withdraw Money
    public void withdraw(double amount){
        if(amount <= 0){
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        else if(amount > balance){
            System.out.println("Insufficient Balance.");
        }
        else{
            balance -= amount;

            transactions.add(String.format("Withdrawn: " + amount));
            System.out.println("Withdrawal successful.");
        }
    }

    // Display Account Information
    public void displayAccountDetails(){
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: " + balance);
    }

    public String getAccountHolder() {
        return accountHolder;
    }
    // Return the current Balance
    public double getBalance(){
        return balance;
    }

    public int getAccountNumber(){
        return accountNumber;
    }
    public void displayTransactionHistory(){
        System.out.println("\n--- TRANSACTION HISTORY ---");
        if(transactions.isEmpty()){
            System.out.println("No transactions found.");
            return;
        }
        for(int i=0;i<transactions.size();i++){
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }
}
