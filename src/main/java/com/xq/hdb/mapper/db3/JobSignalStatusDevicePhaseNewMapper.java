package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusDevicePhaseNew;

import java.util.List;
import java.util.Map;

public interface JobSignalStatusDevicePhaseNewMapper extends BaseMapper<JobSignalStatusDevicePhaseNew> {

    List<Map> getJobPhaseByDeviceInfoId(String deviceId);

}
