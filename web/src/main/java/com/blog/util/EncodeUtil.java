package com.blog.util;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author pengli
 * @create 2021-08-11 14:09
 *
 * 编码转换工具
 */
public class EncodeUtil {

    /**
     * 文件转字符串格式
     * @param path  文件路径
     * @return
     */
    public static String FileToString(String path){
        InputStream is=null;
        byte [] bytes = null;
        try {
            is=new FileInputStream(new File(path));
            /**
             * 一次性拉满
             */
            bytes=new byte[is.available()];
            is.read(bytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);//返回Base64编码过的字节数组字符串
    }

    /**
     * 将base64字符串 翻译成文件保存
     * @param base64
     * @return
     */
    public static boolean TransformerPhone(String base64){
        BASE64Decoder decoder=new BASE64Decoder();
        if(StringUtils.isEmpty(base64)){
            return false;
        }
        try {
            byte[] b=decoder.decodeBuffer(base64);
            //  处理异常编码
            for(int i=0;i<b.length;i++){
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            //生成文件
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
