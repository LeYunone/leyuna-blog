package com.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.domain.TagTypeDomain;
import com.blog.api.domain.UserDomain;
import com.blog.api.dto.ResultDTO;
import com.blog.api.dto.TagDTO;
import com.blog.api.dto.TypeDTO;
import com.blog.api.dto.TypeNavDTO;
import com.blog.bean.CascaderTypeBean;
import com.blog.bean.ResponseBean;
import com.blog.bean.TreeTypeBean;
import com.blog.error.SystemAsserts;
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
public class TagTypeControl extends SysBaseControl{

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
        Page<TagDTO> aLlTags = tagTypeDomain.getALlTags(pageIndex,pageSize,conditionName);
        ResponseBean responseBean = successResponseBean(aLlTags.getRecords());
        responseBean.setPage(aLlTags);
        responseBean.setObjData(aLlTags.getRecords().size());
        return  responseBean;
    }

    @GetMapping("/tagsId")
    public ResponseBean getTagsById(Integer...ids){
        List<TagDTO> tagsByIds = tagTypeDomain.getTagsByIds(ids);
        return successResponseBean(tagsByIds);
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
        Page<TypeDTO> aLlTags = tagTypeDomain.getALlTypes(pageIndex,pageSize,conditionName);
        ResponseBean responseBean = successResponseBean(aLlTags.getRecords());
        responseBean.setPage(aLlTags);
        return  responseBean;
    }

    /**
     * 取得分类导航和分类的父子集， 提供给前台级联操作
     * @return
     */
    @RequestMapping("/getTypeInNav")
    public ResponseBean getTypeInNav(){
        //暂定默认最多只会有100个分类
        Page<TypeDTO> aLlTypes = tagTypeDomain.getALlTypes(null, null, null);
        List<TypeDTO> records = aLlTypes.getRecords();
        Map<Integer, TypeNavDTO> typeNavMap = tagTypeDomain.getTypeNavMap();
        List<CascaderTypeBean> cascaderTypeResult = getCascaderTypeResult(records, typeNavMap);
        return successResponseBean(cascaderTypeResult);
    }

    public List<CascaderTypeBean> getCascaderTypeResult(List<TypeDTO> types,Map<Integer, TypeNavDTO> typeNavMap){
        List<CascaderTypeBean> lists=new ArrayList<>();
        Map<Integer,CascaderTypeBean> map=new HashMap<>();
        types.stream().forEach(t->{
            Integer fatherType = t.getFatherType();
            CascaderTypeBean cascaderTypeBean = map.get(fatherType);
            if(null==cascaderTypeBean){
                CascaderTypeBean reslutFul=new CascaderTypeBean();
                //拿导航名
                TypeNavDTO typeNavDTO = typeNavMap.get(fatherType);
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
    public ResponseBean getTypesById(Integer...ids){
        List<TypeDTO> tagsByIds = tagTypeDomain.getTypesByIds(ids);
        return successResponseBean(tagsByIds);
    }

    /**
     * 添加分类或标签  只要添加名字就好了
     * @param tags
     * @param types
     * @return
     */
    @RequestMapping("/addTagsAndTypes")
    public ResponseBean addTagsAndTypes(@RequestParam(required = false) List<String>
                                                    tags,@RequestParam(required = false) List<String> types,@RequestParam(required = false)Integer typeNav){
        userDomain.checkLock();
        ResultDTO resultDTO = tagTypeDomain.addTypesOrTags(tags, types,typeNav);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

    /**
     * 删除分类或标签
     * @param tags
     * @param types
     * @return
     */
    @GetMapping("/deleteTagsAndTypes")
    public ResponseBean deleteTagsAndTypes(@RequestParam(required = false,value = "tags") List<Integer> tags,@RequestParam(required = false)List<Integer> types){
        userDomain.checkLock();
        ResultDTO resultDTO = tagTypeDomain.deleteTypesOrTags(tags, types);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

    /**
     * 更新标签
     * @return
     */
    @PostMapping("/updateTag")
    public ResponseBean updateTag(Integer id,String tagName){
        userDomain.checkLock();
        TagDTO build = TagDTO.builder().id(id).tagName(tagName).build();
        ResultDTO resultDTO = tagTypeDomain.updateTypesOrTags(build, null);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

    /**
     * 更新分类
     * @return
     */
    @PostMapping("/updateType")
    public ResponseBean updateTypes(Integer id,String typeName){
        TypeDTO build = TypeDTO.builder().id(id).typeName(typeName).build();
        ResultDTO resultDTO = tagTypeDomain.updateTypesOrTags(null, build);
        if(resultDTO.getMessages()==null){
            return successResponseBean();
        }else{
            return failResponseBean(resultDTO.getMessages());
        }
    }

    /**
     * 查询所有的分类导航
     * @return
     */
    @GetMapping("/getTypeNav")
    public ResponseBean getTypeNav(@RequestParam(required = false)String conditionName){
        List<TypeNavDTO> typeNavList = tagTypeDomain.getTypeNavList(conditionName);
        return successResponseBean(typeNavList);
    }
    /**
     * 获得树形分类
     * @return
     */
    @GetMapping("/treeType")
    public ResponseBean getTreeType(){
        //得到分类导航  id - dto
        Map<Integer, TypeNavDTO> typeNav = tagTypeDomain.getTypeNavMap();
        //得到所有分类
        Page<TypeDTO> aLlTypes = tagTypeDomain.getALlTypes(null, null, null);
        List<TypeDTO> records = aLlTypes.getRecords();
        List<TreeTypeBean> treeResult = getTreeResult(typeNav, records);
        ResponseBean responseBean = successResponseBean(treeResult);
        responseBean.setObjData(records.size());
        return responseBean;
    }

    /**
     * 得到最终结果的树形结构
     * 拼装树形结构数据
     * @return
     */
    public List<TreeTypeBean> getTreeResult(Map<Integer,TypeNavDTO> typeNav,List<TypeDTO> types){
        List<TreeTypeBean> result=new ArrayList<>();

        //结果集
        Map<Integer,TreeTypeBean> mapTreeType=new HashMap<>();
        types.stream().forEach(t->{
            Integer navId=t.getFatherType();
            TreeTypeBean thisTreeType = mapTreeType.get(navId);
            TypeNavDTO typeNavDTO = typeNav.get(navId);
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
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.ADD_TYPENAV_FAIL);
        }
    }

    /**
     * 修改分类导航名
     * @param typeNavName
     * @return
     */
    @PostMapping("/updateTypeNav")
    public ResponseBean editTypeNav(String typeNavName,Integer typeNavId){
        userDomain.checkLock();
        boolean b = tagTypeDomain.updateTypeNav(typeNavName, typeNavId);
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.UPDATE_TYPENAV_FAIL);
        }
    }

    @PostMapping("/deleteTypeNav")
    public ResponseBean deleteTypeNav(Integer typeNavId){
        userDomain.checkLock();
        boolean b = tagTypeDomain.deleteTypeNav(typeNavId);
        if(b){
            return successResponseBean();
        }else{
            return failResponseBean(SystemAsserts.DELETE_TYPENAV_FAIL);
        }
    }
}
