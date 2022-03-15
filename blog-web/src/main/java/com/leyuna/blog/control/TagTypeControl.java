package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.*;
import com.leyuna.blog.co.blog.TagCO;
import com.leyuna.blog.co.blog.TypeCO;
import com.leyuna.blog.co.blog.TypeNavCO;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.service.TagTypeService;
import com.leyuna.blog.service.UserService;
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
    private TagTypeService tagTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheExe cacheExe;
    /**
     * 取标签  【二级分类】
     * @param
     * @return
     */
    @RequestMapping("/tags")
    public DataResponse<Page<TagCO>> getTags(TagBean query){
        return tagTypeService.getALlTags(query);
    }

    /**
     * 取一级分类
     * @param
     * @return
     */
    @RequestMapping("/types")
    public DataResponse getTypes(TypeBean query){
        return tagTypeService.getALlTypes(query);
    }

    private List<CascaderTypeBean> getCascaderTypeResult(List<TypeCO> types,Map<String, TypeNavCO> typeNavMap){
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

    /**
     * 添加分类或标签  只要添加名字就好了
     * @param tags
     * @param types
     * @return
     */
    @RequestMapping("/addTagsAndTypes")
    public DataResponse addTagsAndTypes(@RequestParam(required = false) List<String> tags,
                                        @RequestParam(required = false) List<String> types,
                                        @RequestParam(required = false)String typeNav){
        return tagTypeService.addTagsAndTypes(tags, types,typeNav);
    }

    /**
     * 删除分类或标签
     * @param tags
     * @param types
     * @return
     */
    @GetMapping("/deleteTagsAndTypes")
    public DataResponse deleteTagsAndTypes(@RequestParam(required = false) List<String> tags,
                                           @RequestParam(required = false)List<String> types){
        return tagTypeService.deleteTagsAndTypes(tags, types);
    }

    /**
     * 更新标签和分类
     * @return
     */
    @PostMapping("/updateTagAndTypes")
    public DataResponse updateTag(List<TagBean> tag,List<TypeBean> type){
        return tagTypeService.updateTypesOrTags(tag, type);
    }

    /**
     * 查询所有的分类导航
     * @return
     */
    @GetMapping("/getTypeNav")
    public DataResponse getTypeNav(TypeNavBean typeNavBean){
        return tagTypeService.getTypeNavList(typeNavBean);
    }
    /**
     * 获得树形分类
     * @return
     */
    @GetMapping("/treeType")
    public DataResponse getTreeType(){

        DataResponse<Page<TypeCO>> aLlTypes = tagTypeService.getALlTypes(new TypeBean());
        Page<TypeCO> data = aLlTypes.getData();

        DataResponse<Map<String, TypeNavCO>> dataMap = tagTypeService.getTypeNavMap(new TypeNavBean());

        List<TreeTypeBean> result = getTreeResult(dataMap.getData(),data.getRecords());
        return DataResponse.of(result);
    }

    /**
     * 取得分类导航和分类的父子集， 提供给前台级联操作
     * @return
     */
    @RequestMapping("/getTypeInNav")
    public DataResponse getTypeInNav(){

        DataResponse<Page<TypeCO>> aLlTypes = tagTypeService.getALlTypes(new TypeBean());
        Page<TypeCO> data = aLlTypes.getData();

        DataResponse<Map<String, TypeNavCO>> dataMap = tagTypeService.getTypeNavMap(new TypeNavBean());

        List<CascaderTypeBean> cascaderTypeResult = getCascaderTypeResult(data.getRecords(), dataMap.getData());
        return DataResponse.of(cascaderTypeResult);
    }

    /**
     * 得到最终结果的树形结构
     * 拼装树形结构数据
     * @return
     */
    private List<TreeTypeBean> getTreeResult(Map<String,TypeNavCO> typeNav,List<TypeCO> types){
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

    @PostMapping("/saveTypeNav")
    public DataResponse addTypeNav(@RequestBody TypeNavBean typeNavBean){
        return tagTypeService.saveTypeNav(typeNavBean);
    }

    @PostMapping("/deleteTypeNav")
    public DataResponse deleteTypeNav(String typeNavId){
        return tagTypeService.deleteTypeNav(typeNavId);
    }
}
