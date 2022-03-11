package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.WebHistoryCO;
import com.leyuna.blog.entry.WebHistory;
import com.leyuna.blog.gateway.WebHistoryGateway;
import com.leyuna.blog.repository.mapper.WebHistoryMapper;
import com.leyuna.blog.util.TransformationUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * (WebHistory)原子服务
 *
 * @author pengli
 * @since 2021-08-26 15:41:38
 */
@Service
public class WebHistoryRepository extends BaseRepository<WebHistoryMapper, WebHistory , WebHistoryCO>
        implements WebHistoryGateway {

    @Override
    public Page<WebHistoryCO> selectByLikeNamePage(Integer index, Integer size, String conditionName) {
        Page page=new Page(index,size);
        IPage<WebHistory> Page = this.page(page, new QueryWrapper<WebHistory>().lambda().
                like(StringUtils.isNotBlank(conditionName),WebHistory::getTitle,conditionName)
                .orderByDesc(WebHistory::getCreateTime));
        return TransformationUtil.copyToPage(Page,WebHistoryCO.class);
    }

}

