package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;


@Data
@TableName("job_sync_record")
public class JobSyncRecordNew {

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
}
