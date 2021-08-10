package com.blog.control;

import com.blog.bean.ResponseBean;
import com.blog.bean.ResponseCode;

/**
 * @author pengli
 * @create 2021-08-10 15:04
 *
 * 系统控制基类 - 提供一般方法
 */
public class SysBaseControl {

    /**
     * 返回成功参数的响应结果
     * @return
     */
    public ResponseBean successResponseBean(){
        ResponseBean code = ResponseBean.builder().code(ResponseCode.SUCCESS_CODE).build();
        return code;
    }


    /**
     * 返回成功参数的响应结果
     * @return
     */
    public ResponseBean failResponseBean(){
        ResponseBean code = ResponseBean.builder().code(ResponseCode.ERROR_CODE).build();
        return code;
    }
}
