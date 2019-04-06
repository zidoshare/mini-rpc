package site.zido.rpc.utils;

import java.util.Date;

public class ReflectionUtils {
    public static boolean isPrimitive(Class<?> cls){
        return cls.isPrimitive() || cls == String.class || cls == Boolean.class || cls == Character.class
                || Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls);
    }
}
