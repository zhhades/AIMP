package cn.neu.aimp.iot.service;

import cn.neu.aimp.iot.entity.AimpPtzcontrolConfig;

import java.util.List;

/**
 * (AimpPtzcontrolConfig)表服务接口
 *
 * @author makejava
 * @since 2021-11-28 12:34:27
 */
public interface AimpPtzcontrolConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpPtzcontrolConfig queryById(String ip);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpPtzcontrolConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 实例对象
     */
    AimpPtzcontrolConfig insert(AimpPtzcontrolConfig aimpPtzcontrolConfig);

    /**
     * 修改数据
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 实例对象
     */
    AimpPtzcontrolConfig update(AimpPtzcontrolConfig aimpPtzcontrolConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    boolean deleteById(String ip);

}