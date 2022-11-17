package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_resinfo_resourceset
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobSignalResinfoResourceset {
    private static final long serialVersionUID = 1L;


    private String id;


    @TableField("signalRessourceinfoId")
    private String signalRessourceinfoId;


    private String moduleId;


    private String resourceName;


    private String level;


    private String unit;


    private String name;


    private Integer insertDateMonth;


}
