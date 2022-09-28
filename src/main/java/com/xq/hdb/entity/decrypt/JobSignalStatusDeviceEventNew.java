package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("job_signal_status_device_event")
public class JobSignalStatusDeviceEventNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("event_id")
    private String eventId;

    @TableField("signaStatusDeviceId")
    private String signaStatusDeviceId;

    @TableField("event_value")
    private String eventValue;

    @TableField("insert_date_month")
    private Integer insertDateMonth;

    @TableField("insert_time")
    private String insertTime;
}
