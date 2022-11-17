package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusDeviceEventNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JobSignalStatusDeviceEventNewMapper extends BaseMapper<JobSignalStatusDeviceEventNew> {

    List<Map> getDeviceEventBySignalStatusId(String statusDeviceId);

    List<JobSignalStatusDeviceEventNew> pageList(@Param("a") int a, @Param("b") int b);


}
