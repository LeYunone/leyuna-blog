package com.leyuna.blog.service;

import com.leyuna.blog.dao.MenuDao;
import com.leyuna.blog.model.co.MenuCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return
     */
    public List<MenuCO> getAllMenu(){
        List<MenuCO> menuCOS = menuDao.selectByCon(null, MenuCO.class);
        return menuCOS;
    }
}
