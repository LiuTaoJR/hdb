package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_status_device
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobSignalStatusDevice
{
    private static final long serialVersionUID = 1L;


    private String id;

    @TableField("signalStatusId")
    private String signalStatusId;


    private String deviceId;


    private String status;


    private String statusDetails;


    private String speed;


    private String eventId;


    private String totalProductionCounter;


    private String productionCounter;


    private String moduleIds;


    private Integer insertDateMonth;


}
