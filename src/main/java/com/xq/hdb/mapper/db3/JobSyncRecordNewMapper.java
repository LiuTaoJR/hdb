package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSyncRecordNew;

public interface JobSyncRecordNewMapper extends BaseMapper<JobSyncRecordNew> {


    /**
     * 校验jobId是否存在
     * @param jobId
     * @return
     */
    Integer isExists(String jobId);
}
