package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job_signal_status_device")
public class JobSignalStatusDeviceNew {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("signalStatusId")
    private String signalStatusId;

    @TableField("device_id")
    private String deviceId;

    @TableField("status")
    private String status;

    @TableField("status_details")
    private String statusDetails;

    @TableField("speed")
    private String speed;

    @TableField("event_id")
    private String eventId;

    @TableField("total_production_counter")
    private String totalProductionCounter;

    @TableField("production_counter")
    private String productionCounter;

    @TableField("module_ids")
    private String moduleIds;

    @TableField("insert_time")
    private String insertTime;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
