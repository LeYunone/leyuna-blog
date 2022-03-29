package com.leyuna.blog.rpc.command;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.bean.disk.UpFileBean;
import com.leyuna.blog.co.blog.UserCO;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.co.disk.UserFileInfoCO;
import com.leyuna.blog.rpc.service.LeyunaDiskRpcService;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author pengli
 * @create 2021-12-24 11:02
 */
@Component
public class DiskFileExe {

    @Autowired
    private LeyunaDiskRpcService leyunaDiskRpcService;

    @Value("${disk.max.memory:1}")
    private Long maxMemory;

    /**
     * 查询文件列表
     * @param queryBean
     * @return
     */
    public DataResponse<UserFileInfoCO> selectFile(FileQueryBean queryBean){
        UserCO user=(UserCO) StpUtil.getSession().get("user");
        queryBean.setUserId(user.getId());
        //查所有文件 0
        if(ObjectUtils.isNotEmpty(queryBean.getFileType()) && queryBean.getFileType()==0){
            queryBean.setFileType(null);
        }
        //请求disk服务查询
        DataResponse<Page<FileInfoCO>> userFileInfoCODataResponse = leyunaDiskRpcService.selectFile(queryBean);
        DataResponse<Double> doubleDataResponse = leyunaDiskRpcService.selectAllFileSize(user.getId());

        //校验数据
        resultValidate(doubleDataResponse);
        resultValidate(userFileInfoCODataResponse);
        
        UserFileInfoCO userFileInfoCO=new UserFileInfoCO();
        //设置文件列表
        userFileInfoCO.setFileinfos(userFileInfoCODataResponse.getData());
        //设置文件总内存
        double total = (doubleDataResponse.getData() / maxMemory) * 100;
        userFileInfoCO.setFileTotalStr(String.format("%.2f",total));
        return DataResponse.of(userFileInfoCO);
    }

    /**
     * 获得当前用户使用文件内存
     * @return
     */
    public DataResponse selectAllFileSize(){
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        String userId=user.getId();
        DataResponse<Double> doubleDataResponse = leyunaDiskRpcService.selectAllFileSize(userId);
        resultValidate(doubleDataResponse);
        Double data = doubleDataResponse.getData();
        double total = (data / maxMemory) * 100;
        return DataResponse.of(String.format("%.2f",total));
    }

    /**
     * 请求上传文件 判断其合法 disk服务内判断
     * @param file
     * @return
     */
    public DataResponse<Integer> requestSaveFile(List<MultipartFile> file){

        AssertUtil.isFalse(CollectionUtils.isEmpty(file),"操作失败：文件接收为空");
        //用户编号
        String userId = (String) StpUtil.getLoginId();

        DataResponse<Integer> integerDataResponse = leyunaDiskRpcService.requestSaveFile(userId, file.get(0));
        resultValidate(integerDataResponse);
        //0 是服务器内部处理文件    1 是需要在服务器生成文件
        return integerDataResponse;
    }

    /**
     * 上传保存文件
     * @param fileBean
     * @return
     */
    public DataResponse saveFile(UpFileBean fileBean){
        String userId = (String) StpUtil.getLoginId();
        MultipartFile multipartFile = fileBean.getFiles().get(0);
        String saveTime = fileBean.getSaveTime();
        DataResponse responseBean = leyunaDiskRpcService.saveFile(userId, multipartFile, saveTime);
        resultValidate(responseBean);
        return responseBean;
    }

    /**
     * id删除文件
     * @param id
     * @return
     */
    public DataResponse deleteFile(String id){
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        DataResponse responseBean = leyunaDiskRpcService.deleteFile(id, user.getId());
        return responseBean;
    }

    /**
     * 下载文件 取得的是文件信息
     * @param id
     * @return
     */
    public FileInfoCO downloadFile(String id){
        UserCO user=(UserCO)StpUtil.getSession().get("user");
        //获取文件信息
        DataResponse<FileInfoCO> fileInfoCODataResponse = leyunaDiskRpcService.downloadFile(id, user.getId());
        resultValidate(fileInfoCODataResponse);
        FileInfoCO data = fileInfoCODataResponse.getData();
        //data 中含有待下载文件的流形式
        return data;
    }

    public <T> T resultValidate(DataResponse<T> response, Object... obj) {
        if (response.isStatus()) {
            if (Objects.nonNull(response.getData())) {
                return response.getData();
            } else {
                return response.getData();
            }
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
}
