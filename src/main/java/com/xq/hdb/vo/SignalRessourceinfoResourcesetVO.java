package com.xq.hdb.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * 【请填写功能名称】对象 job_signal_resinfo_resourceset_resource
 *
 * @author qjk
 * @date 2022-06-07
 */
@Data
public class SignalRessourceinfoResourcesetVO {

    private static final long serialVersionUID = 1L;


    private String ModuleID;


    private String ResourceName;


    private Enum Level;


    private String Unit;


    private String Name;


    private List<SignalResourcesetResourceVO> Resource;


}
