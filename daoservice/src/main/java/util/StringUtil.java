package util;

/**
 * @author pengli
 * @create 2021-08-10 17:14
 *
 * 字符串工具类
 */
public class StringUtil {

    public static boolean isNotBanks(String str){
        if(null==str){
            return false;
        }else{
            if(str.length() != 0 ){
                return true;
            }else{
                return false;
            }
        }
    }
}
