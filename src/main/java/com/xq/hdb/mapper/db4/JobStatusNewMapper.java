package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobStatusNew;

public interface JobStatusNewMapper extends BaseMapper<JobStatusNew> {

    Integer delectJobStatusByjobId(String jobId);
}
