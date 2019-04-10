package site.zido.rpc.utils;

public class CharUtils {
    public static boolean equalsIgnoreCase(char a,char b){
        if(a == b){
            return true;
        }
        if(toLower(a) == toLower(b)){
            return true;
        }
        return false;
    }

    public static char toLower(char a){
        if(a >=65 && a <=122 ){
            return (char)(a + 32);
        }
        return a;
    }
}
