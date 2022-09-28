package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;


@Data
@TableName("job_signal_status")
public class JobSignalStatusNew {

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
