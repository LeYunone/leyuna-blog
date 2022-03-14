package com.leyuna.blog.control;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.constant.ServerCode;
import com.leyuna.blog.error.SystemErrorEnum;
import com.leyuna.blog.service.TouristService;
import com.leyuna.blog.service.UserService;
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
 *  服务器相关控制器  比如文件服务器
 */
@RestController
@RequestMapping("/server")
public class ServerControl{
    @Autowired
    private UserService userService;
    @Autowired
    private CacheExe cacheExe;
    @Autowired
    private TouristService touristDomain;
    @PostMapping("/updownimg")
    public DataResponse upDownImgToServer(MultipartFile file) {
        userService.checkLock();
        //上传服务器
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean b = UpLoadUtil.imgUpLoadFromClient(file,format);
        if(b){
            return DataResponse.of(format+"/"+file.getOriginalFilename());
        }else{
            return DataResponse.buildFailure(SystemErrorEnum.UPLOCAD_IMG_FAIL.getMsg());
        }
    }
    
    @RequestMapping("/clearCache")
    public DataResponse clearCache(String name){
        if(ServerCode.SERVER_NAME.equals(name)){
            cacheExe.clearAllCache();
            return DataResponse.buildSuccess();
        }
        return DataResponse.buildFailure("暗号错啦，错啦！！！");
    }

    /**
     * 游客上传图片
     * @param file
     * @return
     */
    @PostMapping("/tourist/upImg")
    public DataResponse touristDoUpImg( MultipartFile file, HttpServletRequest request){
        String remoteAddr = ServerUtil.getClientIp(request);
        return touristDomain.touristDoUpImg(file,remoteAddr);
    }
}
