package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusNew;
import com.xq.hdb.vo.DeviceDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JobSignalStatusNewMapper extends BaseMapper<JobSignalStatusNew> {

    Integer getSignalStatusCountByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, String deviceId);

    List<Map> pagingGetSignalStatusByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    List<Map> pagingGetSignalStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    //分组统计查询job_id
    List<Map> getJobIdOnly(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<DeviceDataVO> getJobSignalStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId);

    //生产废张
    List<DeviceDataVO> getProductWaste(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId, @Param("activityName") String activityName);


    //分组查询设备信息的jobId
    List<Map> getGroupDeviceJobId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId);

    //查询分组后的jobId设备数据
    List<DeviceDataVO> getDeviceByJobId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId, @Param("jobId") String jobId, @Param("activityName") String activityName);


}
