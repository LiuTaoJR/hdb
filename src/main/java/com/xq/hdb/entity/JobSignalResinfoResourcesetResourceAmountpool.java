package com.xq.hdb.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_resinfo_resourceset_resource_amountpool
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class JobSignalResinfoResourcesetResourceAmountpool
{
    private static final long serialVersionUID = 1L;


    private String id;

    @TableField("signalRessourceinfoResourcesetResourceId")
    private String signalRessourceinfoResourcesetResourceId;


    private String actualAmount;


    private String amount;


    private Integer insertDateMonth;


}
