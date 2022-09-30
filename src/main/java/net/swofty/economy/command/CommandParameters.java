package net.swofty.economy.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters {
    String aliases() default "";

    String permission() default "";
}
