package com.xq.hdb.vo;


import lombok.Data;

import java.util.List;

@Data
public class SignalStatusVO {


    //private String Time;


    //private String DeviceID;


    private List<SignalStatusDeviceInfoVO> DeviceInfo;


}
