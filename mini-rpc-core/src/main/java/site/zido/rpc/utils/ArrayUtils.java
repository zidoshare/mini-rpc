package site.zido.rpc.utils;

public class ArrayUtils {
    public static boolean isEmpty(Object[] array){
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array){
        return !isEmpty(array);
    }
}
