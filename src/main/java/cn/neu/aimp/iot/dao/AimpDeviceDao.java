package cn.neu.aimp.iot.dao;

import cn.neu.aimp.iot.entity.AimpDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Device)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 12:32:57
 */
public interface AimpDeviceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param deviceId 主键
     * @return 实例对象
     */
    AimpDevice queryById(String deviceId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpDevice> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param aimpDevice 实例对象
     * @return 对象列表
     */
    List<AimpDevice> queryAll(AimpDevice aimpDevice);

    /**
     * 新增数据
     *
     * @param aimpDevice 实例对象
     * @return 影响行数
     */
    int insert(AimpDevice aimpDevice);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpDevice> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AimpDevice> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpDevice> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<AimpDevice> entities);

    /**
     * 修改数据
     *
     * @param aimpDevice 实例对象
     * @return 影响行数
     */
    int update(AimpDevice aimpDevice);

    /**
     * 通过主键删除数据
     *
     * @param deviceId 主键
     * @return 影响行数
     */
    int deleteById(String deviceId);

}