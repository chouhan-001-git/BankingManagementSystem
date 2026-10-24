# Banking Management System

A console-based Banking Management System developed using Java and MySQL. The project demonstrates object-oriented programming, JDBC connectivity, SQL database operations, and transaction management fundamentals.

## Features

- Create a new bank account
- Deposit money
- Withdraw money with balance validation
- Check account balance
- View transaction history
- Display all bank accounts
- Store account details and transactions in MySQL
- Load existing accounts from the database

## Technologies Used

- Java
- MySQL
- JDBC (Java Database Connectivity)
- IntelliJ IDEA
- MySQL Workbench

## Database Structure

The application uses a MySQL database named `banking_db` with two tables:

- `accounts` — stores account numbers, account holders, balances, and creation timestamps.
- `transactions` — stores transaction types, amounts, and transaction timestamps.

## Setup Instructions

1. Install the JDK and MySQL Server.
2. Create the `banking_db` database and required tables.
3. Add MySQL Connector/J to the project's dependencies.
4. Configure the database URL, username, and password in `DBConnection.java`.
5. Open the project in IntelliJ IDEA.
6. Run `Main.java`.

## Security Note

Do not commit database passwords or other credentials to GitHub. Use environment variables or a local configuration file excluded from version control for real deployments.

## Learning Outcomes

This project helped practice Java OOP concepts, collections, exception handling, JDBC, SQL queries, and database persistence.
