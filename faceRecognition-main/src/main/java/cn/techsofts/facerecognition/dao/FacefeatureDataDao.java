package cn.techsofts.facerecognition.dao;

import cn.techsofts.facerecognition.entity.FacefeatureData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (FacefeatureData)表数据库访问层
 *
 * @author Aiots-cao
 * @since 2020-02-14 19:36:24
 */
@Repository
public interface FacefeatureDataDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FacefeatureData queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<FacefeatureData> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param facefeatureData 实例对象
     * @return 对象列表
     */
    List<FacefeatureData> queryAll(FacefeatureData facefeatureData);

    /**
     * 新增数据
     *
     * @param facefeatureData 实例对象
     * @return 影响行数
     */
    int insert(FacefeatureData facefeatureData);

    /**
     * 修改数据
     *
     * @param facefeatureData 实例对象
     * @return 影响行数
     */
    int update(FacefeatureData facefeatureData);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}