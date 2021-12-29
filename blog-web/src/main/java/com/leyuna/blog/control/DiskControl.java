package com.leyuna.blog.control;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.leyuna.blog.bean.blog.ResponseBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.DiskCO;
import com.leyuna.blog.error.UserAsserts;
import com.leyuna.blog.service.file.DiskDomain;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengli
 * @create 2021-12-28 14:01
 * 云盘模型控制器
 */
@RestController
@RequestMapping("/disk")
public class DiskControl {

    @Autowired
    private DiskDomain diskDomain;

    /**
     * 获取当前用户的云盘信息
     * @return
     */
    @GetMapping("/getDiskInfo")
    public ResponseBean getDiskInfo(){
        //看这个用户的云盘是否登录
        SaSession session = StpUtil.getSession();
        AssertUtil.isFalse(ObjectUtils.isEmpty(session), UserAsserts.LOGINT_NOT.getMsg());
        UserCO user = (UserCO)session.get("user");
        AssertUtil.isFalse(ObjectUtils.isEmpty(user),UserAsserts.LOGINT_NOT.getMsg());
        if(ObjectUtils.isEmpty(user)){
            //直接驳回 返回前端登录展示
            return ResponseBean.buildFailure();
        }
        //开始组装云盘初始信息源
        DiskCO fileList = diskDomain.getFileList(user.getId());
        return ResponseBean.of(fileList);
    }
}
