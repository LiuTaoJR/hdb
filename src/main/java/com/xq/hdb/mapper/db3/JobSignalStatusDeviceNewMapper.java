package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusDeviceNew;

import java.util.List;
import java.util.Map;

public interface JobSignalStatusDeviceNewMapper extends BaseMapper<JobSignalStatusDeviceNew> {

    List<Map> getDeviceSpeedBySignalStatusId(String signalStatusId);

}
