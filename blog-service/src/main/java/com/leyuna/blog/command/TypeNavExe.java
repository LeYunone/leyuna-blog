package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TypeNavBean;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.domain.TypeNavE;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengli
 * @create 2022-03-11 15:15
 */
@Service
public class TypeNavExe {

    @CacheEvict(cacheNames = "typeNav")
    public void saveTypeNav(TypeNavBean typeNavBean){
        TypeNavCO save = TypeNavE.of(typeNavBean).save();
        AssertUtil.isFalse(save==null, SystemErrorEnum.UPDATE_TYPENAV_FAIL.getMsg());
    }

    @Cacheable(cacheNames = "typeNav",key = "#typeNavBean.toString()")
    public DataResponse getTypeNav(TypeNavBean typeNavBean,boolean ifMap){
        List<TypeNavCO> typeNavCOS = 
                TypeNavE.of(typeNavBean).selectByCon();
        if(ifMap){
            Map<String,TypeNavCO> resultMap=new HashMap<>();
            typeNavCOS.stream().forEach(t->{
                resultMap.put(t.getId(),t);
            });
            return DataResponse.of(resultMap);
        }else{
            return DataResponse.of(typeNavCOS);
        }
    }
    
    public void deleteTypeNav(String id){
        TypeNavE.queryInstance().getGateway().delete(id);
    }
}
