package cn.neu.aimp.iot.service;

import cn.neu.aimp.iot.entity.AimpDeviceConfig;

import java.util.List;

/**
 * (AimpDeviceConfig)表服务接口
 *
 * @author makejava
 * @since 2021-11-28 12:33:56
 */
public interface AimpDeviceConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpDeviceConfig queryById(String ip);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpDeviceConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param aimpDeviceConfig 实例对象
     * @return 实例对象
     */
    AimpDeviceConfig insert(AimpDeviceConfig aimpDeviceConfig);

    /**
     * 修改数据
     *
     * @param aimpDeviceConfig 实例对象
     * @return 实例对象
     */
    AimpDeviceConfig update(AimpDeviceConfig aimpDeviceConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    boolean deleteById(String ip);

}