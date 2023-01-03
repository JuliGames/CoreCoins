package net.juligames.core.addons.coins.jdbi;

import java.sql.Date;

/**
 * @author Ture Bentzin
 * 12.12.2022
 */
public class TransactionBean {

    private Date timeStamp;
    private String from; //AccountName fkey
    private String to; //AccountName fkey
    private String initiator; //UUID nullable
    private String coin; //CoinName
    private int amount;

    public TransactionBean(Date timeStamp, String from, String to, String initiator, String coin, int amount) {
        this.timeStamp = timeStamp;
        this.from = from;
        this.to = to;
        this.initiator = initiator;
        this.coin = coin;
        this.amount = amount;
    }

    public TransactionBean() {
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
