package com.xq.hdb.entity;


import com.xq.hdb.utils.DateUtils;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class JobSyncRecord {


    private String id;


    private String jobId;


    private String syncStatusJob;


    private String syncStatusWorkstep;


    private Date createTime;


    private Date updateTime;


    private Integer insertDateMonth;


    public static void main(String[] args) {
        Date date = DateUtils.msToDate(1654828508439L);
        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String dates = formatter.format(date);//格式化数据
        System.out.println(dates);
    }


}
