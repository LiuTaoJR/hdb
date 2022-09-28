package com.xq.hdb.mapper.db2;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobWorkstepActualtimes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobWorkstepActualtimesMapper extends BaseMapper<JobWorkstepActualtimes> {



    List<JobWorkstepActualtimes> getActualTimesByWorkstepId(String workstepId);



    Integer deleteActualtimeByWorkstepId(String workstepId);


}
