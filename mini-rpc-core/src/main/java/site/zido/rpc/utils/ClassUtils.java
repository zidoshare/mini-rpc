package site.zido.rpc.utils;

public class ClassUtils {
    public static ClassLoader getClassLoader(Class<?> cls){
        ClassLoader classLoader = null;
        try{
            classLoader = Thread.currentThread().getContextClassLoader();
        }catch (Throwable ignore){
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if(classLoader == null){
            classLoader = cls.getClassLoader();
            if(classLoader == null){
                try{
                    classLoader = ClassLoader.getSystemClassLoader();
                }catch (Throwable ignore){
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return classLoader;
    }
}
