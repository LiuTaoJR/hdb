package com.xq.hdb.mapper.db1;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobSignalStatusDevicePhasePart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author qjk
 * @date 2022-06-07
 */
@Mapper
public interface JobSignalStatusDevicePhasePartMapper extends BaseMapper<JobSignalStatusDevicePhasePart> {
    List<JobSignalStatusDevicePhasePart> pageList(@Param("a") int a, @Param("b") int b);
}
