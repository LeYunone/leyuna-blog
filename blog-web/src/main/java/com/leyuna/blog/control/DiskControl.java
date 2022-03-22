package com.leyuna.blog.control;

import com.leyuna.blog.bean.blog.DataResponse;
import com.leyuna.blog.bean.disk.FileQueryBean;
import com.leyuna.blog.co.disk.FileInfoCO;
import com.leyuna.blog.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author pengli
 * @create 2021-12-28 14:01
 * 云盘模型控制器
 *
 * AOP完成用户权限校验
 */
@RestController
@RequestMapping("/disk")
public class DiskControl {

    @Autowired
    private DiskService DiskService;

    /**
     * 获取当前用户的云盘信息
     * @return
     */
    @GetMapping("/getDiskInfo")
    public DataResponse getDiskInfo(FileQueryBean queryBean){
        //开始组装云盘初始信息源
        return DiskService.selectFile(queryBean);
    }

    /**
     * 上传文件
     * @return
     */
    @PostMapping("/uploadFile")
    public DataResponse uploadFile(List<MultipartFile> file, String saveTime){
        return DiskService.uploadFile(file,saveTime);
    }

    /**
     * 请求上传文件
     * @return
     */
    @PostMapping("/requestSaveFile")
    public DataResponse requestSaveFile(List<MultipartFile> file){
        return DiskService.requestSaveFile(file);
    }

    /**
     * 下载文件
     * @param fileId
     * @param response
     */
    @GetMapping(value = "/downFile")
    public void downFile(String fileId, HttpServletResponse response){
        FileInfoCO fileInfoCO = DiskService.downloadFile(fileId);
        responseFile(fileInfoCO,response);
    }

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    @GetMapping(value = "/deleteFile")
    public DataResponse deleteFile(String fileId){
        return DiskService.deleteFile(fileId);
    }

    private void responseFile(FileInfoCO fileInfoCO,HttpServletResponse response){
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            //设置返回文件信息
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileInfoCO.getName(), "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            os = response.getOutputStream();
            bis = new BufferedInputStream(new ByteArrayInputStream(fileInfoCO.getBase64File()));
            while(bis.read(buffer) != -1){
                os.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null) {
                    bis.close();
                }
                if(os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
