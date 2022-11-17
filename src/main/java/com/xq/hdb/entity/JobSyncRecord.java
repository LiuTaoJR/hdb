package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xq.hdb.utils.DateUtils;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@TableName("job_sync_record")
public class JobSyncRecord {


    @TableId("id")
    private String id;


    @TableField("job_id")
    private String jobId;


    @TableField("sync_status_job")
    private String syncStatusJob;


    @TableField("sync_status_workstep")
    private String syncStatusWorkstep;


    @TableField("create_time")
    private Date createTime;


    @TableField("update_time")
    private Date updateTime;


    @TableField("insert_date_month")
    private Integer insertDateMonth;


    public static void main(String[] args) {
        Date date = DateUtils.msToDate(1654828508439L);
        java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dates = formatter.format(date);//格式化数据
        System.out.println(dates);
    }


}
