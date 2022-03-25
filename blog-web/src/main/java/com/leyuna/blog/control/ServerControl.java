package com.leyuna.blog.control;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.constant.code.ServerCode;
import com.leyuna.blog.constant.enums.UploadImgTypeEnum;
import com.leyuna.blog.service.TouristService;
import com.leyuna.blog.util.ServerUtil;
import com.leyuna.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pengli
 * @create 2021-08-30 16:42
 * 服务器相关控制器  比如文件服务器
 */
@RestController
@RequestMapping("/server")
public class ServerControl {
    @Autowired
    private CacheExe cacheExe;
    @Autowired
    private TouristService touristService;

    @PostMapping("/updownimg")
    public DataResponse upDownImgToServer (MultipartFile file, String type) {
        String path = "";
        String message = "";
        //表情包上传
        if (UploadImgTypeEnum.EMO_IMG.getValue().equals(type)) {
            path = ServerCode.EMO_SAVE_PATH;
            message = ServerCode.SERVER_EMO_SAVE_PATH + file.getOriginalFilename();
        }
        //一般图片上传
        if (UploadImgTypeEnum.IMAGE_IMG.getValue().equals(type)) {
            String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            path = ServerCode.IMG_SAVE_PATH + format;
            message = ServerCode.SERVER_IMG_SAVE_PATH + format + "/" + file.getOriginalFilename();
        }
        //上传服务器
        UpLoadUtil.uploadFile(path, file);
        return DataResponse.of(message);
    }

    @RequestMapping("/clearCache")
    public DataResponse clearCache (String name) {
        if (ServerCode.SERVER_NAME.equals(name)) {
            cacheExe.clearAllCache();
            return DataResponse.buildSuccess();
        }
        return DataResponse.buildFailure("暗号错啦，错啦！！！");
    }

    /**
     * 游客上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/tourist/upImg")
    public DataResponse touristDoUpImg (MultipartFile file, HttpServletRequest request) {
        String remoteAddr = ServerUtil.getClientIp(request);
        return touristService.touristDoUpImg(file, remoteAddr);
    }
}
