package site.zido.rpc.core.extensions;

import java.lang.annotation.*;

/**
 * @author zido
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Activate {

    /**
     * 优先级，可选，越小越优先
     *
     * @return absolute ordering info
     */
    int order() default 0;

    String[] value() default {};
}
