package com.leyuna.blog.command;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengli
 * @create 2022-03-11 14:31
 */
@Service
public class TypeExe {

    public void addTypes(List<String> typeName, String typeNav){
        List<TypeE> listTypes=new ArrayList<>();
        //将名字封装成类
        typeName.stream().forEach(name->{
            TypeE typeDTOBuilder = TypeE.queryInstance()
                    .setTypeName(name).setUseCount(0).setFatherType(typeNav);
            listTypes.add(typeDTOBuilder);
        });
        boolean b = TypeE.batchCreate(listTypes);
        AssertUtil.isTrue(b, SystemErrorEnum.ADD_TYPE_FALE.getMsg());
    }

    /**
     * 删除分类
     *
     * @param types
     */
    @Transactional
    public void deleteTypes (List<String> types) {
        int b = TypeE.queryInstance().getGateway().batchDelete(types);
        AssertUtil.isTrue(b==types.size(),SystemErrorEnum.DELETE_TYPE_FALE.getMsg());
    }

    public void updateTypes(List<TypeBean> types){
        boolean is=true;
        for(TypeBean typeBean:types){
            is = TypeE.of(typeBean).update();
        }
        AssertUtil.isTrue(is,SystemErrorEnum.UPDATE_TYPE_FALE.getMsg());
    }

    /**
     * 获取所有分类  有分页和模糊
     *
     * @return
     */
    public DataResponse<Page<TypeCO>> getAllTypes (TypeBean type) {
        //如果有模糊查询条件则走模糊查询
        Page<TypeCO> typePage = TypeE.queryInstance().getGateway().selectLikePage(type);
        typePage.getRecords().stream().forEach(tag->{
            LocalDateTime lastTime=tag.getupdateDt();
            //如果最后使用的时间加了一个月还在现在的时间前面，那么就说明这个标签很久没用了
            if(LocalDateTime.now().isBefore(lastTime.plusMonths(1))){
                tag.setUserStatus("hot");
            }else{
                tag.setUserStatus("cold");
            }
        });

        return DataResponse.of(typePage);
    }
}
