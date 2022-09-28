package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_status
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobStatus
{
    private static final long serialVersionUID = 1L;


    private String id;

    @TableField("jobId")
    private String jobId;


    private String globalStatus;


    private Integer insertDateMonth;


}
