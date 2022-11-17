package com.xq.hdb.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeviceDataResultVO {
    private List<Map> ProductGoodList;//成品数量
    private List<Map> ProductWasteList;//生产废张
    private List<Map> PassPaperList;//过版纸
    private List<Map> PrintTimeList;//印刷时间
}
