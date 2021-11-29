package cn.neu.aimp.iot.service;

import cn.neu.aimp.iot.entity.AimpRealplayConfig;

import java.util.List;

/**
 * (AimpRealplayConfig)表服务接口
 *
 * @author makejava
 * @since 2021-11-28 12:53:22
 */
public interface AimpRealplayConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpRealplayConfig queryById(String ip);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpRealplayConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param aimpRealplayConfig 实例对象
     * @return 实例对象
     */
    AimpRealplayConfig insert(AimpRealplayConfig aimpRealplayConfig);

    /**
     * 修改数据
     *
     * @param aimpRealplayConfig 实例对象
     * @return 实例对象
     */
    AimpRealplayConfig update(AimpRealplayConfig aimpRealplayConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 是否成功
     */
    boolean deleteById(String ip);

}