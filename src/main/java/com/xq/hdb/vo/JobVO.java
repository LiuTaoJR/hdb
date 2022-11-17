package com.xq.hdb.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class JobVO {


    private String id;


    private String name;


    private Date dueDate;


    private Date creationDate;


    private Date lastModified;


    private Double deliveryAmount;


    private Double numberPlannedPages;


    private Double priority;


    private String customerOrderid;


    private String description;


    private String projectId;


    private String shopId;


    private String author;


    private String customerId;


    private String customerName;


    private String productionMonitorUrl;


    private List<String> sourceJobIds;


    private List<JobGangJobsVO> gangJobs;


    private List<JobSequencesVO> sequences;


}
