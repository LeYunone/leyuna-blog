package com.blog.daoservice.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.daoservice.dao.WebHistoryDao;
import com.blog.daoservice.entry.Type;
import com.blog.daoservice.entry.WebHistory;
import com.blog.daoservice.mapper.WebHistoryMapper;
import org.springframework.stereotype.Service;
import util.AssertUtil;
import util.ErrorMeassage;
import util.ObjectUtil;
import util.TransformationUtil;

import java.util.Map;

/**
 * (WebHistory)原子服务
 *
 * @author pengli
 * @since 2021-08-26 15:41:38
 */
@Service
public class WebHistoryDaoImpl extends SysBaseMpImpl<WebHistoryMapper, WebHistory> implements WebHistoryDao {

    @Override
    public IPage<WebHistory> selectByLikeNamePage(Integer index,Integer size, String conditionName) {
        Page page=new Page(index,size);
        IPage<WebHistory> iPage = this.page(page, new QueryWrapper<WebHistory>().lambda().
                like(WebHistory::getTitle,conditionName).orderByDesc(WebHistory::getCreateTime));
        return iPage;
    }

    @Override
    public WebHistory selecyById(Integer id){
        return this.baseMapper.selectById(id);
    }
}

