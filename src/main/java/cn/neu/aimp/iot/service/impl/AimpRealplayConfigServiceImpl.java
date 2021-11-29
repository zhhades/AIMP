package cn.neu.aimp.iot.service.impl;

import cn.neu.aimp.iot.dao.AimpRealplayConfigDao;
import cn.neu.aimp.iot.entity.AimpRealplayConfig;
import cn.neu.aimp.iot.service.AimpRealplayConfigService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AimpRealplayConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-11-28 12:53:22
 */
@Service("aimpRealplayConfigService")
@Setter
@Transactional
public class AimpRealplayConfigServiceImpl implements AimpRealplayConfigService {
    @Resource
    private AimpRealplayConfigDao aimpRealplayConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    @Override
    public AimpRealplayConfig queryById(String ip) {
        return this.aimpRealplayConfigDao.queryById(ip);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AimpRealplayConfig> queryAllByLimit(int offset, int limit) {
        return this.aimpRealplayConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param aimpRealplayConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpRealplayConfig insert(AimpRealplayConfig aimpRealplayConfig) {
        this.aimpRealplayConfigDao.insert(aimpRealplayConfig);
        return aimpRealplayConfig;
    }

    /**
     * 修改数据
     *
     * @param aimpRealplayConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpRealplayConfig update(AimpRealplayConfig aimpRealplayConfig) {
        this.aimpRealplayConfigDao.update(aimpRealplayConfig);
        return this.queryById(aimpRealplayConfig.getIp());
    }

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ip) {
        return this.aimpRealplayConfigDao.deleteById(ip) > 0;
    }
}