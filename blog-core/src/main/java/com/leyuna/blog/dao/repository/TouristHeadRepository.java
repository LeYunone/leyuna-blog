package com.leyuna.blog.dao.repository;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leyuna.blog.dao.TouristHeadDao;
import com.leyuna.blog.dao.repository.entry.TouristHeadDO;
import com.leyuna.blog.dao.repository.mapper.TouristHeadMapper;
import org.springframework.stereotype.Service;

/**
 * (TouristHead)原子服务
 *
 * @author
 * @since 2021-09-17 11:15:03
 */
@Service
public class TouristHeadRepository extends BaseRepository<TouristHeadMapper, TouristHeadDO>
        implements TouristHeadDao {
    @Override
    public boolean updateHead(String ip, String head) {
        boolean update = this.update(new UpdateWrapper<TouristHeadDO>().lambda().eq(TouristHeadDO::getIp, ip).set(TouristHeadDO::getHead, head));
        return update;
    }
}

