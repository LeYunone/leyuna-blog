package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.entry.WebHistory;
import com.leyuna.blog.gateway.WebHistoryDao;
import com.leyuna.blog.repository.mapper.WebHistoryMapper;
import org.springframework.stereotype.Service;

/**
 * (WebHistory)原子服务
 *
 * @author pengli
 * @since 2021-08-26 15:41:38
 */
@Service
public class WebHistoryDaoImpl extends SysBaseMpImpl<WebHistoryMapper, WebHistory> implements WebHistoryDao {

    @Override
    public IPage<WebHistory> selectByLikeNamePage(Integer index, Integer size, String conditionName) {
        Page page=new Page(index,size);
        IPage<WebHistory> iPage = this.page(page, new QueryWrapper<WebHistory>().lambda().
                like(WebHistory::getTitle,conditionName).orderByDesc(WebHistory::getCreateTime));
        return iPage;
    }

}

