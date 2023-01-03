package net.juligames.core.addons.coins.jdbi;

import net.juligames.core.api.jdbi.mapper.bean.LocaleBean;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * @author Ture Bentzin
 * 10.12.2022
 */
@RegisterBeanMapper(CoinBean.class)
public interface CoinDAO {

    @SqlUpdate("""
            create table if not exists coins_coins
            (
            name      varchar(30)            not null primary key ,
            description varchar(150) default ''
            );""")
    void createTable();

    @SqlQuery("SELECT * FROM coins_coins")
    List<CoinBean> listAllBeans();

    @SqlUpdate("INSERT IGNORE INTO coins_coins(name, description) values (:name, :description)")
    void insert(@BindBean CoinBean coinBean);

    @SqlUpdate("DELETE FROM coins_coins WHERE name = :name")
    void delete(@Bind("name") String name);

    @SqlUpdate("UPDATE coins_coins " +
            "SET description = :desc " +
            "WHERE name LIKE :name;")
    void update(@Bind("name") String locale, @Bind("desc") String newDescription);

    @SqlQuery("SELECT * FROM coins_coins where name = :name")
    CoinBean selectBean(@Bind("name") String name);

    default boolean has(String name, String description) {
        return listAllBeans().contains(new CoinBean(name,description));
    }

}
