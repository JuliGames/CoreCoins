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
@RegisterBeanMapper(AccountBean.class)
public interface AccountDAO {


    @SqlUpdate("""
            create table if not exists coins_accounts
            (
            name  varchar(50) not null primary key ,
            owner varchar(36) not null
            );""")
    void createTable();

    @SqlQuery("SELECT * FROM coins_accounts")
    List<AccountBean> listAllBeans();

    @SqlUpdate("""
            INSERT IGNORE INTO coins_accounts(name, owner) values (:name,:uuid)
            """)
    void insert(@Bind("name") String name, @Bind("uuid") String uuid);

    @SqlUpdate("""
            INSERT IGNORE INTO coins_accounts(name, owner) values (:name,:uuid)
            """)
    void insert(@BindBean AccountBean accountBean);

    @SqlUpdate("DELETE FROM coins_accounts WHERE name = :name")
    void delete(@Bind("name") String name);

    @SqlUpdate("UPDATE coins_accounts " +
            "SET owner = :owner " +
            "WHERE name = :name;")
    void update(@Bind("name") String name, @Bind("owner") String newOwner);

    @SqlQuery("SELECT * FROM coins_accounts where name = :name")
    AccountBean selectBean(@Bind("name") String name);
}
