import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private Map<Integer, BankAccount> accounts = new HashMap<>();

    public boolean addAccount(BankAccount account){
        if(account == null){
            System.out.println("Invalid Account.");
            return false;
        }
        int accountNumber = account.getAccountNumber();

        if(accounts.containsKey(accountNumber)){
            System.out.println("Account number already exists.");
            return false;
        }
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully.");
        return true;
    }

    public BankAccount findAccount(int accountNumber){
        return accounts.get(accountNumber);
    }

    public void displayAllAccounts(){
        if(accounts.isEmpty()){
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("\n===== ALL BANK ACCOUNTS =====");
        for(BankAccount account : accounts.values()){
            account.displayAccountDetails();
            System.out.println("--------------------------------");
        }
    }
    public int getTotalAccounts(){
        return accounts.size();
    }

    public void loadAccounts(List<BankAccount> savedAccounts) {

        for (BankAccount account : savedAccounts) {

            accounts.put(account.getAccountNumber(), account);

        }

        System.out.println(
                savedAccounts.size() + " accounts loaded from database."
        );

    }
}
