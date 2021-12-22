package com.leyuna.blog.command;

import com.leyuna.blog.co.TouristHeadCO;
import com.leyuna.blog.domain.TouristHeadE;
import com.leyuna.blog.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pengli
 * @create 2021-09-17 11:16
 *  文件相关表操作指令
 */
@Service
public class FileExe {

    public String getTouristHead(String ip){
        List<TouristHeadCO> touristHeads = TouristHeadE.queryInstance().setIp(ip).selectByCon();
        TouristHeadCO first = CollectionUtil.getFirst(touristHeads);
        if(null!=first){
            return first.getHead();
        }
        return null;
    }

    public boolean addTouristHead(String head,String ip){
        TouristHeadCO save = TouristHeadE.queryInstance().setHead(head).setIp(ip).save();
        return true;
    }

    public boolean updateTouristHead(String head,String ip){
        boolean b = TouristHeadE.queryInstance().getGateway().updateHead(ip, head);
        return b;
    }
}
