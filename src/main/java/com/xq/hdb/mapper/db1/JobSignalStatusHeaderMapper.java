package com.xq.hdb.mapper.db1;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobSignalStatusHeader;
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
public interface JobSignalStatusHeaderMapper extends BaseMapper<JobSignalStatusHeader> {


    /**
     * @param signalStatusId
     * @return
     */
    List<Map> getHeaderBySignalStatusId(String signalStatusId);


    Date getTimeByStatusIdAndDeviceId(@Param("signalStatusId") String signalStatusId, @Param("deviceId") String deviceId);


    Date getCreateTimeByStatusIdAndDeviceId(@Param("signalStatusId") String signalStatusId, @Param("deviceId") String deviceId);


    List<JobSignalStatusHeader> pageList(@Param("a") int a, @Param("b") int b);
}
