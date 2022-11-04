package com.xq.hdb.mapper.db4;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobWorkstepTypeNew;

public interface JobWorkstepTypeNewMapper extends BaseMapper<JobWorkstepTypeNew> {

    Integer deleteTypeByWorkstepId(String workstepId);

}
