package com.xq.hdb.vo;


import lombok.Data;

import java.util.List;

/**
 * 【请填写功能名称】对象 job_signal_resinfo
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class SignalRessourceInfoVO
{
    private static final long serialVersionUID = 1L;



    private Enum Status;


    private String ModuleID;


    private Long ActualAmount;


    private String Unit;


    private String JobID;


    private String ExternalID;


    private String ProductID;


    private List<SignalRessourceinfoCommentVO> Comment;


    private List<SignalRessourceinfoResourcesetVO> ResourceSet;


}
