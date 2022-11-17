package com.xq.hdb.service;

import java.util.List;

public interface SqliteService {


    List<String> getAllTable();


    /**
     * 刷新表数据
     *
     * @param tables
     */
    void refreshData(List<String> tables);


}
