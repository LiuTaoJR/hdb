package com.xq.hdb.vo;

import lombok.Data;

import java.util.Date;

//设备数据
@Data
public class DeviceDataVO {
    private String jobId;
    private Date startTime;
    private Date time;
    private String activityName;
    private String activityId;
    private String eventId;
    private String totalProductionCounter;
    private String productionCounter;
    private String status;
    private String statusDetails;
    private String amount;
    private String waste;
    private String speed;
}
