package com.xq.hdb.vo;


import lombok.Data;

import java.util.List;


@Data
public class SignalStatusDeviceInfoVO {



    private String DeviceID;


    //private Enum Status;
    private String Status;


    //private Enum StatusDetails;
    private String StatusDetails;


    private Long Speed;


    private String EventID;


    private String TotalProductionCounter;


    private String ProductionCounter;


    private String ModuleIDs;


    private List<SignalStatusDevicePhaseVO> JobPhase;


    private List<SignalStatusDeviceEventVO> Event;



}
