package com.blog.control;

import com.blog.api.command.CacheExe;
import com.blog.api.constant.ServerCode;
import com.blog.api.domain.UserDomain;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import com.blog.util.EncodeUtil;
import com.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pengli
 * @create 2021-08-30 16:42
 *  服务器相关控制器  比如文件服务器
 */
@RestController
@RequestMapping("/server")
public class ServerControl extends SysBaseControl {
    @Autowired
    private UserDomain userDomain;
    @Autowired
    private CacheExe cacheExe;
    @PostMapping("/updownimg")
    public ResponseBean upDownImgToServer( MultipartFile file) {
        userDomain.checkLock();
        //上传服务器
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean b = UpLoadUtil.imgUpLoadFromClient(file,format);
        if(b){
            return successResponseBean(format+"/"+file.getOriginalFilename());
        }else{
            return failResponseBean(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
        }
    }

    /**
     * 游客上传图片
     * @param file
     * @return
     */
    @PostMapping("/tourist/upImg")
    public ResponseBean touristDoUpImg(MultipartFile file, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        boolean b = UpLoadUtil.imgUpLoadFromClientCustomName(file, remoteAddr);
        if(b){
            //拼装图片位置


            //加入今天的缓存中
            cacheExe.setCacheKey(remoteAddr, ServerCode.SERVER_HEAD_IMG_ADDR,43200);
        }
    }
}
