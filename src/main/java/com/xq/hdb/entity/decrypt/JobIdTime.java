package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("job_id_time")
public class JobIdTime {

    @TableField("job_time")
    private String jobTime;

    @TableField("job_id")
    private String jobId;

    @TableField("time")
    private String time;

    @TableField("create_time")
    private Date createTime;
}
