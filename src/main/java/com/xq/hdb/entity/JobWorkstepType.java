package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_workstep_type
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobWorkstepType {
    private static final long serialVersionUID = 1L;


    private String id;


    @TableField("workstepId")
    private String workstepId;


    private String type;


    private Integer insertDateMonth;


}
