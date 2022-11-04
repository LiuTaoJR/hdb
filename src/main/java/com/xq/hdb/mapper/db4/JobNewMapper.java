package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobNew;

public interface JobNewMapper extends BaseMapper<JobNew> {

    Integer isExists(String jobId);
}
