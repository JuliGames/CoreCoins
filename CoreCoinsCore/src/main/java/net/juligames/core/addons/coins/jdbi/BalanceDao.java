package net.juligames.core.addons.coins.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * @author Ture Bentzin
 * 11.12.2022
 */
@RegisterBeanMapper(BalanceBean.class)
public interface BalanceDao {


    @SqlUpdate("""
            create table if not exists coins_balance
            (
            accountName  varchar(50) not null primary key ,
            coinName varchar(30) not null,
            balance int not null
            );""")
    void createTable();

    @SqlQuery("SELECT * FROM coins_balance")
    List<BalanceBean> listAllBeans();

    @SqlUpdate("""
            INSERT IGNORE INTO coins_balance(accountName, coinName, balance) values (:name,:coin, :balance)
            """)
    void insert(@Bind("name") String accountName, @Bind("coin") String coinName, @Bind("balance") int balance);

    @SqlUpdate("""
            INSERT IGNORE INTO coins_accounts(accountName, coinName, balance) values (:accountName, :coinName, :balance)
            """)
    void insert(@BindBean BalanceBean balanceBean);

    @SqlUpdate("DELETE FROM coins_balance WHERE name = :name")
    void delete(@Bind("name") String accountName);

    @SqlUpdate("DELETE FROM coins_balance WHERE accountName = :name AND coinName = :coinName")
    void delete(@Bind("name") String accountName, @Bind("coinName") String coinName) ;

    @SqlUpdate("UPDATE coins_balance " +
            "SET balance = :balance " +
            "WHERE accountName LIKE :name AND coinName LIKE :coinName")
    void update(@Bind("name") String name, @Bind("coinName") String coinName, @Bind("balance") int balance);

    @SqlQuery("SELECT * FROM coins_balance where name = :name AND coinName = :coinName")
    BalanceBean selectBean(@Bind("name") String name, @Bind("coin") String coinName);

}
