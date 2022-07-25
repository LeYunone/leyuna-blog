package com.leyuna.blog.core.util;

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

    /**
     * 驼峰命名转下划线
     * @param name
     * @return
     */
   public static String underscoreName(String name) {
         StringBuilder result = new StringBuilder();
         if (name != null && name.length() > 0) {
                 // 将第一个字符处理成大写
                 result.append(name.substring(0, 1).toUpperCase());
                 // 循环处理其余字符
                 for (int i = 1; i < name.length(); i++) {
                        String s = name.substring(i, i + 1);
                         // 在大写字母前添加下划线
                         if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                                 result.append("_");
                             }
                         // 其他字符直接转成大写
                         result.append(s);
                 }
             }
         return result.toString();
   }
}
