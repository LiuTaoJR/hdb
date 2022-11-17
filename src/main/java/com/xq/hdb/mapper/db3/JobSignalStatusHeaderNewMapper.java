package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusHeaderNew;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JobSignalStatusHeaderNewMapper extends BaseMapper<JobSignalStatusHeaderNew> {

    List<Map> getHeaderBySignalStatusId(String signalStatusId);

    Date getTimeByStatusIdAndDeviceId(@Param("signalStatusId") String signalStatusId, @Param("deviceId") String deviceId);

    Date getCreateTimeByStatusIdAndDeviceId(@Param("signalStatusId") String signalStatusId, @Param("deviceId") String deviceId);

    //当日统计发生时间
    List<Map> getStatisticsDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId, @Param("eventId") String eventId);


}
