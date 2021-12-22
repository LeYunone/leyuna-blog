package com.leyuna.blog.repository;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leyuna.blog.co.TouristHeadCO;
import com.leyuna.blog.entry.TouristHead;
import com.leyuna.blog.gateway.TouristHeadGateway;
import com.leyuna.blog.repository.mapper.TouristHeadMapper;
import org.springframework.stereotype.Service;

/**
 * (TouristHead)原子服务
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
@Service
public class TouristHeadRepository extends BaseRepository<TouristHeadMapper, TouristHead, TouristHeadCO>
        implements TouristHeadGateway {
    @Override
    public boolean updateHead(String ip, String head) {
        boolean update = this.update(new UpdateWrapper<TouristHead>().lambda().eq(TouristHead::getIp, ip).set(TouristHead::getHead, head));
        return update;
    }
}

