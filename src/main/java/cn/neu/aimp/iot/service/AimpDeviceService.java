package cn.neu.aimp.iot.service;

import cn.neu.aimp.iot.entity.AimpDevice;

import java.util.List;

/**
 * (AimpDevice)表服务接口
 *
 * @author makejava
 * @since 2021-11-28 12:33:01
 */
public interface AimpDeviceService {

    /**
     * 通过ID查询单条数据
     *
     * @param deviceId 主键
     * @return 实例对象
     */
    AimpDevice queryById(String deviceId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpDevice> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param aimpDevice 实例对象
     * @return 实例对象
     */
    AimpDevice insert(AimpDevice aimpDevice);

    /**
     * 修改数据
     *
     * @param aimpDevice 实例对象
     * @return 实例对象
     */
    AimpDevice update(AimpDevice aimpDevice);

    /**
     * 通过主键删除数据
     *
     * @param deviceId 主键
     * @return 是否成功
     */
    boolean deleteById(String deviceId);

}