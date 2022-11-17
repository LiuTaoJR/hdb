package com.xq.hdb.vo.screen;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ScreenDataVO {
    private Map<String,Integer> deviceSpeed; //设备速度
    private List<Map> proGoodAmountMonth; //每月印张数
    private Map<String,Object> deviceDataToDay; //每日设备印张数
}
