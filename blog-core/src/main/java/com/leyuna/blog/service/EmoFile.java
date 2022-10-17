package com.leyuna.blog.service;

import cn.hutool.core.util.StrUtil;
import com.leyuna.blog.baseinterface.FileInterface;
import com.leyuna.blog.constant.enums.UploadFileTypeEnum;
import com.leyuna.blog.dao.FileDao;
import com.leyuna.blog.dao.repository.entry.FileDO;
import com.leyuna.blog.model.constant.FileResponse;
import com.leyuna.blog.model.dto.FileDTO;
import com.leyuna.blog.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-20
 */
@Service
public class EmoFile implements FileInterface {

    @Autowired
    private OssService ossService;
    @Autowired
    private FileDao fileDao;

    @Override
    public FileResponse uploadFile(FileDTO fileDTO) {
        String imageUrl = ossService.uploadFile(fileDTO.getFile(), "image/emo");
        if(StrUtil.isBlank(imageUrl)){
            return null;
        }
        FileDO fileDO = new FileDO();
        fileDO.setFileMd5(fileDTO.getMd5Code());
        fileDO.setFileUrl(imageUrl);
        fileDO.setFileType(UploadFileTypeEnum.EMO_IMG.getCode());
        boolean addFile = fileDao.insertOrUpdate(fileDO);
        AssertUtil.isTrue(addFile);
        return FileResponse.builder().url(imageUrl).md5Code(fileDTO.getMd5Code()).build();
    }
}
