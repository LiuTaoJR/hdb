package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSyncRecordNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobSyncRecordNewMapper extends BaseMapper<JobSyncRecordNew> {


    /**
     * 校验jobId是否存在
     *
     * @param jobId
     * @return
     */
    Integer isExists(String jobId);

    /**
     * 根据同步状态获取jobId
     *
     * @param status
     * @return
     */
    List<String> getjobIdBySyncStatusJob(String status);


    Integer upsateSyncStatusJob(@Param("jobId") String jobId, @Param("status") String status);

    /**
     * 根据同步状态获取jobId
     *
     * @param status
     * @return
     */
    List<String> getjobIdBySyncStatusWorkstep(String status);

    Integer upsateSyncStatusWorkstep(@Param("jobId") String jobId, @Param("status") String status);


    //根据jobId删除
    Integer delByJobId(String jobId);

}
