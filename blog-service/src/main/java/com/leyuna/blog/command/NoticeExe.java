package com.leyuna.blog.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leyuna.blog.co.WebHistoryCO;
import com.leyuna.blog.domain.WebHistoryE;
import com.leyuna.blog.util.CollectionUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pengli
 * @create 2021-09-10 15:48
 * 操作公告指令
 */
@Service
public class NoticeExe {


    /**
     * 分页查询网站更新历史
     * @param index
     * @param size
     * @return
     */
    @Cacheable(cacheNames = "getWebHistory")
    public IPage<WebHistoryCO> getWebHistory(Integer index, Integer size){
        IPage<WebHistoryCO> webHistoryIPage = WebHistoryE.queryInstance().getGateway().
                selectByConOrderPage(new WebHistoryCO(), index , size ,0);
        return webHistoryIPage;
    }

    public WebHistoryCO getWebHistoryById(String id){
        List<WebHistoryCO> webHistoryCOS = WebHistoryE.queryInstance().setId(id).selectByCon();
        return CollectionUtil.getFirst(webHistoryCOS);
    }

    /**
     * 分页查询网站更新历史 模糊查询
     * @param index
     * @param size
     * @return
     */
    @Cacheable(cacheNames = "getWebHistory")
    public IPage<WebHistoryCO> getWebHistory(Integer index, Integer size,String conditionName){
        IPage<WebHistoryCO> webHistoryIPage = WebHistoryE.queryInstance().getGateway().
                selectByLikeNamePage(index,size,conditionName);
        //封装结果集
        return webHistoryIPage;
    }

    /**
     * 添加网站历史
     * @param webHistoryDTO
     * @return
     */
    public boolean addHistory(WebHistoryE webHistoryDTO){
        webHistoryDTO.setCreateTime(LocalDateTime.now());
        WebHistoryCO save = webHistoryDTO.save();
        return save!=null;
    }


    public boolean updateWebHis(WebHistoryE webHistoryDTO){
        boolean update = webHistoryDTO.update();
        return update;
    }
}
