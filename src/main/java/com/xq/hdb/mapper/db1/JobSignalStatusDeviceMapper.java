package com.xq.hdb.mapper.db1;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobSignalStatusDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author qjk
 * @date 2022-06-07
 */
@Mapper
public interface JobSignalStatusDeviceMapper extends BaseMapper<JobSignalStatusDevice>{




    List<JobSignalStatusDevice> getStatusDeviceListByStatusId(String signalStatusId);



    List<Map> getDeviceSpeedBySignalStatusId(String signalStatusId);


    List<JobSignalStatusDevice> pageList(@Param("a") int a,@Param("b") int b);




}
