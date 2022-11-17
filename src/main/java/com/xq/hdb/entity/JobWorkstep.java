package com.xq.hdb.entity;


import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 job_workstep
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobWorkstep {
    private static final long serialVersionUID = 1L;


    private String id;


    private String name;


    private String jobId;


    private String jobName;


    private String status;


    private String amountPlanned;


    private String wastePlanned;


    private String amountProduced;


    private String wasteProduced;


    private String deviceId;


    private String sequenceType;


    private String startPlanned;


    private String endPlanned;


    private String start;


    private String end;


    private String setuptimePlanned;


    private String prodtimePlanned;


    private Long insertTime;


    private Long insertDate;


    private Date startTime;


    private Date endTime;


    private Date createTime;


    private Integer insertDateMonth;


}
