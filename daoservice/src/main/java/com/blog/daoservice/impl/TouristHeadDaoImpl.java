package com.blog.daoservice.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blog.daoservice.dao.TouristHeadDao;
import com.blog.daoservice.entry.TouristHead;
import com.blog.daoservice.mapper.TouristHeadMapper;
import org.springframework.stereotype.Service;

/**
 * (TouristHead)原子服务
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
@Service
public class TouristHeadDaoImpl extends SysBaseMpImpl<TouristHeadMapper, TouristHead> implements TouristHeadDao {
    @Override
    public boolean updateHead(String ip, String head) {
        boolean update = this.update(new UpdateWrapper<TouristHead>().lambda().eq(TouristHead::getIp, ip).set(TouristHead::getHead, head));
        return update;
    }
}

