package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_workstep_actualtimes")
public class JobWorkstepActualtimesNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("job_id")
    private String jobId;

    @TableField("job_name")
    private String jobName;

    @TableField("workstepId")
    private String workstepId;

    @TableField("workstep_name")
    private String workstepName;

    @TableField("time_type_group_name")
    private String timeTypeGroupName;

    @TableField("time_type_name")
    private String timeTypeName;

    @TableField("duration")
    private String duration;

    @TableField("insert_date_month")
    private Integer insertDateMonth;

}
