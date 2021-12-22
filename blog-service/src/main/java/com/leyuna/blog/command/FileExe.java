package com.leyuna.blog.command;

import com.blog.daoservice.dao.TouristHeadDao;
import com.blog.daoservice.entry.TouristHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.CollectionUtil;

import java.util.List;

/**
 * @author pengli
 * @create 2021-09-17 11:16
 *  文件相关表操作指令
 */
@Service
public class FileExe {

    @Autowired
    private TouristHeadDao touristHeadDao;

    public String getTouristHead(String ip){
        List<TouristHead> touristHeads = touristHeadDao.selectByCon(TouristHead.builder().ip(ip).build());
        TouristHead first = CollectionUtil.getFirst(touristHeads);
        if(null!=first){
            return first.getHead();
        }
        return null;
    }

    public boolean addTouristHead(String head,String ip){
        TouristHead touristHead=TouristHead.builder().head(head).ip(ip).build();
        boolean save = touristHeadDao.save(touristHead);
        return save;
    }

    public boolean updateTouristHead(String head,String ip){
        boolean b = touristHeadDao.updateHead(ip, head);
        return b;
    }
}
