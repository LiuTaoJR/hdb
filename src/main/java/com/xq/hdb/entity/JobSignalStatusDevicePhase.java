package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_status_device_phase
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
@TableName("job_signal_status_device_phase")
public class JobSignalStatusDevicePhase {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("signalStatusDeviceId")
    private String signalStatusDeviceId;

    @TableField("status")
    private String status;

    @TableField("amount")
    private String amount;

    @TableField("start_time")
    private String startTime;

    @TableField("total_amount")
    private String totalAmount;

    @TableField("waste")
    private String waste;

    @TableField("percent_completed")
    private String percentCompleted;

    @TableField("cost_center_id")
    private String costCenterId;

    @TableField("job_id")
    private String jobId;

    @TableField("workstep_id")
    private String workstepId;

    @TableField("insert_date_month")
    private Integer insertDateMonth;

    @TableField("insert_time")
    private String insertTime;


}
