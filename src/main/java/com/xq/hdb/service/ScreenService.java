package com.xq.hdb.service;

import com.xq.hdb.vo.screen.ScreenDataVO;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScreenService {

    //统计每个设备最新运行速度
    Map<String,Integer> getDeviceSpeed(Date date);

    //查询每月成品数
    List<Map> getProGoodAmountMonth(Date date);

    //查询当日设备数据
    Map<String,Object> getDeviceDataToDay(Date date);

    ScreenDataVO getScreenData(Date date);

}
