package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 job_signal_status
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
@TableName("job_signal_status")
public class JobSignalStatus {
    private static final long serialVersionUID = 1L;


    @TableId("id")
    private String id;

    @TableField("time")
    private String time;

    @TableField("device_id")
    private String deviceId;

    @TableField("insert_time")
    private Long insertTime;

    @TableField("insert_date")
    private Long insertDate;

    @TableField("data_time")
    private Date dataTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("insert_date_month")
    private Integer insertDateMonth;


}
