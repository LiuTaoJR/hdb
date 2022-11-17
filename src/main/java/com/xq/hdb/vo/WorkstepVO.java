package com.xq.hdb.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class WorkstepVO {


    private String id;


    private String name;


    //private JobVO job;
    private Map job;


    private String status;


    private Long amountPlanned;


    private Long wastePlanned;


    private Long amountProduced;


    private Long wasteProduced;


    private String deviceId;


    private String sequenceType;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startPlanned;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endPlanned;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date start;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date end;


    private Long setuptimePlanned;


    private Long prodtimePlanned;


    private List<String> types;


    private List<WorkstepActualTimesVO> actualTimes;


}
