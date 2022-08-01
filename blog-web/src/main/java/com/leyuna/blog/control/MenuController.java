package com.leyuna.blog.control;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyuna.blog.model.co.MenuCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-07-31
 * 菜单控制器
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获得所有菜单
     *
     * @return
     */
    @GetMapping("/getAllMenu")
    public DataResponse getAllMenu() {
        List<MenuCO> allMenu = menuService.getAllMenu();
        List<MenuCO> menuCOS = processMenuList(allMenu);
        return DataResponse.of(menuCOS);
    }

    /**
     * 加工菜单集合 得到父子级菜单列表
     *
     * @param menuList
     * @return
     */
    private List<MenuCO> processMenuList(List<MenuCO> menuList) {
        Map<String, MenuCO> map = new HashMap();
        Stack<MenuCO> stack = new Stack<>();
        menuList.stream().forEach(menu -> {
            MenuCO menuCO = map.get(menu.getMenuParentId());
            if (ObjectUtil.isNotNull(menuCO)) {
                //如果有父级菜单
                List<MenuCO> childrenMenu = menuCO.getChildrenMenu();
                if (CollectionUtil.isEmpty(childrenMenu)) {
                    childrenMenu = new ArrayList<>();
                    menuCO.setChildrenMenu(childrenMenu);
                }
                childrenMenu.add(menu);
            } else {
                if (0 != Integer.valueOf(menu.getMenuParentId())) {
                    stack.add(menu);
                }
            }
            map.put(menu.getMenuId(), menu);
        });

        //处理子菜单 先与父菜单顺序情况
        while(!stack.isEmpty()){
            MenuCO pop = stack.pop();
            MenuCO menuCO = map.get(pop.getMenuParentId());

            //如果有父级菜单
            List<MenuCO> childrenMenu = menuCO.getChildrenMenu();
            if (CollectionUtil.isEmpty(childrenMenu)) {
                childrenMenu = new ArrayList<>();
                menuCO.setChildrenMenu(childrenMenu);
            }
            childrenMenu.add(pop);
        }

        //只留下顶级菜单结果集
        ArrayList<MenuCO> menuCOS = CollectionUtil.newArrayList(map.values());
        List<MenuCO> result = menuCOS.stream().filter(menuCO -> {
            return "0".equals(menuCO.getMenuParentId());
        }).collect(Collectors.toList());
        return result;
    }
}
