package com.leyuna.blog.control;

import com.leyuna.blog.model.co.MenuCO;
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
     * 获得所有菜单
     * @return
     */
    @GetMapping("/getAllMenu")
    public DataResponse getAllMenu(){
        List<MenuCO> allMenu = menuService.getAllMenu();
        return DataResponse.of(allMenu);
    }
}
