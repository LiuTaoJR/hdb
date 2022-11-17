package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.screen.ScreenData;

import java.util.List;
import java.util.Map;

public interface ScreenDataMapper extends BaseMapper<ScreenData> {

    int removeScreenByTime(String time);

    //查询每月印刷张数
    List<Map> getScreenData(String time);

}
