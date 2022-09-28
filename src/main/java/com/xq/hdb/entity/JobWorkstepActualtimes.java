package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_workstep_actualtimes
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobWorkstepActualtimes
{
    private static final long serialVersionUID = 1L;


    private String id;


    private String jobId;


    private String jobName;

    @TableField("workstepId")
    private String workstepId;


    private String workstepName;


    private String timeTypeGroupName;


    private String timeTypeName;


    private String duration;


    private Integer insertDateMonth;


}
