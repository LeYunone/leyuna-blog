package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.CascaderTypeBean;
import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.bean.ResultDTO;
import com.leyuna.blog.bean.TreeTypeBean;
import com.leyuna.blog.co.TagCO;
import com.leyuna.blog.co.TypeCO;
import com.leyuna.blog.co.TypeNavCO;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.service.TagTypeDomain;
import com.leyuna.blog.service.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengli
 * @create 2021-08-12 10:17
 *
 * 标签以及分类 操作控制器
 */
@RestController()
@RequestMapping("/tagType")
public class TagTypeControl{

    @Autowired
    private TagTypeDomain tagTypeDomain;

    @Autowired
    private UserDomain userDomain;
    /**
     * 取标签  【二级分类】
     * @param
     * @return
     */
    @RequestMapping("/tags")
    public ResponseBean getTags(@RequestParam(required = false)Integer pageIndex,
                                @RequestParam(required = false)Integer pageSize,
                                @RequestParam(required = false)String conditionName){
        Page<TagCO> aLlTags = tagTypeDomain.getALlTags(pageIndex,pageSize,conditionName);
        return  ResponseBean.of(aLlTags);
    }

    @GetMapping("/tagsId")
    public ResponseBean getTagsById(String...ids){
        List<TagCO> tagsByIds = tagTypeDomain.getTagsByIds(ids);
        return ResponseBean.of(tagsByIds);
    }

    /**
     * 取一级分类
     * @param
     * @return
     */
    @RequestMapping("/types")
    public ResponseBean getTypes(@RequestParam(required = false)Integer pageIndex,
                                 @RequestParam(required = false)Integer pageSize,
                                 @RequestParam(required = false)String conditionName){
        Page<TypeCO> aLlTags = tagTypeDomain.getALlTypes(pageIndex,pageSize,conditionName);
        return  ResponseBean.of(aLlTags);
    }

    /**
     * 取得分类导航和分类的父子集， 提供给前台级联操作
     * @return
     */
    @RequestMapping("/getTypeInNav")
    public ResponseBean getTypeInNav(){
        //暂定默认最多只会有100个分类
        Page<TypeCO> aLlTypes = tagTypeDomain.getALlTypes(null, null, null);
        List<TypeCO> records = aLlTypes.getRecords();
        Map<String, TypeNavCO> typeNavMap = tagTypeDomain.getTypeNavMap();
        List<CascaderTypeBean> cascaderTypeResult = getCascaderTypeResult(records, typeNavMap);
        return ResponseBean.of(cascaderTypeResult);
    }

    public List<CascaderTypeBean> getCascaderTypeResult(List<TypeCO> types,Map<String, TypeNavCO> typeNavMap){
        List<CascaderTypeBean> lists=new ArrayList<>();
        Map<String,CascaderTypeBean> map=new HashMap<>();
        types.stream().forEach(t->{
            String fatherType = t.getFatherType();
            CascaderTypeBean cascaderTypeBean = map.get(fatherType);
            if(null==cascaderTypeBean){
                CascaderTypeBean reslutFul=new CascaderTypeBean();
                //拿导航名
                TypeNavCO typeNavDTO = typeNavMap.get(fatherType);
                reslutFul.setLabel(typeNavDTO.getTypeNavName());
                reslutFul.setValue(t.getFatherType());

                //创建子集合
                List<CascaderTypeBean> chiden=new ArrayList<>();
                CascaderTypeBean chidenBean=new CascaderTypeBean();
                chidenBean.setValue(t.getId());
                chidenBean.setLabel(t.getTypeName());
                chiden.add(chidenBean);

                //填到结果集里
                reslutFul.setChildren(chiden);
                map.put(fatherType,reslutFul);
            }else{
                CascaderTypeBean chiden=new CascaderTypeBean();
                chiden.setValue(t.getId());
                chiden.setLabel(t.getTypeName());
                cascaderTypeBean.getChildren().add(chiden);
            }
        });
        map.forEach((key,value) ->{
            lists.add(value);
        });
        return lists;
    }

    @GetMapping("/typesId")
    public ResponseBean getTypesById(String...ids){
        List<TypeCO> tagsByIds = tagTypeDomain.getTypesByIds(ids);
        return ResponseBean.of(tagsByIds);
    }

    /**
     * 添加分类或标签  只要添加名字就好了
     * @param tags
     * @param types
     * @return
     */
    @RequestMapping("/addTagsAndTypes")
    public ResponseBean addTagsAndTypes(@RequestParam(required = false) List<String>
                                                    tags,@RequestParam(required = false) List<String> types,@RequestParam(required = false)String typeNav){
        userDomain.checkLock();
        String message=tagTypeDomain.addTypesOrTags(tags, types,typeNav);
        if(message==null){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(message);
        }
    }

