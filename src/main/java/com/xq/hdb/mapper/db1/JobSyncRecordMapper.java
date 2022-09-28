package com.xq.hdb.mapper.db1;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobSyncRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author qjk
 * @date 2022-06-07
 */
@Mapper
public interface JobSyncRecordMapper extends BaseMapper<JobSyncRecord> {


    /**
     * 根据同步状态获取jobId
     * @param status
     * @return
     */
    List<String> getjobIdBySyncStatusJob(String status);



    /**
     * 根据同步状态获取jobId
     * @param status
     * @return
     */
    List<String> getjobIdBySyncStatusWorkstep(String status);



    /**
     * 校验jobId是否存在
     * @param jobId
     * @return
     */
    Integer isExists(String jobId);





    Integer upsateSyncStatusJob(@Param("jobId") String jobId, @Param("status") String status);




    Integer upsateSyncStatusWorkstep(@Param("jobId") String jobId, @Param("status") String status);



}
