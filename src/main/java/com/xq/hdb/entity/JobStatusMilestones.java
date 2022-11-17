package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_status_milestones
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobStatusMilestones {
    private static final long serialVersionUID = 1L;


    private String id;

    @TableField("statusId")
    private String statusId;


    private String milestoneDefid;


    private String status;


    private String calculatedProgress;


    private String comment;


    private Integer insertDateMonth;


}
