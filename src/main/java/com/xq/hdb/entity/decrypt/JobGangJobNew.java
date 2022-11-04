package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_gang_job")
public class JobGangJobNew {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("gang_job_id")
    private String gangJobId;

    @TableField("jobId")
    private String jobId;

    @TableField("sheet_name_list_str")
    private String sheetNameListStr;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
