package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobWorkstepNew;

import java.util.List;
import java.util.Map;

public interface JobWorkstepNewMapper extends BaseMapper<JobWorkstepNew> {

    Integer isExists(String workstepId);

    List<Map> getPullWorkstepByJobId(String jobId);

}
