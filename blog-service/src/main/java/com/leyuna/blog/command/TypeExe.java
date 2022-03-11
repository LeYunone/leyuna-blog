package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.TypeBean;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
