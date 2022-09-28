package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_status_device_phase_mis_details
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
@TableName("job_signal_status_device_phase_mis_details")
public class JobSignalStatusDevicePhaseMisDetails
{
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("signalStatusDevicePhaseId")
    private String signalStatusDevicePhaseId;

    @TableField("work_type")
    private String workType;

    @TableField("insert_date_month")
    private Integer insertDateMonth;

    @TableField("insert_time")
    private String insertTime;


}
