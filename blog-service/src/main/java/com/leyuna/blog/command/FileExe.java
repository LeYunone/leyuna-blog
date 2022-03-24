package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.TouristHeadCO;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.domain.TouristHeadE;
import com.leyuna.blog.util.UpLoadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public DataResponse uploadHeadImg(MultipartFile file, String value, String remoteAddr) {
        String path=ServerCode.SERVER_IMG_SAVE_PATH+"/avatar/";
        //上传图片
        UpLoadUtil.uploadFile(path,file);

        //添加到数据库中
        List<TouristHeadCO> touristHeadCOS = TouristHeadE.queryInstance().setIp(remoteAddr).selectByCon();
        if (CollectionUtils.isEmpty(touristHeadCOS)) {
            //新增
            TouristHeadE.queryInstance().setIp(remoteAddr).setHead(value).save();
        } else {
            //更新
            TouristHeadCO touristHeadCO = touristHeadCOS.get(0);
            TouristHeadE.queryInstance().setHead(value).setId(touristHeadCO.getId()).update();
        }
        DataResponse<String> of = DataResponse.of(value);
        of.setMessage(remoteAddr);
        return of;
    }
}
