package com.github.zipcodewilmington;

import org.junit.Assert;
import org.junit.Test;

import com.github.zipcodewilmington.casino.CasinoAccount;

public class CasinoAccountTest {
    @Test
    public void testDepositIncreasesBalance() {
        CasinoAccount account = new CasinoAccount("alice", "pass");
        account.depositToBalance(100.0);

        Assert.assertEquals(100.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawDecreasesBalance() {
        CasinoAccount account = new CasinoAccount("alice", "pass");
        account.depositToBalance(100.0);
        account.withdrawBalance(40.0);

        Assert.assertEquals(60.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        CasinoAccount account = new CasinoAccount("alice", "pass");
        account.depositToBalance(50.0);
        account.withdrawBalance(100.0);

        Assert.assertEquals(50.0, account.getBalance(), 0.001);
    }

    @Test
    public void testGetAccountReturnsNullIfNotFound() {
        CasinoAccount accountRegistry = new CasinoAccount("registry", "registry-pass");

        CasinoAccount result = accountRegistry.getAccount("nobody", "wrong");

        Assert.assertNull(result);
    }
}