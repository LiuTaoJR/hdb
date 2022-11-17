package com.xq.hdb.controller;


import com.xq.hdb.service.SignalRessourceService;
import com.xq.hdb.vo.SignalRessourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/signalRessource")
public class SignalRessourceController {


    @Autowired
    private SignalRessourceService signalRessourceService;


    @PostMapping("/postPull")
    public List<SignalRessourceVO> postPullSignalStatus(@RequestBody SignalRessourceVO signalRessourceVO) {
        return null;
    }


    @GetMapping("/getPull")
    public List<SignalRessourceVO> getSignalStatus(
            @RequestParam(value = "jobId", required = false) String jobId,
            @RequestParam(value = "speed", required = false) Long speed,
            @RequestParam(value = "deviceId", required = false) String deviceId,
            @RequestParam(value = "start", required = false) String eventId,
            @RequestParam(value = "end", required = false) String stutas) {
        return null;
    }


}
