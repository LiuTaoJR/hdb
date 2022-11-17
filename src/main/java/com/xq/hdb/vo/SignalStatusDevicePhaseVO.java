package com.xq.hdb.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】对象 job_signal_status_device_phase
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class SignalStatusDevicePhaseVO {
    private static final long serialVersionUID = 1L;


    //private Enum Status;
    private String Status;

    private Long Amount;


    private Date StartTime;


    private Long TotalAmount;


    private Long Waste;


    private Long PercentCompleted;


    private String CostCenterID;


    private String JobID;


    private String WorkstepID;


    private List<SignalStatusDevicePhaseMisDetailsVO> MISDetails;


    private List<SignalStatusDevicePhasePartVO> Part;


    private List<SignalStatusDevicePhaseActivityVO> Activity;

}
