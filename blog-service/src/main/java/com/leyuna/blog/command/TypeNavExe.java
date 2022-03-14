package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TypeNavBean;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.domain.TypeNavE;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
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

    public void updateTypeNav(TypeNavBean typeNavBean){
        boolean update = TypeNavE.of(typeNavBean).update();
        AssertUtil.isTrue(update, SystemErrorEnum.UPDATE_TYPENAV_FAIL.getMsg());
    }
    
    public DataResponse getTypeNav(TypeNavBean typeNavBean,boolean ifMap){
        List<TypeNavCO> typeNavCOS = 
                TypeNavE.of(typeNavBean).selectNavType();
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
        TypeNavE.queryInstance().setId(id).setDeleted(1);
    }
}
