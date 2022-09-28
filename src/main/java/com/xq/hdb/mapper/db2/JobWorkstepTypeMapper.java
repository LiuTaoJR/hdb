package com.xq.hdb.mapper.db2;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.JobWorkstepType;
import com.xq.hdb.vo.WorkstepVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author qjk
 * @date 2022-06-07
 */
@Mapper
public interface JobWorkstepTypeMapper extends BaseMapper<JobWorkstepType>{




    Integer deleteTypeByWorkstepId(String workstepId);


}
