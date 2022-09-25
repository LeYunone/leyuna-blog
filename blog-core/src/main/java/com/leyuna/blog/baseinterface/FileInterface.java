package com.leyuna.blog.baseinterface;

import com.leyuna.blog.model.constant.FileResponse;
import com.leyuna.blog.model.dto.FileDTO;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-21
 */
public interface FileInterface {

    FileResponse uploadFile(FileDTO fileDTO);
}
