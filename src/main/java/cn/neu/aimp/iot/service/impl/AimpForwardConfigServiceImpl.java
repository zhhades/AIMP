package cn.neu.aimp.iot.service.impl;

import cn.neu.aimp.iot.dao.AimpForwardConfigDao;
import cn.neu.aimp.iot.entity.AimpForwardConfig;
import cn.neu.aimp.iot.service.AimpForwardConfigService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AimpForwardConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-11-28 12:34:12
 */
@Service("aimpForwardConfigService")
@Setter
@Transactional
public class AimpForwardConfigServiceImpl implements AimpForwardConfigService {
    @Resource
    private AimpForwardConfigDao aimpForwardConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    @Override
    public AimpForwardConfig queryById(String ip) {
        return this.aimpForwardConfigDao.queryById(ip);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AimpForwardConfig> queryAllByLimit(int offset, int limit) {
        return this.aimpForwardConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param aimpForwardConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpForwardConfig insert(AimpForwardConfig aimpForwardConfig) {
        this.aimpForwardConfigDao.insert(aimpForwardConfig);
        return aimpForwardConfig;
    }

    /**
     * 修改数据
     *
     * @param aimpForwardConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpForwardConfig update(AimpForwardConfig aimpForwardConfig) {
        this.aimpForwardConfigDao.update(aimpForwardConfig);
        return this.queryById(aimpForwardConfig.getIp());
    }

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ip) {
        return this.aimpForwardConfigDao.deleteById(ip) > 0;
    }
}