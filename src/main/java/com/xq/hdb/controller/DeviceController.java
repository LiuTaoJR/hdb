package com.xq.hdb.controller;


import com.xq.hdb.service.SignalStatusService;
import com.xq.hdb.vo.SignalStatusVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/signalStatus")
@Api(value = "设备信息接口", tags = {"设备信息接口"})
public class DeviceController {

    @Autowired
    private SignalStatusService signalStatusService;


    @PostMapping("/postPull")
    public List<SignalStatusVO> postPullSignalStatus(@RequestBody SignalStatusVO signalStatusVO) {
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


    @ApiOperation("查询设备信息")
    @GetMapping("/getPullSignalStatusByDate")
    public Map getPullSignalStatusByDate(@RequestParam(value = "date", defaultValue = "2022-10-10") Date date,
                                         @RequestParam(value = "deviceId", defaultValue = "7706") String deviceId,
                                         @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return signalStatusService.getPullSignalStatusCopyByDate(date, deviceId, currentPage, pageSize);
    }


    @GetMapping("/getDeviceDataResult")
    public Map<String, Object> getDeviceDataResult(
            @RequestParam(value = "date") Date date,
            @RequestParam(value = "deviceId") String deviceId) {
        return signalStatusService.aa(date, deviceId);
    }


}

