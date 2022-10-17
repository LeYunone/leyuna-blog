package com.leyuna.blog.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyuna.blog.baseinterface.FileInterface;
import com.leyuna.blog.constant.enums.UploadFileTypeEnum;
import com.leyuna.blog.dao.FileDao;
import com.leyuna.blog.dao.repository.entry.FileDO;
import com.leyuna.blog.model.co.FileCO;
import com.leyuna.blog.model.constant.FileResponse;
import com.leyuna.blog.model.dto.FileDTO;
import com.leyuna.blog.model.query.FileQuery;
import com.leyuna.blog.util.AssertUtil;
import com.leyuna.blog.util.MD5Util;
import com.leyuna.blog.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-28
 */
@Service
public class FileService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FileDao fileDao;

    private FileResponse repeatFileDTO(FileDTO fileDTO){

        String md5 = null;
        try {
            md5 = MD5Util.fileToMD5(fileDTO.getFile().getBytes());
        } catch (IOException e) {
        }
        fileDTO.setMd5Code(md5);
        FileQuery fileQuery = new FileQuery();
        fileQuery.setMd5Code(md5);
        FileDO file = fileDao.selectOne(fileQuery);
        if (ObjectUtil.isNotNull(file)) {
            return FileResponse.builder().url(file.getFileUrl()).md5Code(file.getFileMd5()).build();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public FileResponse uploadFiles(List<MultipartFile> files, Integer type) {
        AssertUtil.isFalse(CollectionUtil.isEmpty(files),"操作失败：上传文件为空");
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFiles(files);
        fileDTO.setType(UploadFileTypeEnum.getEnum(type));
        FileResponse fileResponse = this.repeatFileDTO(fileDTO);
        if(ObjectUtil.isNotNull(fileResponse)) return fileResponse;

        FileInterface fileService = SpringContextUtil.getBean(fileDTO.getType().getFileService());
        fileResponse = fileService.uploadFile(fileDTO);
        AssertUtil.isFalse(ObjectUtil.isNull(fileResponse), "操作失败：文件上传错误");
        return fileResponse;
    }

    public FileResponse uploadFile(MultipartFile file, Integer type) {

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFile(file);
        fileDTO.setType(UploadFileTypeEnum.getEnum(type));
        FileResponse fileResponse = this.repeatFileDTO(fileDTO);
        if(ObjectUtil.isNotNull(fileResponse)) return fileResponse;

        FileInterface fileService = SpringContextUtil.getBean(fileDTO.getType().getFileService());
        fileResponse = fileService.uploadFile(fileDTO);
        AssertUtil.isFalse(ObjectUtil.isNull(fileResponse), "操作失败：文件上传错误");
        return fileResponse;
    }

    public List<FileCO> getFiles(FileQuery fileQuery) {
        List<FileCO> fileCOS = fileDao.selectByCon(fileQuery, FileCO.class);
        return fileCOS;
    }
}
