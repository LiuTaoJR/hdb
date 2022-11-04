package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobWorkstepActualtimesNew;

public interface JobWorkstepActualtimesNewMapper extends BaseMapper<JobWorkstepActualtimesNew> {

    Integer deleteActualtimeByWorkstepId(String workstepId);

}
