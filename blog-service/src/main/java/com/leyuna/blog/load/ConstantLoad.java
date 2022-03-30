package com.leyuna.blog.load;

import com.leyuna.blog.constant.code.ServerCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author pengli
 * @create 2022-03-29 14:20
 * 常数加载器
 */
@Component
public class ConstantLoad {

    @Value("${SERVER_HEAD_IMG_ADDR:https://www.leyuna.xyz/image/avatar/}")
    private String SERVER_HEAD_IMG_ADDR;

    @Value("${SERVER_HEAD_IMG_DEFAULT:https://www.leyuna.xyz/image/avatar/default.jpg}")
    private String SERVER_HEAD_IMG_DEFAULT;

    @Value("${SERVER_HEAD_IMG_ADMIN:https://www.leyuna.xyz/image/avatar/admin.jpg}")
    private String SERVER_HEAD_IMG_ADMIN;

    @Value("${SERVER_IMG_SAVE_PATH:https://www.leyuna.xyz/image/}")
    private String SERVER_IMG_SAVE_PATH;

    @Value("${SERVER_EMO_SAVE_PATH:https://www.leyuna.xyz/image/emo/}")
    private String SERVER_EMO_SAVE_PATH;

    @Value("${DIR_SAVE_PATH:C:/dir/blogDir/}")
    private String DIR_SAVE_PATH;

    @Value("${EMO_SAVE_PATH:C:/img/emo/}")
    private String EMO_SAVE_PATH;

    @Value("${IMG_SAVE_PATH:C:/img/}")
    private String IMG_SAVE_PATH;

    @Value("${SERVER_NAME:LeYuna}")
    private String SERVER_NAME;

    @Value("${SERVER_COVER_SAVE_PATH:https://www.leyuna.xyz/image/cover}")
    private String SERVER_COVER_SAVE_PATH;

    public void loading () {
        //封面地址
        ServerCode.SERVER_COVER_SAVE_PATH = this.SERVER_COVER_SAVE_PATH;
        //头像存储域名
        ServerCode.SERVER_HEAD_IMG_ADDR = this.SERVER_HEAD_IMG_ADDR;
        //默认头像
        ServerCode.SERVER_HEAD_IMG_DEFAULT = this.SERVER_HEAD_IMG_DEFAULT;
        //站长头像
        ServerCode.SERVER_HEAD_IMG_ADMIN = this.SERVER_HEAD_IMG_ADMIN;
        //图片存储域名
        ServerCode.SERVER_IMG_SAVE_PATH = this.SERVER_IMG_SAVE_PATH;
        //表情包存储域名
        ServerCode.SERVER_EMO_SAVE_PATH = this.SERVER_EMO_SAVE_PATH;
        //搜索库存储路径
        ServerCode.DIR_SAVE_PATH = this.DIR_SAVE_PATH;
        //表情包存储路径
        ServerCode.EMO_SAVE_PATH = this.EMO_SAVE_PATH;
        //图片存储路径
        ServerCode.IMG_SAVE_PATH = this.IMG_SAVE_PATH;
        //服务器名
        ServerCode.SERVER_NAME = this.SERVER_NAME;
    }
}
