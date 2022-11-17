package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusDeviceEventCopy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobSignalStatusDeviceEventCopyMapper extends BaseMapper<JobSignalStatusDeviceEventCopy> {

    List<JobSignalStatusDeviceEventCopy> pageList(@Param("a") int a, @Param("b") int b);
}
