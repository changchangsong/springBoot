package com.example.demo.dao;

import com.example.demo.SqlProvider.AbnormalTableProvider;
import com.example.demo.entity.AbnormalTableEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * ${comments}
 *
 * @author Song
 * @email 852146603@qq.com
 * @date 2019-03-20 10:37:40
 */
@Mapper
public interface AbnormalTableDao extends BaseDao<AbnormalTableEntity> {

    @Select("select * from T_ABNORMAL_TABLE")
    List<AbnormalTableEntity> findAll();

    @Select("select * from T_ABNORMAL_TABLE where ID = #{id} ")
    AbnormalTableEntity findById(String id);

    @UpdateProvider(type = AbnormalTableProvider.class, method = "updateAbnormalTableEntity")
    int update(AbnormalTableEntity abnormalTableEntity);

}
