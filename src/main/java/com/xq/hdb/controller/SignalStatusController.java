package com.xq.hdb.controller;


import com.xq.hdb.service.SignalStatusService;
import com.xq.hdb.vo.SignalStatusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/signalStatus")
public class SignalStatusController {
    private static final Logger logger = LoggerFactory.getLogger(SignalStatusController.class);


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
        return signalStatusService.getPullSignalStatusCopyByDate(date, deviceId, currentPage, pageSize);
    }

    @GetMapping("/getStatisticsDate")
    public List<Map> getStatisticsDate(
            @RequestParam(value = "date") Date date,
            @RequestParam(value = "deviceId") String deviceId,
            @RequestParam(value = "eventId") String eventId) {
        List<Map> maps=signalStatusService.getStatisticsDate(date,deviceId,eventId);
        logger.info("signalStatus getStatisticsDate date:"+date+"deviceId: "+deviceId+"eventId: "+eventId);
        return maps;
    }

    @GetMapping("/getJobIdOnly")
    public List<Map> getJobIdOnly(
            @RequestParam(value = "date") Date date,
            @RequestParam(value = "deviceId") String deviceId) {
        return signalStatusService.getJobIdOnly(date,deviceId);
    }



}
