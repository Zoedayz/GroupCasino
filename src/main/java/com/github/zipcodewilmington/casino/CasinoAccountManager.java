package com.github.zipcodewilmington.casino;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 7/21/2020.
 * `CasinoAccountManager` stores, manages, and retrieves `CasinoAccount` objects.
 */
public class CasinoAccountManager {

    private final List<CasinoAccount> accounts = new ArrayList<>();

    /**
     * @param accountName     name of account to be returned
     * @param accountPassword password of account to be returned
     * @return `CasinoAccount` with specified `accountName` and `accountPassword`, or null if not found
     */
    public CasinoAccount getAccount(String accountName, String accountPassword) {
        for (CasinoAccount account : accounts) {
            if (account.getUsername().equals(accountName) &&
                account.getPassword().equals(accountPassword)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Creates a new `CasinoAccount` with a $500 starting balance.
     *
     * @param accountName     name of account to be created
     * @param accountPassword password of account to be created
     * @return new instance of `CasinoAccount`
     */
    public CasinoAccount createAccount(String accountName, String accountPassword) {
        CasinoAccount newAccount = new CasinoAccount(accountName, accountPassword);
        newAccount.depositToBalance(500.00);
        return newAccount;
    }

    /**
     * Registers a `CasinoAccount` to the managed account list.
     *
     * @param casinoAccount the account to register
     */
    public void registerAccount(CasinoAccount casinoAccount) {
        accounts.add(casinoAccount);
    }
}
