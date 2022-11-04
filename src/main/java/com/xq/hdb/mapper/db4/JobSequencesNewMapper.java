package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSequencesNew;

public interface JobSequencesNewMapper extends BaseMapper<JobSequencesNew> {

    Integer delectJobSequencesByjobId(String jobId);

}
