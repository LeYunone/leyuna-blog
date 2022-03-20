package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.TouristHeadCO;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.domain.TouristHeadE;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author pengli
 * @create 2021-09-17 11:16
 * 文件相关表操作指令
 */
@Service
public class FileExe {

    /**
     * 获取ip用户对象历史头像
     * @param ip
     * @return
     */
    public DataResponse getTouristHead(String ip) {
        String head = ServerCode.SERVER_HEAD_IMG_DEFAULT;
        List<TouristHeadCO> touristHeads = TouristHeadE.queryInstance().setIp(ip).selectByCon();
        if(!CollectionUtils.isEmpty(touristHeads)){
            TouristHeadCO touristHeadCO = touristHeads.get(0);
            head=touristHeadCO.getHead();
        }
        return DataResponse.of(head);
    }
}
