package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("job_signal_status_device_phase_activity")
public class JobSignalStatusDevicePhaseActivityNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("activity_id")
    private String activityId;

    @TableField("signalStatusDevicePhaseId")
    private String signalStatusDevicePhaseId;

    @TableField("activity_name")
    private String activityName;

    @TableField("activity_personal_id")
    private String activityPersonalId;

    @TableField("activity_start_time")
    private String activityStartTime;

    @TableField("insert_date_month")
    private Integer insertDateMonth;

    @TableField("insert_time")
    private String insertTime;
}
