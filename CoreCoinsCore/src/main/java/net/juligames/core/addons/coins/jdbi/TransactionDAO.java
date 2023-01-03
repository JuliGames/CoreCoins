package net.juligames.core.addons.coins.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.Date;
import java.util.List;

/**
 * @author Ture Bentzin
 * 12.12.2022
 */
@RegisterBeanMapper(TransactionBean.class)
public interface TransactionDAO {



    @SqlUpdate("""
            create table if not exists coins_transaction
            (
            timeStamp  datetime not null,
            from_account varchar(50) not null,
            to_account varchar(50) not null,
            coin varchar(30) not null,
            amount int not null,
            initiator varchar(36) null,
            
            constraint transaction_pk primary key(
            timeStamp,from_account,to_account,coin
            ),
            constraint accounts_from_fk foreign key (from_account) REFERENCES coins_accounts(name),
            constraint accounts_to_fk foreign key (to_account) REFERENCES coins_accounts(name),
            constraint coin_fk foreign key (coin) REFERENCES coins_coins(name)
            );""")
    void createTable();

    @SqlQuery("SELECT * FROM coins_transaction")
    List<TransactionBean> listAllBeans();

    @SqlUpdate("""
            INSERT IGNORE INTO coins_transaction(timeStamp, from_account, to_account, coin, amount, initiator)
             values (:timeStamp,:from_account,:to_account,:coin,:amount,:initiator)
            """)
    void insert(@Bind("timeStamp") Date date, @Bind("from_account") String from_account,
                @Bind("to_account") String to_account, @Bind("coin") String coin, @Bind("amount") int amount,
                @Bind("initiator") String initiator);

    @SqlUpdate("""
            INSERT IGNORE INTO coins_transaction(timeStamp, from_account, to_account, coin, amount, initiator)
             values (:timeStamp,:from,:to,:coin,:amount,:initiator)
            """)
    void insert(@BindBean BalanceBean balanceBean);

    @SqlUpdate("DELETE FROM coins_transaction WHERE timeStamp = :date")
    void delete(@Bind("date") Date date);

    @SqlUpdate("DELETE FROM coins_transaction WHERE timeStamp = :date AND from_account = :from_account AND to_account = :to_account AND coin = :coin")
    void delete(@Bind("data") Date date, @Bind("from_account") String from_account,
                @Bind("to_account") String to_account, @Bind("coin") String coin);

    @SqlQuery("SELECT * FROM coins_transaction WHERE timeStamp = :date AND from_account = :from_account AND to_account = :to_account AND coin = :coin")
    BalanceBean selectBean(@Bind("data") Date date, @Bind("from_account") String from_account,
                           @Bind("to_account") String to_account, @Bind("coin") String coin);

    @SqlQuery("SELECT * FROM coins_transaction WHERE from_account = :from_account AND to_account = :to_account")
    List<BalanceBean> selectBeans(@Bind("from_account") String from_account,
                                  @Bind("to_account") String to_account);

}
