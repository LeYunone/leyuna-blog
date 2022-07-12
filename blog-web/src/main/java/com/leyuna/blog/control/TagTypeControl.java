package com.leyuna.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.core.model.co.TagCO;
import com.leyuna.blog.core.model.co.TypeCO;
import com.leyuna.blog.core.model.co.TypeNavCO;
import com.leyuna.blog.core.model.constant.DataResponse;
import com.leyuna.blog.core.model.dto.*;
import com.leyuna.blog.core.service.TagTypeService;
import com.leyuna.blog.core.service.UserService;
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

    /**
     * 取标签  【二级分类】
     * @param
     * @return
     */
    @RequestMapping("/tags")
    public DataResponse<Page<TagCO>> getTags(TagDTO query){
        return tagTypeService.getALlTags(query);
    }

    /**
     * 取一级分类
     * @param
     * @return
     */
    @RequestMapping("/types")
    public DataResponse getTypes(TypeDTO query){
        return tagTypeService.getALlTypes(query);
    }

    /**
     * 二级树
     * @param types
     * @param typeNavMap
     * @return
     */
    private List<CascaderTypeDTO> getCascaderTypeResult(List<TypeCO> types, Map<String, TypeNavCO> typeNavMap){
        List<CascaderTypeDTO> lists=new ArrayList<>();
        Map<String, CascaderTypeDTO> map=new HashMap<>();
        types.stream().forEach(t->{
            String fatherType = t.getTypeNav();
            CascaderTypeDTO cascaderTypeDTO = map.get(fatherType);
            if(null== cascaderTypeDTO){
                CascaderTypeDTO reslutFul=new CascaderTypeDTO();
                //拿导航名
                TypeNavCO typeNavDTO = typeNavMap.get(fatherType);
                reslutFul.setLabel(typeNavDTO.getTypeNavName());
                reslutFul.setValue(t.getTypeNav());

                //创建子集合
                List<CascaderTypeDTO> chiden=new ArrayList<>();
                CascaderTypeDTO chidenBean=new CascaderTypeDTO();
                chidenBean.setValue(t.getId());
                chidenBean.setLabel(t.getTypeName());
                chiden.add(chidenBean);

                //填到结果集里
                reslutFul.setChildren(chiden);
                map.put(fatherType,reslutFul);
            }else{
                CascaderTypeDTO chiden=new CascaderTypeDTO();
                chiden.setValue(t.getId());
                chiden.setLabel(t.getTypeName());
                cascaderTypeDTO.getChildren().add(chiden);
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
    @GetMapping("/updateTagAndTypes")
    public DataResponse updateTagAndTypes(String id,String newName,String name){
        return tagTypeService.updateTypesOrTags(id,newName,name);
    }

    /**
     * 查询所有的分类导航
     * @return
     */
    @GetMapping("/getTypeNav")
    public DataResponse getTypeNav(TypeNavDTO typeNavDTO){
        return tagTypeService.getTypeNavList(typeNavDTO);
    }
    /**
     * 获得树形分类
     * @return
     */
    @GetMapping("/treeType")
    public DataResponse getTreeType(){
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setSize(50);
        DataResponse<Page<TypeCO>> aLlTypes = tagTypeService.getALlTypes(typeDTO);
        Page<TypeCO> data = aLlTypes.getData();

        DataResponse<Map<String, TypeNavCO>> dataMap = tagTypeService.getTypeNavMap(new TypeNavDTO());

        List<TreeTypeDTO> result = getTreeResult(dataMap.getData(),data.getRecords());
        return DataResponse.of(result);
    }

    /**
     * 取得分类导航和分类的父子集， 提供给前台级联操作
     * @return
     */
    @RequestMapping("/getTypeInNav")
    public DataResponse getTypeInNav(){
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setSize(50);
        DataResponse<Page<TypeCO>> aLlTypes = tagTypeService.getALlTypes(typeDTO);
        Page<TypeCO> data = aLlTypes.getData();

        DataResponse<Map<String, TypeNavCO>> dataMap = tagTypeService.getTypeNavMap(new TypeNavDTO());

        List<CascaderTypeDTO> cascaderTypeResult = getCascaderTypeResult(data.getRecords(), dataMap.getData());
        return DataResponse.of(cascaderTypeResult);
    }

    /**
     * 得到最终结果的树形结构
     * 拼装树形结构数据
     * @return
     */
    private List<TreeTypeDTO> getTreeResult(Map<String,TypeNavCO> typeNav, List<TypeCO> types){
        List<TreeTypeDTO> result=new ArrayList<>();

        //结果集
        Map<String, TreeTypeDTO> mapTreeType=new HashMap<>();
        types.stream().forEach(t->{
            String navId=t.getTypeNav();
            TreeTypeDTO thisTreeType = mapTreeType.get(navId);
            TypeNavCO typeNavDTO = typeNav.get(navId);
            //如果当前结果集里没有这个分类导航
            if(null==thisTreeType){
                TreeTypeDTO treeTypeDTO =new TreeTypeDTO();

                //创建子集合
                List<TreeTypeDTO> childrenTree=new ArrayList<>();
                //拼接子集合信息
                TreeTypeDTO type=new TreeTypeDTO();
                type.setId(t.getId());
                type.setLabel(t.getTypeName());
                childrenTree.add(type);

                treeTypeDTO.setId(typeNavDTO.getId());
                treeTypeDTO.setLabel(typeNavDTO.getTypeNavName());
                treeTypeDTO.setChildren(childrenTree);
                //将这次父导航塞入结果集中
                mapTreeType.put(navId, treeTypeDTO);
            }else{
                TreeTypeDTO type=new TreeTypeDTO();
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
    public DataResponse saveTypeNav(@RequestBody TypeNavDTO typeNavDTO){
        return tagTypeService.saveTypeNav(typeNavDTO);
    }

    @PostMapping("/deleteTypeNav")
    public DataResponse deleteTypeNav(String typeNavId){
        return tagTypeService.deleteTypeNav(typeNavId);
    }
}
