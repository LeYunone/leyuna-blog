package com.blog.api.command;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.api.dto.BlogDTO;
import com.blog.api.dto.WebHistoryDTO;
import com.blog.daoservice.dao.WebHistoryDao;
import com.blog.daoservice.entry.Blog;
import com.blog.daoservice.entry.WebHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import util.TransformationUtil;

import java.time.LocalDateTime;

/**
 * @author pengli
 * @create 2021-09-10 15:48
 * 操作公告指令
 */
@Service
public class NoticeExe {


    @Autowired
    private WebHistoryDao historyDao;

    /**
     * 分页查询网站更新历史
     * @param index
     * @param size
     * @return
     */
    @Cacheable(cacheNames = "getWebHistory")
    public Page<WebHistoryDTO> getWebHistory(Integer index, Integer size){
        Page<WebHistory> page=new Page<>(index,size);
        IPage<WebHistory> webHistoryIPage = historyDao.selectByConPageOrderCreateTime(new WebHistory(), page,0);

        //封装结果集
        Page<WebHistoryDTO> historyDTOS=new Page<>(index,size,page.getTotal());
        historyDTOS.setRecords(TransformationUtil.copyToLists(webHistoryIPage.getRecords(), WebHistoryDTO.class));
        return historyDTOS;
    }

    public WebHistoryDTO getWebHistoryById(Integer id){
        WebHistory webHistory = historyDao.selectById(id);
        return TransformationUtil.copyToDTO(webHistory,WebHistoryDTO.class);
    }

    /**
     * 分页查询网站更新历史 模糊查询
     * @param index
     * @param size
     * @return
     */
    @Cacheable(cacheNames = "getWebHistory")
    public Page<WebHistoryDTO> getWebHistory(Integer index, Integer size,String conditionName){
        Page<WebHistory> page=new Page<>(index,size);
        IPage<WebHistory> webHistoryIPage = historyDao.selectByLikeNamePage(index,size,conditionName);
        //封装结果集
        Page<WebHistoryDTO> historyDTOS=new Page<>(index,size,page.getTotal());
        historyDTOS.setRecords(TransformationUtil.copyToLists(webHistoryIPage.getRecords(),WebHistoryDTO.class));
        return historyDTOS;
    }

    /**
     * 添加网站历史
     * @param webHistoryDTO
     * @return
     */
    public boolean addHistory(WebHistoryDTO webHistoryDTO){
        webHistoryDTO.setCreateTime(LocalDateTime.now());
        boolean save = historyDao.save(TransformationUtil.copyToDTO(webHistoryDTO, WebHistory.class));
        return save;
    }


    public boolean updateWebHis(WebHistoryDTO webHistoryDTO){
        boolean b = historyDao.updateById(TransformationUtil.copyToDTO(webHistoryDTO, WebHistory.class));
        return b;
    }
}
