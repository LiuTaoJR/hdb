package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobGangJobNew;

public interface JobGangJobNewMapper extends BaseMapper<JobGangJobNew> {

    Integer delectJobGangJobsByjobId(String jobId);

}
