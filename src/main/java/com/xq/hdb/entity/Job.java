package com.xq.hdb.entity;


import lombok.Data;

/**
 * 【请填写功能名称】对象 job
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class Job {
    private static final long serialVersionUID = 1L;


    private String id;


    private String name;


    private String customerId;


    private String customerName;


    private String customerOrderId;


    private String dueDate;


    private String deliveryAmount;


    private String creationDate;


    private String lastModified;


    private String author;


    private String priority;


    private String numberPlannedPages;


    private String description;


    private String shopId;


    private String projectId;


    private String productionMonitorUrl;


    private String sourceJobIds;


    private Integer insertDateMonth;


}
