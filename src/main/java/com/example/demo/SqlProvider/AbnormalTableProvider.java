package com.example.demo.SqlProvider;

import com.example.demo.entity.AbnormalTableEntity;
import org.apache.ibatis.jdbc.SQL;

import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;

/**
 * AbnormalTable构建动态sql语句
 */
public class AbnormalTableProvider {
    /**
     * 更新video动态语句
     * @return
     */
    public String updateAbnormalTableEntity(AbnormalTableEntity abnormalTableEntity){
        return new SQL(){{
            UPDATE("T_ABNORMAL_TABLE");
            //条件写法.
            if(abnormalTableEntity.getAbnormalVolume()!=null){
                SET("ABNORMAL_VOLUME=#{abnormalVolume}");
            }
            if(abnormalTableEntity.getAbnormalPileNumber()!=null){
                SET("ABNORMAL_PILE_NUMBER=#{abnormalPileNumber}");
            }
            WHERE("ID=#{id}");
        }}.toString();
    }
}
