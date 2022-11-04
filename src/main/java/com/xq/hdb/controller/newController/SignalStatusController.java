package com.xq.hdb.controller.newController;


import com.xq.hdb.service.JobIdTimeService;
import com.xq.hdb.service.SignalStatusService;
import com.xq.hdb.vo.ResVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/statistics")
@Api(value = "设备信息接口", tags = {"设备信息接口"})
public class SignalStatusController {
    private static final Logger logger = LoggerFactory.getLogger(SignalStatusController.class);


    @Autowired
    private SignalStatusService signalStatusService;
    
    @Autowired
    private JobIdTimeService jobIdTimeService;


    @ApiOperation("今日统计")
    @GetMapping("/getStatisticsDate")
    public ResVo getStatisticsDate(
            @RequestParam(value = "date", defaultValue = "2022-10-10") Date date,
            @RequestParam(value = "deviceId", defaultValue = "7706") String deviceId,
            @RequestParam(value = "eventId", defaultValue = "200041") String eventId) {
        try {
            List<Map> maps=signalStatusService.getStatisticsDate(date,deviceId,eventId);
            logger.info("signalStatus getStatisticsDate date:"+date+"deviceId: "+deviceId+"eventId: "+eventId);
            return  new ResVo("200","success",maps);
        }catch (Exception e){
            return  new ResVo("-1","fail","查询失败");
        }

    }

    @ApiOperation("今日活件统计")
    @GetMapping("/getJobIdOnly")
    public ResVo getJobIdOnly() {
        try {
            Date date=new Date();
            return  new ResVo("200","success",signalStatusService.getJobIdOnly(date));
        }catch (Exception e){
            return  new ResVo("-1","fail","查询失败");
        }
    }

    @ApiOperation("活件查询")
    @GetMapping("/getJobIdTime")
    public ResVo getJobIdTime(
            @RequestParam(value = "date", defaultValue = "2022-10-10") Date date) {
        try {
            return  new ResVo("200","success",jobIdTimeService.getJobIdTime(date));
        }catch (Exception e){
            return  new ResVo("-1","fail","查询失败");
        }
    }



}
