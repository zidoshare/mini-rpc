package site.zido.rpc.core.extensions;

import java.lang.annotation.*;

/**
 * Provide helpful information for {@link ExtensionLoader} to inject dependency extension instance.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Adaptive {
    String[] value() default {};
}
