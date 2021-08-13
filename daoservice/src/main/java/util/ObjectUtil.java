package util;

/**
 * @author pengli
 * @create 2021-08-13 09:29
    对象工具类
 */
public class ObjectUtil {

    public static boolean isNull(Object o){
        if(null==o){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isNotNull(Object o){
        if(null==o){
            return false;
        }else{
            return true;
        }
    }
}
