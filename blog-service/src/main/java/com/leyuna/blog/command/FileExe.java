package com.leyuna.blog.command;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.co.blog.TouristHeadCO;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.domain.TouristHeadE;
import com.leyuna.blog.constant.enums.SystemErrorEnum;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.CollectionUtil;
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

    public DataResponse getTouristHead(String ip) {
        String head = ServerCode.SERVER_HEAD_IMG_DEFAULT;
        List<TouristHeadCO> touristHeads = TouristHeadE.queryInstance().setIp(ip).selectByCon();
        TouristHeadCO first = CollectionUtil.getFirst(touristHeads);
        if (null != first) {
            head = first.getHead();
        }
        return DataResponse.of(head);
    }

    public DataResponse uploadHeadImg(MultipartFile file,String value, String remoteAddr) {
        String fileName = file.getOriginalFilename();
        boolean b = UpLoadUtil.imgUpLoadFromClientCustomName(file, null, fileName);
        AssertUtil.isTrue(b, SystemErrorEnum.UPLOCAD_IMG_FAIL.getMsg());

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
