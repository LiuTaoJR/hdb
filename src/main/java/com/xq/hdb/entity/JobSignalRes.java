package com.xq.hdb.entity;


import lombok.Data;

/**
 * 【请填写功能名称】对象 job_signal_res
 *
 * @author qjk
 * @date 2022-06-07
 */

@Data
public class JobSignalRes
{
    private static final long serialVersionUID = 1L;


    private String id;


    private String personalId;


    private String deviceId;


    private String time;


    private Integer insertDateMonth;


}
