package com.xq.hdb.controller;


import com.xq.hdb.service.JobService;
import com.xq.hdb.vo.JobVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/job")
public class JobController {


    @Autowired
    private JobService jobService;


    @PostMapping("/jobInsert")
    @ResponseBody
    public Map jobInsert(@RequestBody String jsonStr) {
        return jobService.jobInsert(jsonStr);
    }


    @PostMapping("/postPull")
    public List<JobVO> postPullSignalStatus(@RequestBody JobVO jobVO) {
        return null;
    }


    @GetMapping("/getPull")
    public List<JobVO> getSignalStatus(
            @RequestParam(value = "jobId", required = false) String jobId,
            @RequestParam(value = "speed", required = false) Long speed,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "start", required = false) String eventId,
            @RequestParam(value = "end", required = false) String stutas) {
        return null;
    }


    @GetMapping("/getJobByJobId")
    public Map getJobByJobId(String jobId) {
        return jobService.getJobByJobId(jobId);
    }


}
