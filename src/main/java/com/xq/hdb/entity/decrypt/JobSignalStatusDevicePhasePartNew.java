package com.xq.hdb.entity.decrypt;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("job_signal_status_device_phase_part")
public class JobSignalStatusDevicePhasePartNew {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("signalStatusDevicePhaseId")
    private String signalStatusDevicePhaseId;

    @TableField("side")
    private String side;

    @TableField("sheet_name")
    private String sheetName;

    @TableField("insert_date_month")
    private Integer insertDateMonth;


    @TableField("insert_time")
    private String insertTime;
}
