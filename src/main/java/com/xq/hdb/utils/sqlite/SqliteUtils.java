package com.xq.hdb.utils.sqlite;


import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.file.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SqliteUtils {


    public static String backupByMonth(String sqliteDBUrl, String backupDBUrl) throws Exception {
        //复制数据库至新位置
        String newPath = backupDBUrl + "/hdb_other" + DateUtils.getLastMonth() + ".db";
        String code = FileUtils.copySingleFile(sqliteDBUrl, newPath);
        if (!code.equals("200")) {
            return "复制文件出错";
        }

        try {
            //加载数据库驱动
            Class.forName("org.sqlite.JDBC");
            //连接目标数据库
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + newPath);
            Statement stmt = conn.createStatement();

            //获取数据库里的所有表并删除时间段以外的数据
            String allTableSql = " SELECT name from sqlite_master where name like 'job%' ";

            ResultSet allTableName = stmt.executeQuery(allTableSql);

            List<String> tableNamelist = new ArrayList();
            while (allTableName.next()) {
                tableNamelist.add(allTableName.getString(1));
            }

            for (String tableName : tableNamelist) {
                String dataRefreshd = "delete from " + tableName + " where insert_date_month != " + DateUtils.getLastMonth();
                stmt.executeUpdate(dataRefreshd);
            }

            //缩减数据文件大小
            stmt.executeUpdate("vacuum");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "操作备份数据库出错";
        }

        return "200";

    }


}
