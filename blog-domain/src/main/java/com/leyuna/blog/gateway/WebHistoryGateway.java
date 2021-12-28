package com.leyuna.blog.gateway;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.co.blog.WebHistoryCO;

/**
 * (WebHistory)dao
 *
 * @author pengli
 * @since 2021-08-26 16:00:43
 */
public interface WebHistoryGateway extends BaseGateway<WebHistoryCO> {

    /**
     * 模糊查询根据标题
     * @param index
     * @param size
     * @param conditionName
     * @return
     */
    Page<WebHistoryCO> selectByLikeNamePage (Integer index, Integer size, String conditionName);
}

