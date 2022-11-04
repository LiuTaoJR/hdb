package com.xq.hdb.controller;


import com.xq.hdb.service.DeviceActionSubscriptionService;
import com.xq.hdb.utils.AssignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping
public class ReceiveSubscriptionController {



    @Autowired
    private DeviceActionSubscriptionService deviceActionSubscriptionService;



    /**
     * 3.2.5接收订阅信息订阅
     * @return
     */
    @PostMapping("/")
    @ResponseBody
    public String dealSignal(@RequestBody String jsonStr){
        try {
            String json = AssignUtils.toUtf8String(jsonStr);
            if(json.contains("SignalNotification")){
                log.info("3.1.15接收订阅信息 JsonStr start:"+json);
            }else{
                log.info("3.2.5接收订阅信息 JsonStr start:"+json);
            }
            return deviceActionSubscriptionService.dealSignal(json);
        }catch (Exception e){
            e.printStackTrace();
            return "500";
        }
    }

}
