package util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-10 16:46
 * 转换对象工具类
 */
public class TransformationUtil {

    /**
     * 将DTO对象转换成Map ->  属性名 - 值   allEq查询
     */
    public static Map<String,Object> transDTOColumnMap(Object object){
        Map<String,Object> resultMap=new HashMap<>();
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for(Field field : fields){
            String name = field.getName();
            //省略
            if("serialVersionUID".equals(name)){
                continue;
            }
            // 获取原来的访问控制权限
            boolean accessFlag = field.isAccessible();
            // 修改访问控制权限
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
            }
            // 恢复访问控制权限
            field.setAccessible(accessFlag);

            if (null != value && StringUtils.isNotBlank(value.toString())) {
                resultMap.put(name, value);
            }
        }
        return resultMap;
    }

    /**
     *
     * @param copyClass  复制源
     * @param toClass    出参对象
     * @return
     */
    public static <T,D>Object copyToDTO(Class<T> copyClass,Class<D> toClass){


    }
}
