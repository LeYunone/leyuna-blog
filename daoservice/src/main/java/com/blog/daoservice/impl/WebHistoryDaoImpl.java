package com.blog.daoservice.impl;


import com.blog.daoservice.dao.WebHistoryDao;
import com.blog.daoservice.entry.WebHistory;
import com.blog.daoservice.mapper.WebHistoryMapper;
import org.springframework.stereotype.Service;

/**
 * (WebHistory)原子服务
 *
 * @author pengli
 * @since 2021-08-26 15:41:38
 */
@Service
public class WebHistoryDaoImpl extends SysBaseMpImpl<WebHistoryMapper, WebHistory> implements WebHistoryDao {
}

