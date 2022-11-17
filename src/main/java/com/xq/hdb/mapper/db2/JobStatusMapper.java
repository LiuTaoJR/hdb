package com.xq.hdb.mapper.db2;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobStatus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author qjk
 * @date 2022-06-07
 */
@Mapper
public interface JobStatusMapper extends BaseMapper<JobStatus> {


    Integer delectJobStatusByjobId(String jobId);


}
