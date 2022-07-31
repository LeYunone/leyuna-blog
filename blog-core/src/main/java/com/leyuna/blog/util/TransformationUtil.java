package com.leyuna.blog.util;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String,Object> resultMap=new HashMap<String,Object>();
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

            if (null != value && StringUtil.isNotBanks(value.toString())) {
                //将名字转换成大小写下划线
                String s = StringUtil.underscoreName(name);
                resultMap.put(s, value);
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
    public static <D>D copyToDTO(Object copyClass, Class<D> toClass){
        D d = null;
        try {
            d = toClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(ObjectUtil.isNotNull(copyClass)){
            BeanUtils.copyProperties(copyClass, d);
        }
        return d;
    }

    /**
     *
     * @param copyClass  复制源
     * @param toClass    出参对象
     * @return
     */
    public static <D>List<D> copyToLists(List<?> copyClass, Class<D> toClass){
        List<D> result=new ArrayList<>();
        copyClass.stream().forEach(list->{
            try {
                Object tarObject = toClass.newInstance();
                BeanUtils.copyProperties(list, tarObject);
                result.add((D)tarObject);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    public static <D> Page<D> copyToPage(IPage page, Class<D> toClass){
        Page<D> result=new Page<>();
        result.setRecords(TransformationUtil.copyToLists(page.getRecords(),toClass));
        result.setTotal(page.getTotal());
        result.setPages(page.getPages());
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        return result;
    }
}
