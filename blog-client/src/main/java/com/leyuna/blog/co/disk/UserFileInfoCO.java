package com.leyuna.blog.co.disk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author pengli
 * @create 2022-03-22 09:33
 */
@Getter
@Setter
@ToString
public class UserFileInfoCO {

    /**
     * 用户内存总量
     */
    private Double fileTotal;

    private String fileTotalStr;

    /**
     * 用户文件
     */
    private List<FileInfoCO> fileinfos;


}
