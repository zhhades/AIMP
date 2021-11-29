package cn.neu.aimp.iot.dao;

import cn.neu.aimp.iot.entity.AimpRealplayConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (RealPlayConfig)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-28 12:53:16
 */
public interface AimpRealplayConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param ip 主键
     * @return 实例对象
     */
    AimpRealplayConfig queryById(String ip);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<AimpRealplayConfig> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param aimpRealplayConfig 实例对象
     * @return 对象列表
     */
    List<AimpRealplayConfig> queryAll(AimpRealplayConfig aimpRealplayConfig);

    /**
     * 新增数据
     *
     * @param aimpRealplayConfig 实例对象
     * @return 影响行数
     */
    int insert(AimpRealplayConfig aimpRealplayConfig);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpRealplayConfig> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AimpRealplayConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AimpRealplayConfig> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<AimpRealplayConfig> entities);

    /**
     * 修改数据
     *
     * @param aimpRealplayConfig 实例对象
     * @return 影响行数
     */
    int update(AimpRealplayConfig aimpRealplayConfig);

    /**
     * 通过主键删除数据
     *
     * @param ip 主键
     * @return 影响行数
     */
    int deleteById(String ip);

}