    /**
     * 删除分类或标签
     * @param tags
     * @param types
     * @return
     */
    @GetMapping("/deleteTagsAndTypes")
    public ResponseBean deleteTagsAndTypes(@RequestParam(required = false,value = "tags") List<String> tags,@RequestParam(required = false)List<String> types){
        userDomain.checkLock();
        String message = tagTypeDomain.deleteTypesOrTags(tags, types);
        if(message==null){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(message);
        }
    }

    /**
     * 更新标签
     * @return
     */
    @PostMapping("/updateTag")
    public ResponseBean updateTag(String id,String tagName){
        userDomain.checkLock();
        TagE build = TagE.queryInstance().setId(id).setTagName(tagName);
        ResultDTO resultDTO = tagTypeDomain.updateTypesOrTags(build, null);
        if(resultDTO.getMessages()==null){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(resultDTO.getMessages().toString());
        }
    }

    /**
     * 更新分类
     * @return
     */
    @PostMapping("/updateType")
    public ResponseBean updateTypes(String id,String typeName){
        TypeE build = TypeE.queryInstance().setId(id).setTypeName(typeName);
        ResultDTO resultDTO = tagTypeDomain.updateTypesOrTags(null, build);
        if(resultDTO.getMessages()==null){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(resultDTO.getMessages().toString());
        }
    }

    /**
     * 查询所有的分类导航
     * @return
     */
    @GetMapping("/getTypeNav")
    public ResponseBean getTypeNav(@RequestParam(required = false)String conditionName){
        List<TypeNavCO> typeNavList = tagTypeDomain.getTypeNavList(conditionName);
        return ResponseBean.of(typeNavList);
    }
    /**
     * 获得树形分类
     * @return
     */
    @GetMapping("/treeType")
    public ResponseBean getTreeType(){
        //得到分类导航  id - dto
        Map<String, TypeNavCO> typeNav = tagTypeDomain.getTypeNavMap();
        //得到所有分类
        Page<TypeCO> aLlTypes = tagTypeDomain.getALlTypes(null, null, null);
        List<TypeCO> records = aLlTypes.getRecords();
        List<TreeTypeBean> treeResult = getTreeResult(typeNav, records);
        return ResponseBean.of(treeResult);
    }

    /**
     * 得到最终结果的树形结构
     * 拼装树形结构数据
     * @return
     */
    public List<TreeTypeBean> getTreeResult(Map<String,TypeNavCO> typeNav,List<TypeCO> types){
        List<TreeTypeBean> result=new ArrayList<>();

        //结果集
        Map<String,TreeTypeBean> mapTreeType=new HashMap<>();
        types.stream().forEach(t->{
            String navId=t.getFatherType();
            TreeTypeBean thisTreeType = mapTreeType.get(navId);
            TypeNavCO typeNavDTO = typeNav.get(navId);
            //如果当前结果集里没有这个分类导航
            if(null==thisTreeType){
                TreeTypeBean treeTypeBean=new TreeTypeBean();

                //创建子集合
                List<TreeTypeBean> childrenTree=new ArrayList<>();
                //拼接子集合信息
                TreeTypeBean type=new TreeTypeBean();
                type.setId(t.getId());
                type.setLabel(t.getTypeName());
                childrenTree.add(type);

                treeTypeBean.setId(typeNavDTO.getId());
                treeTypeBean.setLabel(typeNavDTO.getTypeNavName());
                treeTypeBean.setChildren(childrenTree);
                //将这次父导航塞入结果集中
                mapTreeType.put(navId,treeTypeBean);
            }else{
                TreeTypeBean type=new TreeTypeBean();
                type.setId(t.getId());
                type.setLabel(t.getTypeName());
                thisTreeType.getChildren().add(type);
            }

        });
        mapTreeType.forEach((key,value)->{
            result.add(value);
        });
        return result;
    }

    @PostMapping("/addTypeNav")
    public ResponseBean addTypeNav(String typeNavName){
        userDomain.checkLock();
        boolean b = tagTypeDomain.addTypeNav(typeNavName);
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.ADD_TYPENAV_FAIL.getMsg());
        }
    }

    /**
     * 修改分类导航名
     * @param typeNavName
     * @return
     */
    @PostMapping("/updateTypeNav")
    public ResponseBean editTypeNav(String typeNavName,String typeNavId){
        userDomain.checkLock();
        boolean b = tagTypeDomain.updateTypeNav(typeNavName, typeNavId);
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.UPDATE_TYPENAV_FAIL.getMsg());
        }
    }

    @PostMapping("/deleteTypeNav")
    public ResponseBean deleteTypeNav(String typeNavId){
        userDomain.checkLock();
        boolean b = tagTypeDomain.deleteTypeNav(typeNavId);
        if(b){
            return ResponseBean.buildSuccess();
        }else{
            return ResponseBean.buildFailure(SystemAsserts.DELETE_TYPENAV_FAIL.getMsg());
        }
    }
}
