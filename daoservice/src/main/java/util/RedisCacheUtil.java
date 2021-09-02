package util;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * @author pengli
 * @create 2021-09-02 10:19
 * redis 处理缓存工具类
 */
public class RedisCacheUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 私有化构造，准备单例
     */
    private RedisCacheUtil(){}
    private static RedisCacheUtil redisCacheUtil=null;

    public static RedisCacheUtil getRedisCacheUtil(){
            if(redisCacheUtil==null){
                synchronized (RedisCacheUtil.class){
                    if(redisCacheUtil==null){
                        redisCacheUtil=new RedisCacheUtil();
                    }
                }
            }
            return redisCacheUtil;
    }

    /**
     * 清空当前所有缓存
     */
    public void clearAllCache(){
        Set<String> keys = stringRedisTemplate.keys("*");
        if (ObjectUtils.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }
}
