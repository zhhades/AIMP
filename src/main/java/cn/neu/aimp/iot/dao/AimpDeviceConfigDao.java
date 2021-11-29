package cn.neu.aimp.iot.dao;

import cn.neu.aimp.iot.entity.AimpDeviceConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (DeviceConfig)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 12:33:56
 */
public interface AimpDeviceConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpDeviceConfig queryById(String ip);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpDeviceConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param aimpDeviceConfig 实例对象
     * @return 对象列表
     */
    List<AimpDeviceConfig> queryAll(AimpDeviceConfig aimpDeviceConfig);

    /**
     * 新增数据
     *
     * @param aimpDeviceConfig 实例对象
     * @return 影响行数
     */
    int insert(AimpDeviceConfig aimpDeviceConfig);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpDeviceConfig> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AimpDeviceConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpDeviceConfig> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<AimpDeviceConfig> entities);

    /**
     * 修改数据
     *
     * @param aimpDeviceConfig 实例对象
     * @return 影响行数
     */
    int update(AimpDeviceConfig aimpDeviceConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 影响行数
     */
    int deleteById(String ip);

}