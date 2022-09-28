package com.xq.hdb.controller;


import com.xq.hdb.service.SignalStatusService;
import com.xq.hdb.vo.SignalStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pyx/api/HBD_DEVICE_MGR/signalStatus")
public class SignalStatusController {


    @Autowired
    private SignalStatusService signalStatusService;






    @PostMapping("/postPull")
    public List<SignalStatusVO> postPullSignalStatus(@RequestBody SignalStatusVO signalStatusVO){
        return signalStatusService.postPullSignalStatus(signalStatusVO);
    }




    @GetMapping("/getPull")
    public Map getSignalStatus(
            @RequestParam(value = "jobId", required = false) String jobId,
            @RequestParam(value = "speed", required = false) Long speed,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "eventId", required = false) String eventId,
            @RequestParam(value = "stutas", required = false) String stutas) {



        return signalStatusService.getPullSignalStatus(jobId, speed, deviceId, eventId, stutas);
    }



    @GetMapping("/getPullSignalStatusByDate")
    public Map getPullSignalStatusByDate(@RequestParam(value = "date", required = false) Date date,
                                         @RequestParam(value = "deviceId", required = false) String deviceId,
                                         @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return signalStatusService.getPullSignalStatusByDate(date, deviceId, currentPage, pageSize);
    }



}
