package net.juligames.core.addons.coins.jdbi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Ture Bentzin
 * 11.12.2022
 * @implSpec this will show that the annotated method will execute stuff with jdbi
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface CauseJDBI {
}
