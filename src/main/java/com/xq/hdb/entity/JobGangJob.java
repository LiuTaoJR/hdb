package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_gang_job
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobGangJob
{
    private static final long serialVersionUID = 1L;


    private String id;


    private String gangJobId;

    @TableField("jobId")
    private String jobId;


    private String sheetNameListStr;


    private Integer insertDateMonth;


}
