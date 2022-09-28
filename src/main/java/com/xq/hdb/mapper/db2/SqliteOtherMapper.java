package com.xq.hdb.mapper.db2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SqliteOtherMapper {




    List<String> getAllTable();



    void refreshData(@Param("table") String table, @Param("insertDateMonth") Integer insertDateMonth);



    void cutDBFile();


}
