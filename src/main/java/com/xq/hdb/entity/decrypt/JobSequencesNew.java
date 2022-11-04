package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_sequences")
public class JobSequencesNew {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("jobId")
    private String jobId;

    @TableField("name")
    private String name;

    @TableField("type")
    private String type;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
