package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_workstep_type")
public class JobWorkstepTypeNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("workstepId")
    private String workstepId;

    @TableField("type")
    private String type;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
