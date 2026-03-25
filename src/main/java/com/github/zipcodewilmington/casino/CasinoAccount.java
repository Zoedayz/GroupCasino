package com.github.zipcodewilmington.casino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 7/21/2020.
 * `ArcadeAccount` is registered for each user of the `Arcade`.
 * The `ArcadeAccount` is used to log into the system to select a `Game` to play.
 */
public class CasinoAccount {
    private String username;
    private String password;
    private double balance;
    private final List<CasinoAccount> accounts = new ArrayList<>();

    public CasinoAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
    }

    public void displayBalance() {
        System.out.printf("Current balance: $%.2f%n", balance);
    }

    public void depositToBalance(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }
        balance += amount;
        System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public void withdrawBalance(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance()  { return balance;  }

    public CasinoAccount createAccount(String accountName, String accountPassword) {
        System.out.printf("Creating account for [ %s ]...%n", accountName);
        CasinoAccount newAccount = new CasinoAccount(accountName, accountPassword);
        System.out.printf("Account created for [ %s ].%n", accountName);
        return newAccount;
    }

    public void registerAccount(CasinoAccount casinoAccount) {
        System.out.printf("Registering account [ %s ]...%n", 
            casinoAccount.getUsername());
            accounts.add(casinoAccount);
        System.out.printf("Account [ %s ] registered successfully.%n", 
        casinoAccount.getUsername());
    }

    public CasinoAccount getAccount(String accountName, String accountPassword) {
        System.out.printf("Looking up account for [ %s ]...%n", accountName);
        for (CasinoAccount account : accounts) {
            if (account.getUsername().equals(accountName) &&
                account.getPassword().equals(accountPassword)) {
                System.out.printf("Account found for [ %s ].%n", accountName);
                return account;
                }
            }
            System.out.printf("No account found for [ %s ].%n", accountName);
        return null;
    }
}
