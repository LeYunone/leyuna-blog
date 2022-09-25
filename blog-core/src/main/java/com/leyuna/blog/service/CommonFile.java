package com.leyuna.blog.service;

import com.leyuna.blog.baseinterface.FileInterface;
import com.leyuna.blog.model.constant.FileResponse;
import com.leyuna.blog.model.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-20
 */
@Service
public class CommonFile implements FileInterface {

    @Autowired
    private OssService ossService;

    @Override
    public FileResponse uploadFile(FileDTO fileDTO) {

        String imageUrl = ossService.uploadFile(fileDTO.getFile(), "image");
        return FileResponse.builder().url(imageUrl).build();
    }
}
