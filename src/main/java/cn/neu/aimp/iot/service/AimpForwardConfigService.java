package cn.neu.aimp.iot.service;

import cn.neu.aimp.iot.entity.AimpForwardConfig;

import java.util.List;

/**
 * (AimpForwardConfig)表服务接口
 *
 * @author makejava
 * @since 2021-11-28 12:34:12
 */
public interface AimpForwardConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpForwardConfig queryById(String ip);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpForwardConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param aimpForwardConfig 实例对象
     * @return 实例对象
     */
    AimpForwardConfig insert(AimpForwardConfig aimpForwardConfig);

    /**
     * 修改数据
     *
     * @param aimpForwardConfig 实例对象
     * @return 实例对象
     */
    AimpForwardConfig update(AimpForwardConfig aimpForwardConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    boolean deleteById(String ip);

}