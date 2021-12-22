package com.leyuna.blog.control;


import com.leyuna.blog.bean.ResponseBean;
import com.leyuna.blog.bean.ResponseCode;
import org.springframework.data.domain.Page;

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
        }else if(o instanceof Page){
            res.setPage((Page)o);
        }else{
            res.setObjData(o);
        }
        return res;
    }

    /**
     * 封装分页对象  ， 默认取res里面的listdata
     * @param res
     * @return
     */
    public ResponseBean packPage(ResponseBean res){
        if(null==res){
            return null;
        }
        List<Object> listData = res.getListData();
        Page pageBean=new Page();
        pageBean.setTotal(listData.size());
        pageBean.setRecords(listData);
        res.setPage(pageBean);
        return res;
    }
}
