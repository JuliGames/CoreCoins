package net.juligames.core.addons.coins.jdbi;

/**
 * @author Ture Bentzin
 * 11.12.2022
 */
public class BalanceBean {

    private String accountName;
    private String coinName;
    private int balance;

    public BalanceBean(String accountName, String coinName, int balance) {
        this.accountName = accountName;
        this.coinName = coinName;
        this.balance = balance;
    }

    public BalanceBean() {
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
