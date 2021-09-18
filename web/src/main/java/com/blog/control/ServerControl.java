package com.blog.control;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.blog.api.command.CacheExe;
import com.blog.api.constant.ServerCode;
import com.blog.api.domain.TouristDomain;
import com.blog.api.domain.UserDomain;
import com.blog.bean.ResponseBean;
import com.blog.error.SystemAsserts;
import com.blog.util.EncodeUtil;
import com.blog.util.ServerUtil;
import com.blog.util.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @Autowired
    private TouristDomain touristDomain;
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
    public ResponseBean touristDoUpImg( MultipartFile file, HttpServletRequest request){
        String remoteAddr = ServerUtil.getClientIp(request);

        if(file==null){
            //如果文件为空，三种情况，一是使用今天换头像的缓存，二是用以前的照片，三是使用系统默认的照片
            if(cacheExe.hasCacheByKey(remoteAddr+":head")){
                return successResponseBean(cacheExe.getCacheByKey(remoteAddr+":head"));
            }else{
                String touristOldHead = touristDomain.getTouristOldHead(remoteAddr);
                if(StringUtils.isNotEmpty(touristOldHead)){
                    return successResponseBean(touristOldHead);
                }else{
                    return successResponseBean(ServerCode.SERVER_HEAD_IMG_DEFAULT);
                }
            }
        }else{
            String fileName=file.getOriginalFilename();
            boolean b = UpLoadUtil.imgUpLoadFromClientCustomName(file,null, fileName);
            if(b){
                //拼装图片位置
                String value=ServerCode.SERVER_HEAD_IMG_ADDR+fileName;
                //加入今天的缓存中
                cacheExe.setCacheKey(remoteAddr+":head", value,43200);
                //添加到数据库中
                boolean b1 = touristDomain.addOrUpdateHead(value, remoteAddr);
                if(!b1){
                    cacheExe.clearCacheKey(remoteAddr+":head");
                    return failResponseBean(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
                }
                ResponseBean responseBean = successResponseBean(value);
                responseBean.setObjData(remoteAddr);
                return responseBean;
            }
        }
        return failResponseBean(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
    }
}
