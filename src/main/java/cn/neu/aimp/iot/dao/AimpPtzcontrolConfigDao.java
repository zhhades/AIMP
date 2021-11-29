package cn.neu.aimp.iot.dao;

import cn.neu.aimp.iot.entity.AimpPtzcontrolConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (PtzControlConfig)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 12:34:27
 */
public interface AimpPtzcontrolConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpPtzcontrolConfig queryById(String ip);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpPtzcontrolConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 对象列表
     */
    List<AimpPtzcontrolConfig> queryAll(AimpPtzcontrolConfig aimpPtzcontrolConfig);

    /**
     * 新增数据
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 影响行数
     */
    int insert(AimpPtzcontrolConfig aimpPtzcontrolConfig);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpPtzcontrolConfig> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AimpPtzcontrolConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpPtzcontrolConfig> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<AimpPtzcontrolConfig> entities);

    /**
     * 修改数据
     *
     * @param aimpPtzcontrolConfig 实例对象
     * @return 影响行数
     */
    int update(AimpPtzcontrolConfig aimpPtzcontrolConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 影响行数
     */
    int deleteById(String ip);

}