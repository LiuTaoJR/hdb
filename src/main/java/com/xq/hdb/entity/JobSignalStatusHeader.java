package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("job_signal_status_header")
public class JobSignalStatusHeader {

    private static final long serialVersionUID = 1L;


    @TableId("id")
    private String id;

    @TableField("signalStatusId")
    private String signalStatusId;

    @TableField("ref_id")
    private String refId;

    @TableField("device_id")
    private String deviceId;

    @TableField("time")
    private String time;

    @TableField("insert_time")
    private Long insertTime;

    @TableField("data_time")
    private Date dataTime;

    @TableField("create_time")
    private Date createTime;

    @TableField("insert_date_month")
    private Integer insertDateMonth;


}
