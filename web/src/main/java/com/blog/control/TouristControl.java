package com.blog.control;

import com.blog.bean.ResponseBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengli
 * @create 2021-09-14 16:57
 *
 * 游客操作 控制器   需要限制取ip限制他们的操作频率
 */
@RequestMapping("/tourist")
@RestController
public class TouristControl {

    @RequestMapping("/test")
    public ResponseBean test(){
        System.out.println(111);
        return null;
    }
}
