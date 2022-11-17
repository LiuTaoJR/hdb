package com.xq.hdb.service.impl;

import com.xq.hdb.mapper.db2.SqliteOtherMapper;
import com.xq.hdb.service.SqliteService;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SqliteServiceImpl implements SqliteService {

    //@Autowired
    //private SqliteJobMapper sqliteJobMapper;

    @Autowired
    private SqliteOtherMapper sqliteOtherMapper;


    @Override
    public List<String> getAllTable() {
        return sqliteOtherMapper.getAllTable();
    }


    /**
     * 刷新数据库
     *
     * @param tables
     */
    @Override
    public void refreshData(List<String> tables) {
        try {
            if (tables != null && tables.size() > 0) {
                Integer insertDateMonth = Integer.valueOf(DateUtils.getLastMonth());
                for (String table : tables) {
                    sqliteOtherMapper.refreshData(table, insertDateMonth);
                }
            }
            sqliteOtherMapper.cutDBFile();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("refreshData方法异常:{}", e.getMessage());
        }
    }


}
