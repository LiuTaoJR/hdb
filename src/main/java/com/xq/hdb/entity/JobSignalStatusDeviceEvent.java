package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_status_device_event
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
@TableName("job_signal_status_device_event")
public class JobSignalStatusDeviceEvent {
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
