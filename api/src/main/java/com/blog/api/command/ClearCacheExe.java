package com.blog.api.command;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import util.SpringUtil;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author pengli
 * @create 2021-09-02 15:24
 *
 * 清除缓存指令
 */
@Service
public class ClearCacheExe {
    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    //存放需要消除的缓存key
    private  CopyOnWriteArrayList<String> copyOnWriteArrayList=new CopyOnWriteArrayList();
    /**
     * 私有化构造，准备单例
     */

    /**
     * 清空当前所有缓存
     */
    public  void clearAllCache(){
        Set<String> allKeys = getAllKeys();
        if (ObjectUtils.isNotEmpty(allKeys)) {
            stringRedisTemplate.delete(allKeys);
        }
    }

    public  Set<String> getAllKeys(){
        Set<String> keys = stringRedisTemplate.keys("*");
        return keys;
    }

    /**
     * 清空博客查询缓存  服务 getAllBlogByPage  getBlogByPage
     */
    public  void clearBlogQueryCache(){
        Set<String> keys = null;
        try {
            keys = stringRedisTemplate.keys("*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isNotEmpty(keys)) {
            keys.parallelStream().forEach(key->{
                if(key.matches("getAllBlogByPage[\\s\\S]*") || key.matches("getBlogByPage[\\s\\S]*")){
                    copyOnWriteArrayList.add(key);
                }
            });
            stringRedisTemplate.delete(copyOnWriteArrayList);
        }
    }

    /**
     * 清空指定博客缓存  服务 getBlogById
     */
    public  void clearBlogQueryByIdCache(Integer id){
        Set<String> keys = stringRedisTemplate.keys("*");
        if (ObjectUtils.isNotEmpty(keys)) {
            for(String key:keys){
                if(key.matches("getBlogById::"+id)){
                    copyOnWriteArrayList.add(key);
                    break;
                }
            }
            stringRedisTemplate.delete(copyOnWriteArrayList);
        }
    }

    /**
     * 清空网站相关公告服务  服务 getWebHistory
     */
    public void clearWebHistoryCache(){
        Set<String> keys = stringRedisTemplate.keys("*");
        if (ObjectUtils.isNotEmpty(keys)) {
            keys.parallelStream().forEach(key->{
                if(key.matches("getWebHistory[\\s\\S]*")){
                    copyOnWriteArrayList.add(key);
                }
            });
            stringRedisTemplate.delete(copyOnWriteArrayList);
        }
    }

    /**
     * 清空标签所有查询 服务 getAllTags
     */
    public void clearTagQueryCache(){
        Set<String> keys = stringRedisTemplate.keys("*");
        if (ObjectUtils.isNotEmpty(keys)) {
            keys.parallelStream().forEach(key->{
                if(key.matches("getAllTags[\\s\\S]*")){
                    copyOnWriteArrayList.add(key);
                }
            });
            stringRedisTemplate.delete(copyOnWriteArrayList);
        }
    }

    /**
     * 清空分类所有查询 服务 getAllTypes
     */
    public void clearTypeQueryCache(){
        Set<String> keys = stringRedisTemplate.keys("*");
        if (ObjectUtils.isNotEmpty(keys)) {
            keys.parallelStream().forEach(key->{
                if(key.matches("getAllTypes[\\s\\S]*")){
                    copyOnWriteArrayList.add(key);
                }
            });
            stringRedisTemplate.delete(copyOnWriteArrayList);
        }
    }

    /**
     * 清空分类导航所有查询 服务 getTypeNav
     */
    public void clearTypeNavQueryCache(){
        Set<String> keys = stringRedisTemplate.keys("*");
        if (ObjectUtils.isNotEmpty(keys)) {
            keys.parallelStream().forEach(key->{
                if(key.matches("getTypeNav[\\s\\S]*")){
                    copyOnWriteArrayList.add(key);
                }
            });
            stringRedisTemplate.delete(copyOnWriteArrayList);
        }
    }
}
