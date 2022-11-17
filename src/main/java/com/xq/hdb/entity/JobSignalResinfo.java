package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_resinfo
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobSignalResinfo {
    private static final long serialVersionUID = 1L;


    private String id;

    @TableField("signalRessourceId")
    private String signalRessourceId;


    private String status;


    private String moduleId;


    private String actualAmount;


    private String unit;


    private String jobId;


    private String externalId;


    private String productId;


    private Integer insertDateMonth;


}
