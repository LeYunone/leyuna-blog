package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.CascaderTypeBean;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.TreeTypeBean;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.domain.TagE;
import com.leyuna.blog.domain.TypeE;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.service.TagTypeService;
import com.leyuna.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    private TagTypeService TagTypeService;

    @Autowired
    private UserService UserService;
    /**
     * 取标签  【二级分类】
     * @param
     * @return
     */
    @RequestMapping("/tags")
    public DataResponse getTags(@RequestParam(required = false)Integer pageIndex,
                                @RequestParam(required = false)Integer pageSize,
                                @RequestParam(required = false)String conditionName){
        Page<TagCO> aLlTags = TagTypeService.getALlTags(pageIndex,pageSize,conditionName);
        return  DataResponse.of(aLlTags);
    }

    @GetMapping("/tagsId")
    public DataResponse getTagsById(String...ids){
        List<TagCO> tagsByIds = TagTypeService.getTagsByIds(ids);
        return DataResponse.of(tagsByIds);
    }

    /**
     * 取一级分类
     * @param
     * @return
     */
    @RequestMapping("/types")
    public DataResponse getTypes(@RequestParam(required = false)Integer pageIndex,
                                 @RequestParam(required = false)Integer pageSize,
                                 @RequestParam(required = false)String conditionName){
        Page<TypeCO> aLlTags = TagTypeService.getALlTypes(pageIndex,pageSize,conditionName);
        return  DataResponse.of(aLlTags);
    }

    /**
     * 取得分类导航和分类的父子集， 提供给前台级联操作
     * @return
     */
    @RequestMapping("/getTypeInNav")
    public DataResponse getTypeInNav(){
        //暂定默认最多只会有100个分类
        Page<TypeCO> aLlTypes = TagTypeService.getALlTypes(null, null, null);
        Map<String, TypeNavCO> typeNavMap = TagTypeService.getTypeNavMap();

        List<CascaderTypeBean> cascaderTypeResult = getCascaderTypeResult(aLlTypes.getRecords(), typeNavMap);
        return DataResponse.of(cascaderTypeResult);
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
    public DataResponse getTypesById(String...ids){
        List<TypeCO> tagsByIds = TagTypeService.getTypesByIds(ids);
        return DataResponse.of(tagsByIds);
    }

    /**
     * 添加分类或标签  只要添加名字就好了
     * @param tags
     * @param types
     * @return
     */
    @RequestMapping("/addTagsAndTypes")
    public DataResponse addTagsAndTypes(@RequestParam(required = false) List<String>
                                                    tags,@RequestParam(required = false) List<String> types,@RequestParam(required = false)String typeNav){
        UserService.checkLock();
        String message=TagTypeService.addTypesOrTags(tags, types,typeNav);
        if(message==null){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(message);
        }
    }

    /**
     * 删除分类或标签
     * @param tags
     * @param types
     * @return
     */
    @GetMapping("/deleteTagsAndTypes")
    public DataResponse deleteTagsAndTypes(@RequestParam(required = false,value = "tags") List<String> tags,@RequestParam(required = false)List<String> types){
        UserService.checkLock();
        String message = TagTypeService.deleteTypesOrTags(tags, types);
        if(message==null){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(message);
        }
    }

    /**
     * 更新标签
     * @return
     */
    @PostMapping("/updateTag")
    public DataResponse updateTag(String id,String tagName){
        UserService.checkLock();
        TagE build = TagE.queryInstance().setId(id).setTagName(tagName);
        String message = TagTypeService.updateTypesOrTags(build, null);
        if(StringUtils.isEmpty(message)){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(message);
        }
    }

    /**
     * 更新分类
     * @return
     */
    @PostMapping("/updateType")
    public DataResponse updateTypes(String id,String typeName){
        TypeE build = TypeE.queryInstance().setId(id).setTypeName(typeName);
        String message = TagTypeService.updateTypesOrTags(null, build);
        if(StringUtils.isEmpty(message)){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(message);
        }
    }

    /**
     * 查询所有的分类导航
     * @return
     */
    @GetMapping("/getTypeNav")
    public DataResponse getTypeNav(@RequestParam(required = false)String conditionName){
        List<TypeNavCO> typeNavList = TagTypeService.getTypeNavList(conditionName);
        return DataResponse.of(typeNavList);
    }
    /**
     * 获得树形分类
     * @return
     */
    @GetMapping("/treeType")
    public DataResponse getTreeType(){
        //得到分类导航  id - dto
        Map<String, TypeNavCO> typeNav = TagTypeService.getTypeNavMap();
        //得到所有分类
        Page<TypeCO> aLlTypes = TagTypeService.getALlTypes(null, null, null);
        List<TreeTypeBean> treeResult = getTreeResult(typeNav, aLlTypes.getRecords());
        return DataResponse.of(treeResult);
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
    public DataResponse addTypeNav(String typeNavName){
        UserService.checkLock();
        boolean b = TagTypeService.addTypeNav(typeNavName);
        if(b){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(SystemAsserts.ADD_TYPENAV_FAIL.getMsg());
        }
    }

    /**
     * 修改分类导航名
     * @param typeNavName
     * @return
     */
    @PostMapping("/updateTypeNav")
    public DataResponse editTypeNav(String typeNavName,String typeNavId){
        UserService.checkLock();
        boolean b = TagTypeService.updateTypeNav(typeNavName, typeNavId);
        if(b){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(SystemAsserts.UPDATE_TYPENAV_FAIL.getMsg());
        }
    }

    @PostMapping("/deleteTypeNav")
    public DataResponse deleteTypeNav(String typeNavId){
        UserService.checkLock();
        boolean b = TagTypeService.deleteTypeNav(typeNavId);
        if(b){
            return DataResponse.buildSuccess();
        }else{
            return DataResponse.buildFailure(SystemAsserts.DELETE_TYPENAV_FAIL.getMsg());
        }
    }
}
