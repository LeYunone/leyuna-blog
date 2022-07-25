package com.leyuna.blog.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author pengli
 * @create 2021-08-10 17:14
 *
 * 集合工具类
 */
public class CollectionUtil {

    public static <T> T getFirst(List<T> collection){
        if(collection!=null){
            if(collection.size()>0){
                return collection.get(0);
            }
        }
        return null;
    }

    public static <T> T getFirst(Set<T> collection){
        if(collection!=null){
            if(collection.size()>0){
                Iterator<T> iterator = collection.iterator();
                T next = iterator.next();
                return next;
            }
        }
        return null;
    }
}
