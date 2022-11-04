package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobIdTime;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JobIdTimeMapper extends BaseMapper<JobIdTime> {

    List<Map> getJobIdTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Map> getOnly(@Param("jobId") String jobId,@Param("time") String time);

}
