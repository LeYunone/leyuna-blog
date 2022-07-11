package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.model.dto.TypeNavDTO;
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

    /**
     * 保存分类导航
     * @param typeNavDTO
     */
    @CacheEvict(cacheNames = "blog:typeNav")
    public void saveTypeNav(TypeNavDTO typeNavDTO){
        TypeNavCO save = TypeNavE.of(typeNavDTO).save();
        AssertUtil.isFalse(save==null, SystemErrorEnum.UPDATE_TYPENAV_FAIL.getMsg());
    }

    /**
     * 全量获取分类导航
     * @param typeNavDTO
     * @param ifMap
     * @return
     */
    @Cacheable(cacheNames = "blog:typeNav",key = "#typeNavDTO.toString()")
    public DataResponse getTypeNav(TypeNavDTO typeNavDTO, boolean ifMap){
        List<TypeNavCO> typeNavCOS =
                TypeNavE.of(typeNavDTO).selectByCon();
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

    /**
     * id删除分类导航
     * @param id
     */
    public void deleteTypeNav(String id){
        TypeNavE.queryInstance().getGateway().delete(id);
    }
}
