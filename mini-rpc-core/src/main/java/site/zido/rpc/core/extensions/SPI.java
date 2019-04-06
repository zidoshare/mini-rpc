package site.zido.rpc.core.extensions;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
    String value() default "";

    Scope scope() default Scope.SINGLETON;

    enum Scope {
        /**
         * 单例
         */
        SINGLETON,

        /**
         * 多例
         */
        PROTOTYPE;
    }
}
