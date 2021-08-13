package com.blog.control;

import com.blog.bean.ResponseBean;
import com.blog.bean.ResponseCode;

import java.util.List;

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
     * 返回成功参数的响应结果 并且加参数
     * @return
     */
    public ResponseBean successResponseBean(Object o){
        ResponseBean code = ResponseBean.builder().code(ResponseCode.SUCCESS_CODE).build();
        if(o!=null){
            code=HandleInput(o,code);
        }
        return code;
    }

    /**
     * 返回成功参数的响应结果 并且加参数
     * @return
     */
    public ResponseBean failResponseBean(){
        ResponseBean code = ResponseBean.builder().code(ResponseCode.ERROR_CODE).build();
        return code;
    }

    /**
     * 返回成功参数的响应结果
     * @return
     */
    public ResponseBean failResponseBean(Object o){
        ResponseBean code = ResponseBean.builder().code(ResponseCode.ERROR_CODE).build();
        if(o!=null){
            code=HandleInput(o,code);
        }
        return code;
    }

    public ResponseBean HandleInput(Object o,ResponseBean res){
        if(o instanceof String){
            res.setSrcData(String.valueOf(o));
        }else if(o instanceof List){
            res.setListData((List)o);
        }else if(o instanceof StringBuilder){
            res.setSrcData(o.toString());
        }
        return res;
    }
}
