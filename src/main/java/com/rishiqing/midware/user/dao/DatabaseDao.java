package com.rishiqing.midware.user.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by  on 2017/6/22.Wallace
 */
public interface DatabaseDao {
    /**
     * 删除${tableName}表中的所有数据
     * @param tableName
     * @return
     */
    long deleteAllTableData(@Param("tableName") String tableName);

    /**
     * 将${tableName}表的${fieldName}字段设置为null
     * 一般用于清除外键关联
     * @param tableName
     * @param fieldName
     * @return
     */
    long setTableFieldNull(@Param("tableName") String tableName,
                           @Param("fieldName") String fieldName);
}
