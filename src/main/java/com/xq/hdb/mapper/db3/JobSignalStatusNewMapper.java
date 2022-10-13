package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusNew;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JobSignalStatusNewMapper extends BaseMapper<JobSignalStatusNew> {

    Integer getSignalStatusCountByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, String deviceId);

    List<Map> pagingGetSignalStatusByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    //分组统计查询job_id
    List<Map> getJobIdOnly(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId);
}
