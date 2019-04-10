package site.zido.rpc.utils;

public class StringUtils {
    private static final int DEFAULT_TABLE_SIZE = 256;
    public static boolean isEmpty(String str){
        return str == null || str.isEmpty();
    }

    public static boolean isBlank(String str) {
        return isEmpty(str);
    }

    public static boolean contains(String str1, String str2){
        return indexOf(str2,str1) != -1;
    }

    public static int indexOf(String pattern, String target){
        int tLen = target.length();
        int pLen = pattern.length();

        if (pLen > tLen) {
            return -1;
        }
        int[] badTable = buildBadChar(pattern);
        int[] goodTable = buildGoodSuffix(pattern);
        for (int i = pLen - 1,j; i < tLen;){
            for (j = pLen - 1; target.charAt(i) == pattern.charAt(j); i--,j--){
                if(j == 0){
                    return i;
                }
            }
            i += Math.max(goodTable[pLen - j - 1],badTable[target.charAt(i)]);
        }
        return -1;
    }

    /**
     * 构建坏字符表
     * @return index array
     */
    private static int[] buildBadChar(String pattern){
        int[] badTable = new int[DEFAULT_TABLE_SIZE];
        int len = pattern.length();
        for(int i = 0; i < DEFAULT_TABLE_SIZE; i++){
            badTable[i] = len;
        }
        for(int i = 0; i < len - 1;i++){
            int k = pattern.charAt(i);
            badTable[k] = len - i - 1;
        }
        return badTable;
    }

    private static int[] buildGoodSuffix(String pattern){
        int len = pattern.length();
        int[] goodTable = new int[len];
        int lastPrefixPosition = len;
        for (int i = len - 1; i >= 0; i--) {
            if(isPrefix(pattern,i + 1)){
                lastPrefixPosition = i + 1;
            }
            goodTable[len - 1 - i] = lastPrefixPosition - i + len - 1;
        }
        for (int i = 0; i < len - 1; ++i){
            int slen = suffixLength(pattern,i);
            goodTable[slen] = len - 1 - i + slen;
        }
        return goodTable;
    }

    private static boolean isPrefix(String pattern, int p) {
        int len = pattern.length();
        for (int i = p,j = 0; i < len; ++i,++j){
            if(pattern.charAt(i) != pattern.charAt(j)){
                return false;
            }
        }
        return true;
    }

    private static int suffixLength(String pattern,int p){
        int len = pattern.length();
        int index = 0;
        for(int i = p, j = len - 1; i >= 0 && pattern.charAt(i) == pattern.charAt(j);i--,j--){
            index += 1;
        }
        return index;
    }
}
