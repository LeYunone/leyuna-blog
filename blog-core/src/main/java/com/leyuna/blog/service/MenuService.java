package com.leyuna.blog.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyuna.blog.constant.enums.MenuPositionEnum;
import com.leyuna.blog.dao.MenuDao;
import com.leyuna.blog.dao.repository.entry.MenuDO;
import com.leyuna.blog.model.co.MenuCO;
import com.leyuna.blog.model.co.MenuTreeCO;
import com.leyuna.blog.model.dto.MenuDTO;
import com.leyuna.blog.model.query.MenuQuery;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-07-31
 * 菜单服务
 */
@Service
public class MenuService {


    @Autowired
    private MenuDao menuDao;

    /**
     * 获得所有菜单信息
     *
     * @return
     */
    public List<MenuTreeCO> getAllMenu() {
        List<MenuCO> menuCOS = menuDao.selectByCon(null, MenuCO.class);
        return this.processMenuList(menuCOS);
    }

    /**
     * 获得树形菜单 不带文章
     *
     * @return
     */
    public List<MenuTreeCO> getTreeMenuList() {
        MenuQuery build = MenuQuery.builder().menuPosition(MenuPositionEnum.NAV_MENU.getCode()).build();
        List<MenuCO> menus = menuDao.selectByCon(build, MenuCO.class);
        return this.processMenuList(menus);
    }

    public Integer saveMenu(MenuDTO menuDTO) {
        MenuDO menuDO = new MenuDO();
        BeanUtil.copyProperties(menuDTO, menuDO);
        menuDao.insertOrUpdate(menuDO);
        return menuDO.getMenuId();
    }

    public void deleteMenu(MenuQuery query) {
        Integer menuId = query.getMenuId();
        MenuQuery menuQuery = MenuQuery.builder().menuParentId(menuId).build();
        List<MenuDO> menuDOS = menuDao.selectByCon(menuQuery);
        AssertUtil.isFalse(CollectionUtil.isNotEmpty(menuDOS),"子节点未删除完");
        menuDao.deleteById(menuId);
    }

    /**
     * 加工菜单集合 得到父子级菜单列表
     *
     * @param menuList
     * @return
     */
    private List<MenuTreeCO> processMenuList(List<MenuCO> menuList) {
        Map<String, MenuTreeCO> map = new HashMap();
        Stack<MenuTreeCO> stack = new Stack<>();
        menuList.stream().forEach(menu -> {
            MenuTreeCO menuTreeCO = new MenuTreeCO();
            BeanUtil.copyProperties(menu, menuTreeCO);
            menuTreeCO.setId(menu.getMenuId());
            menuTreeCO.setValue(menu.getMenuId());
            menuTreeCO.setLabel(menu.getMenuName());

            MenuTreeCO menuCO = map.get(menu.getMenuParentId());
            if (ObjectUtil.isNotNull(menuCO)) {
                //如果有父级菜单
                List<MenuTreeCO> childrenMenu = menuCO.getChildren();
                if (CollectionUtil.isEmpty(childrenMenu)) {
                    childrenMenu = new ArrayList<>();
                    menuCO.setChildren(childrenMenu);
                }
                childrenMenu.add(menuTreeCO);
            } else {
                if (ObjectUtil.isNotNull(menu.getMenuParentId()) && 0 != Integer.parseInt(menu.getMenuParentId())) {
                    stack.add(menuTreeCO);
                }
            }
            map.put(menu.getMenuId(), menuTreeCO);
        });

        //处理子菜单 先与父菜单顺序情况
        while (!stack.isEmpty()) {
            MenuTreeCO pop = stack.pop();
            MenuTreeCO menuCO = map.get(pop.getMenuParentId());
            if(ObjectUtil.isNotNull(menuCO)){
                //如果有父级菜单
                List<MenuTreeCO> childrenMenu = menuCO.getChildren();
                if (CollectionUtil.isEmpty(childrenMenu)) {
                    childrenMenu = new ArrayList<>();
                    menuCO.setChildren(childrenMenu);
                }
                childrenMenu.add(pop);
            }
        }

        //只留下顶级菜单结果集
        ArrayList<MenuTreeCO> menuCOS = CollectionUtil.newArrayList(map.values());
        List<MenuTreeCO> result = menuCOS.stream().filter(menuCO ->
                ObjectUtil.isNull(menuCO.getMenuParentId())
        ).collect(Collectors.toList());
        return result;
    }
}
