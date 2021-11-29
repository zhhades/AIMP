package cn.neu.aimp.iot.dao;

import cn.neu.aimp.iot.entity.AimpForwardConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (ForwardConfig)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 12:34:12
 */
public interface AimpForwardConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpForwardConfig queryById(String ip);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpForwardConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param aimpForwardConfig 实例对象
     * @return 对象列表
     */
    List<AimpForwardConfig> queryAll(AimpForwardConfig aimpForwardConfig);

    /**
     * 新增数据
     *
     * @param aimpForwardConfig 实例对象
     * @return 影响行数
     */
    int insert(AimpForwardConfig aimpForwardConfig);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpForwardConfig> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AimpForwardConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpForwardConfig> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<AimpForwardConfig> entities);

    /**
     * 修改数据
     *
     * @param aimpForwardConfig 实例对象
     * @return 影响行数
     */
    int update(AimpForwardConfig aimpForwardConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 影响行数
     */
    int deleteById(String ip);

}