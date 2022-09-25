package com.leyuna.blog.control;

import com.leyuna.blog.model.co.MenuTreeCO;
import com.leyuna.blog.model.constant.DataResponse;
import com.leyuna.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 获得所有菜单 包括文章目录
     *
     * @return
     */
    @GetMapping("/getTreeMenu")
    public DataResponse<List<MenuTreeCO>> getAllMenu() {
        List<MenuTreeCO> allMenu = menuService.getAllMenu();
        return DataResponse.of(allMenu);
    }

    @GetMapping("/getMenuTree")
    public DataResponse<List<MenuTreeCO>> getTreeMenu(){
        List<MenuTreeCO> treeMenuList = menuService.getTreeMenuList();
        return DataResponse.of(treeMenuList);
    }

}
