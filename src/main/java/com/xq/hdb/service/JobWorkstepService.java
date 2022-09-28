package com.xq.hdb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.hdb.entity.JobWorkstep;
import com.xq.hdb.vo.WorkstepVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface JobWorkstepService extends IService<JobWorkstep> {





    Map workstepInsert(@RequestBody String jsonStr);


    /**
     * 定时拉取工作步骤信息
     */
    void pullWorkstep();



    /**
     * post——josn  拉取数据
     * @param workstepVO
     * @return
     */
    List<WorkstepVO> postPullWorkstep(WorkstepVO workstepVO);





    Map getPullWorkstep(String jobId,String status,String deviceId, Date start, Date end);




    Map getPullWorkstepByDate(Date date);




    Map getPullWorkstepByJobId(String jobId);






}
