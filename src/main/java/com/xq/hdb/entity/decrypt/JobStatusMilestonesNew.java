package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_status_milestones")
public class JobStatusMilestonesNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("statusId")
    private String statusId;

    @TableField("milestone_defId")
    private String milestoneDefid;

    @TableField("status")
    private String status;

    @TableField("calculated_progress")
    private String calculatedProgress;

    @TableField("comment")
    private String comment;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
