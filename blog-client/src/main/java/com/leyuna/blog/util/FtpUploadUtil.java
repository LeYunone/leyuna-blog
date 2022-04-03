package com.leyuna.blog.util;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author pengli
 * @create 2022-03-28 13:41
 */
@Component
public class FtpUploadUtil {

    //    @Value("${server.hostname:www.leyuna.xyz}")
    @Value(("114.132.218.2"))
    private String hostName;
    //    @Value("${server.upload.port:21}")
    @Value("21")
    private int port;
    @Value("${server.username:Administrator}")
    private String username;
    @Value("${server.password:@Aa365627310}")
    private String password;

    /**
     * 上传
     *
     * @param workingPath 服务器的工作目
     * @param inputStream 要上传文件的输入流
     * @param saveName    设置上传之后的文件名
     * @return
     */
    public boolean upload (String workingPath,
                           InputStream inputStream, String saveName) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.enterLocalPassiveMode();
        //1 测试连接
        boolean connect = connect(ftpClient);
        if (connect) {
            try {
                //2 检查工作目录是否存在
                boolean hasTarget = ftpClient.changeWorkingDirectory("test");
                if (hasTarget) {
                    // 3 检查是否上传成功
                    boolean isUpload = storeFile(ftpClient, saveName, inputStream);
                    if (isUpload) {
                        flag = true;
                        disconnect(ftpClient);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                disconnect(ftpClient);
            }
        }
        return flag;
    }

    /**
     * 断开连接
     *
     * @param ftpClient
     * @throws Exception
     */
    public void disconnect (FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试是否能连接
     *
     * @param ftpClient
     * @return 返回真则能连接
     */
        public boolean connect (FTPClient ftpClient) {

        boolean flag = false;
        try {
            //ftp初始化的一些参数
            ftpClient.connect(hostName, port);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            if (ftpClient.login(username, password)) {
                flag = true;
            } else {
                try {
                    disconnect(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param saveName        全路径。如/home/public/a.txt
     * @param fileInputStream 要上传的文件流
     * @return
     */
    public boolean storeFile (FTPClient ftpClient, String saveName, InputStream fileInputStream) {
        boolean flag = false;
        try {
            if (ftpClient.storeFile(saveName, fileInputStream)) {
                flag = true;
                disconnect(ftpClient);
            }
        } catch (IOException e) {
            disconnect(ftpClient);
            e.printStackTrace();
        }
        return flag;
    }
}
