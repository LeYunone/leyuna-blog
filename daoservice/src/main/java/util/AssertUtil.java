package util;

/**
 * @author pengli
 * @create 2021-08-13 09:31
 *
 * 报错处理
 */
public class AssertUtil {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new RuntimeException(message);
        }
    }

    public static void isTrue(String message) {
        throw new RuntimeException(message);
    }
}
