package net.juligames.core.addons.coins.jdbi;

/**
 * @author Ture Bentzin
 * 11.12.2022
 */
public class AccountBean {

    private String accountName;
    private String owner; //UUID

    public AccountBean(String accountName, String owner) {
        this.accountName = accountName;
        this.owner = owner;
    }

    public AccountBean() {

    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
