//package com.leyuna.blog.control;
//
//import com.leyuna.blog.model.constant.DataResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//
///**
// * @author pengli
// * @create 2021-12-28 14:01
// * 云盘模型控制器
// *
// * AOP完成用户权限校验
// */
//@RestController
//@RequestMapping("/disk")
//public class DiskControl {
//
//    @Autowired
//    private com.leyuna.blog.service.DiskService DiskService;
//
//    /**
//     * 获取当前用户的云盘信息
//     * @return
//     */
//    @GetMapping("/getDiskInfo")
//    public DataResponse getDiskInfo(FileQueryBean queryBean){
//        return DiskService.selectFile(queryBean);
//    }
//
//    /**
//     * 请求上传文件
//     * @return
//     */
//    @PostMapping("/requestSaveFile")
//    public DataResponse requestSaveFile(MultipartFile file){
//        return DiskService.requestSaveFile(file);
//    }
//
//    /**
//     * 下载文件
//     * @param fileId
//     * @param response
//     */
//    @GetMapping(value = "/downFile")
//    public void downFile(String fileId, HttpServletResponse response){
//        FileInfoCO fileInfoCO = DiskService.downloadFile(fileId);
//        responseFile(fileInfoCO,response);
//    }
//
//    /**
//     * 删除文件
//     * @param fileId
//     * @return
//     */
//    @GetMapping(value = "/deleteFile")
//    public DataResponse deleteFile(String fileId){
//        return DiskService.deleteFile(fileId);
//    }
//
//    /**
//     * 返回页面文件资料 TODO  待扩展，返回文件下载的进度条
//     * @param fileInfoCO
//     * @param response
//     */
//    private void responseFile(FileInfoCO fileInfoCO,HttpServletResponse response){
//        byte[] buffer = new byte[1024];
//        BufferedInputStream bis = null;
//        //输出流
//        OutputStream os = null;
//        try {
//            //设置返回文件信息
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileInfoCO.getName(), "UTF-8"));
//            response.setContentType("application/octet-stream");
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//            os = response.getOutputStream();
//            bis = new BufferedInputStream(new ByteArrayInputStream(fileInfoCO.getBase64File()));
//            //写入文件
//            while(bis.read(buffer) != -1){
//                os.write(buffer);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //关流了
//            try {
//                if(bis != null) {
//                    bis.close();
//                }
//                if(os != null) {
//                    os.flush();
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
