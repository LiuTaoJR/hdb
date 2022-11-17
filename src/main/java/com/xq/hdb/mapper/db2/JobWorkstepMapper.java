package com.xq.hdb.mapper.db2;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobWorkstep;
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
public interface JobWorkstepMapper extends BaseMapper<JobWorkstep> {


    Integer isExists(String workstepId);


    List<JobWorkstep> getWorkstepList(@Param("jobId") String jobId, @Param("status") String status, @Param("deviceId") String deviceId, @Param("start") String start, @Param("end") String end);


    List<String> getTypesByWorkstepId(String workstepId);


    /**
     * 根据日期获取
     *
     * @return
     */
    List<Map> getPullWorkstepByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * 根据jobId
     *
     * @return
     */
    List<Map> getPullWorkstepByJobId(String jobId);


}
