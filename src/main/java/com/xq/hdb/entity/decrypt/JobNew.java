package com.xq.hdb.entity.decrypt;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("job")
public class JobNew {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("customer_id")
    private String customerId;

    @TableField("customer_name")
    private String customerName;

    @TableField("customer_order_id")
    private String customerOrderId;

    @TableField("due_date")
    private String dueDate;

    @TableField("delivery_amount")
    private String deliveryAmount;

    @TableField("creation_date")
    private String creationDate;

    @TableField("last_modified")
    private String lastModified;

    @TableField("author")
    private String author;

    @TableField("priority")
    private String priority;

    @TableField("number_planned_pages")
    private String numberPlannedPages;

    @TableField("description")
    private String description;

    @TableField("shop_id")
    private String shopId;

    @TableField("project_id")
    private String projectId;

    @TableField("production_monitor_url")
    private String productionMonitorUrl;

    @TableField("source_job_ids")
    private String sourceJobIds;

    @TableField("insert_date_month")
    private Integer insertDateMonth;
}
