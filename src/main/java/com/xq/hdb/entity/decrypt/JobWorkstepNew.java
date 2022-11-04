package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("job_workstep")
public class JobWorkstepNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("job_id")
    private String jobId;

    @TableField("job_name")
    private String jobName;

    @TableField("status")
    private String status;

    @TableField("amount_planned")
    private String amountPlanned;

    @TableField("waste_planned")
    private String wastePlanned;

    @TableField("amount_produced")
    private String amountProduced;

    @TableField("waste_produced")
    private String wasteProduced;

    @TableField("device_id")
    private String deviceId;

    @TableField("sequence_type")
    private String sequenceType;

    @TableField("start_planned")
    private String startPlanned;

    @TableField("end_planned")
    private String endPlanned;

    @TableField("start")
    private String start;

    @TableField("end")
    private String end;

    @TableField("setuptime_planned")
    private String setuptimePlanned;

    @TableField("prodtime_planned")
    private String prodtimePlanned;

    @TableField("insert_time")
    private Long insertTime;

    @TableField("insert_date")
    private Long insertDate;

    @TableField("start_time")
    private Date startTime;

    @TableField("end_time")
    private Date endTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
