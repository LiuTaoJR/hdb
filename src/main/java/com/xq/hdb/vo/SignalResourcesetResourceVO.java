package com.xq.hdb.vo;



import lombok.Data;

import java.util.List;

/**
 * 【请填写功能名称】对象 job_signal_resinfo_resourceset_resource
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class SignalResourcesetResourceVO
{

    private static final long serialVersionUID = 1L;


    private String DescriptiveName;


    private List<SignalResourceAmountpoolVO> AmountPool;


    private List<SignalResourceMiscconsumableVO> MiscConsumable;


}
