package cn.neu.aimp.iot.service.impl;

import cn.neu.aimp.iot.dao.AimpPtzcontrolConfigDao;
import cn.neu.aimp.iot.entity.AimpPtzcontrolConfig;
import cn.neu.aimp.iot.service.AimpPtzcontrolConfigService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AimpPtzcontrolConfig)表服务实现类
 *
 * @author makejava
 * @since 2021-11-28 12:34:27
 */
@Service("aimpPtzcontrolConfigService")
@Setter
@Transactional
public class AimpPtzcontrolConfigServiceImpl implements AimpPtzcontrolConfigService {
    @Resource
    private AimpPtzcontrolConfigDao aimpPtzcontrolConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    @Override
    public AimpPtzcontrolConfig queryById(String ip) {
        return this.aimpPtzcontrolConfigDao.queryById(ip);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AimpPtzcontrolConfig> queryAllByLimit(int offset, int limit) {
        return this.aimpPtzcontrolConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpPtzcontrolConfig insert(AimpPtzcontrolConfig aimpPtzcontrolConfig) {
        this.aimpPtzcontrolConfigDao.insert(aimpPtzcontrolConfig);
        return aimpPtzcontrolConfig;
    }

    /**
     * 修改数据
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AimpPtzcontrolConfig update(AimpPtzcontrolConfig aimpPtzcontrolConfig) {
        this.aimpPtzcontrolConfigDao.update(aimpPtzcontrolConfig);
        return this.queryById(aimpPtzcontrolConfig.getIp());
    }

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String ip) {
        return this.aimpPtzcontrolConfigDao.deleteById(ip) > 0;
    }
}