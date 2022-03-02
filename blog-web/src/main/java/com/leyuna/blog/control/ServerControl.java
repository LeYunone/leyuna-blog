package com.leyuna.blog.control;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.command.CacheExe;
import com.leyuna.blog.constant.ServerCode;
import com.leyuna.blog.error.SystemAsserts;
import com.leyuna.blog.service.TouristDomain;
import com.leyuna.blog.service.UserDomain;
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
    private UserDomain userDomain;
    @Autowired
    private CacheExe cacheExe;
    @Autowired
    private TouristDomain touristDomain;
    @PostMapping("/updownimg")
    public ResponseBean upDownImgToServer(MultipartFile file) {
        userDomain.checkLock();
        //上传服务器
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean b = UpLoadUtil.imgUpLoadFromClient(file,format);
        if(b){
            return ResponseBean.of(format+"/"+file.getOriginalFilename());
        }else{
            return ResponseBean.buildFailure(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
        }
    }
    
    @RequestMapping("/clearCache")
    public ResponseBean clearCache(String name){
        if(ServerCode.SERVER_NAME.equals(name)){
            cacheExe.clearAllCache();
            return ResponseBean.buildSuccess();
        }
        return ResponseBean.buildFailure("暗号错啦，错啦！！！");
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
                return ResponseBean.of(cacheExe.getCacheByKey(remoteAddr+":head"));
            }else{
                String touristOldHead = touristDomain.getTouristOldHead(remoteAddr);
                if(StringUtils.isNotEmpty(touristOldHead)){
                    return ResponseBean.of(touristOldHead);
                }else{
                    return ResponseBean.of(ServerCode.SERVER_HEAD_IMG_DEFAULT);
                }
            }
        }else{
            String fileName=file.getOriginalFilename();
            boolean b = UpLoadUtil.imgUpLoadFromClientCustomName(file,null, fileName);
            if(b){
                //拼装图片位置
                String value=ServerCode.SERVER_HEAD_IMG_ADDR+fileName;
                //添加到数据库中
                boolean b1 = touristDomain.addOrUpdateHead(value, remoteAddr);
                if(!b1){
                    return ResponseBean.buildFailure(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
                }
                //加入今天的缓存中
                cacheExe.setCacheKey(remoteAddr+":head", value,43200);
                ResponseBean<String> of = ResponseBean.of(value);
                of.setMessage(remoteAddr);
                return of;
            }
        }
        return ResponseBean.buildFailure(SystemAsserts.UPLOCAD_IMG_FAIL.getMsg());
    }
}
