package com.xq.hdb.vo;


import lombok.Data;

import java.util.List;

/**
 * 【请填写功能名称】对象 job_signal_res
 *
 * @author qjk
 * @date 2022-06-07
 */

@Data
public class SignalRessourceVO {
    private static final long serialVersionUID = 1L;


    private String PersonalID;


    private String deviceID;


    private String Time;


    private List<SignalRessourceInfoVO> RessourceInfo;


}
