package com.xq.hdb.mapper.db1;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobSignalStatus;
import com.xq.hdb.utils.DateUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author qjk
 * @date 2022-06-07
 */
@Mapper
public interface JobSignalStatusMapper extends BaseMapper<JobSignalStatus>{



    List<JobSignalStatus> getStatusList(@Param("jobId") String jobId, @Param("speed")String speed, @Param("deviceId")String deviceId, @Param("eventId")String eventId, @Param("status")String status);



    Integer getSignalStatusCountByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, String deviceId);



    List<Map> getPullSignalStatusByDate(@Param("startDate") Long startDate, @Param("endDate") Long endDate);




    List<Map> pagingGetSignalStatusByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);


    List<JobSignalStatus> pageList(@Param("a") int a,@Param("b") int b);
}
