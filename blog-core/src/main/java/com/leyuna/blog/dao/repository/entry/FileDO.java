package com.leyuna.blog.dao.repository.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-08-25
 */
@Getter
@Setter
@TableName("file")
public class FileDO {

    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    private String fileIdentif;

    private String fileOss;

    private String fileUrl;
}
