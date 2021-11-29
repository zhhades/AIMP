package cn.neu.aimp.iot.service.impl;

import cn.neu.aimp.iot.dao.AimpDeviceDao;
import cn.neu.aimp.iot.entity.AimpDevice;
import cn.neu.aimp.iot.service.AimpDeviceService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AimpDevice)表服务实现类
 *
 * @author makejava
 * @since 2021-11-28 12:33:02
 */
@Service("aimpDeviceService")
@Setter
@Transactional
public class AimpDeviceServiceImpl implements AimpDeviceService {
    @Resource
    private AimpDeviceDao aimpDeviceDao;

    /**
     * 通过ID查询单条数据
     *
     * @param deviceId 主键
     * @return 实例对象
     */
    @Override
    public AimpDevice queryById(String deviceId) {
        return this.aimpDeviceDao.queryById(deviceId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AimpDevice> queryAllByLimit(int offset, int limit) {
        return this.aimpDeviceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param aimpDevice 实例对象
     * @return 实例对象
     */
    @Override
    public AimpDevice insert(AimpDevice aimpDevice) {
        this.aimpDeviceDao.insert(aimpDevice);
        return aimpDevice;
    }

    /**
     * 修改数据
     *
     * @param aimpDevice 实例对象
     * @return 实例对象
     */
    @Override
    public AimpDevice update(AimpDevice aimpDevice) {
        this.aimpDeviceDao.update(aimpDevice);
        return this.queryById(aimpDevice.getDeviceId());
    }

    /**
     * 通过主键删除数据
     *
     * @param deviceId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String deviceId) {
        return this.aimpDeviceDao.deleteById(deviceId) > 0;
    }
}