package com.xq.hdb.controller;


import com.xq.hdb.service.JobWorkstepCopyService;
import com.xq.hdb.service.JobWorkstepService;
import com.xq.hdb.vo.WorkstepVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作步骤信息Controller
 *
 * @author qjk
 * @date 2022-05-31
 */
@Slf4j
@RestController
@RequestMapping("/workstep")
public class JobWorkstepController {



    @Autowired
    private JobWorkstepService jobWorkstepService;

    @Autowired
    private JobWorkstepCopyService jobWorkstepCopyService;




    @PostMapping("/workstepInsert")
    public Map workstepInsert(@RequestBody String jsonStr){
        return jobWorkstepService.workstepInsert(jsonStr);
    }



    /**
     * 新增工作步骤信息
     */
    @GetMapping("/pullWorkstep")
    public void pullWorkstep(){
        jobWorkstepService.pullWorkstep();
    }









    @PostMapping("/postPull")
    public List<WorkstepVO> postPullWorkstep(@RequestBody WorkstepVO workstepVO){
        return jobWorkstepService.postPullWorkstep(workstepVO);
    }




    @GetMapping("/getPullWorkstep")
    public Map getPullWorkstep(
            @RequestParam(value = "jobId", required = false) String jobId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end){



        return jobWorkstepService.getPullWorkstep(jobId, status, deviceId, start, end);
    }



    //根据jobId查询印刷活件详情
    @GetMapping("/getPullWorkstepByJobId")
    public Map getPullWorkstepByJobId(@RequestParam(value = "jobId", required = true) String jobId){
        return jobWorkstepCopyService.getPullWorkstepByJobId(jobId);
    }




}
