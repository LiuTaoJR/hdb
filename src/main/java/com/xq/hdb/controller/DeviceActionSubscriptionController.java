package com.xq.hdb.controller;


import com.xq.hdb.service.DeviceActionSubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/deviceActionSubscription")
public class DeviceActionSubscriptionController {


    @Autowired
    private DeviceActionSubscriptionService deviceActionSubscriptionService;


    /**
     * 3.2.5设备行动订阅
     *
     * @param action
     * @param url
     * @param type
     * @return
     */
    @GetMapping("/subscription")
    @ResponseBody
    public Map<String, Object> subscription(String action, String url, String type) {
        return deviceActionSubscriptionService.subscription(action, url, type);
    }


    @PostMapping("/dealSignal")
    @ResponseBody
    public String dealSignal(@RequestBody String jsonStr) {
        return deviceActionSubscriptionService.dealSignal(jsonStr);
    }


}
