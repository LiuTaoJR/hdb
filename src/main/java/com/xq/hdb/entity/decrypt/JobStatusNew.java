package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_status")
public class JobStatusNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("jobId")
    private String jobId;

    @TableField("global_status")
    private String globalStatus;

    @TableField("insert_date_month")
    private Integer insertDateMonth;

}
