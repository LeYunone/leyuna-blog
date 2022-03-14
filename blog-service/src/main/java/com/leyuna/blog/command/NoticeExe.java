package com.leyuna.blog.command;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.blog.NoticeBean;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author pengli
 * @create 2021-09-10 15:48
 * 操作公告指令
 */
@Service
public class NoticeExe {

    public DataResponse<WebHistoryCO> getWebHistoryById (String id) {
        WebHistoryCO webHistoryCO = WebHistoryE.queryInstance().setId(id).selectById();
        return DataResponse.of(webHistoryCO);
    }

    /**
     * 分页查询网站更新历史 模糊查询
     *
     * @param index
     * @param size
     * @return
     */
    @Cacheable(cacheNames = "getWebHistory")
    public DataResponse getWebHistory (Integer index, Integer size, String conditionName) {
        Page<WebHistoryCO> webHistoryPage = WebHistoryE.queryInstance().getGateway().
                selectByLikeNamePage(index, size, conditionName);
        //封装结果集
        return DataResponse.of(webHistoryPage);
    }

    /**
     * 添加网站历史
     *
     * @param noticeDTO
     * @return
     */
    public void addHistory (NoticeBean noticeDTO) {
        //0 网站更新公告
        WebHistoryCO save = WebHistoryE.of(noticeDTO).save();
        AssertUtil.isFalse(null == save, SystemErrorEnum.ADD_BLOG_FAIL.getMsg());
    }


    public boolean updateWebHis (NoticeBean webHistoryDTO) {
        boolean update = WebHistoryE.of(webHistoryDTO).update();
        AssertUtil.isTrue(update,SystemErrorEnum.UPDATE_BLOG_FAIL.getMsg());
        return update;
    }
}
