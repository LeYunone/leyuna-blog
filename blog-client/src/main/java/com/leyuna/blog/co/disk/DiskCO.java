package com.leyuna.blog.co.disk;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author pengli
 * @create 2021-12-28 14:51
 * 云盘出参
 */
@Getter
@Setter
@ToString
public class DiskCO {

    private List<FileInfoCO> imgList;

    private List<FileInfoCO> musicList;

    private List<FileInfoCO> videoList;

    private List<FileInfoCO> wordList;

    private List<FileInfoCO> otherList;

    private List<FileInfoCO> fileList;

    private Double fileTotalSize;

    private Long fileCount;
}
