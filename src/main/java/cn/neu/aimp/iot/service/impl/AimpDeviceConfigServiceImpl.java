package cn.neu.aimp.iot.service.impl;

import cn.neu.aimp.iot.dao.AimpDeviceConfigDao;
import cn.neu.aimp.iot.entity.AimpDeviceConfig;
import cn.neu.aimp.iot.service.AimpDeviceConfigService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AimpDeviceConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-11-28 12:33:56
 */
@Service("aimpDeviceConfigService")
@Setter
@Transactional
public class AimpDeviceConfigServiceImpl implements AimpDeviceConfigService {
    @Resource
    private AimpDeviceConfigDao aimpDeviceConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    @Override
    public AimpDeviceConfig queryById(String ip) {
        return this.aimpDeviceConfigDao.queryById(ip);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AimpDeviceConfig> queryAllByLimit(int offset, int limit) {
        return this.aimpDeviceConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param aimpDeviceConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpDeviceConfig insert(AimpDeviceConfig aimpDeviceConfig) {
        this.aimpDeviceConfigDao.insert(aimpDeviceConfig);
        return aimpDeviceConfig;
    }

    /**
     * 修改数据
     *
     * @param aimpDeviceConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpDeviceConfig update(AimpDeviceConfig aimpDeviceConfig) {
        this.aimpDeviceConfigDao.update(aimpDeviceConfig);
        return this.queryById(aimpDeviceConfig.getIp());
    }

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ip) {
        return this.aimpDeviceConfigDao.deleteById(ip) > 0;
    }
}