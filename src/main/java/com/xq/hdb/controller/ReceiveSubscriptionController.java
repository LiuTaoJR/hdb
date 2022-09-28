package com.xq.hdb.controller;


import com.xq.hdb.service.DeviceActionSubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;

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
            String json = this.toUtf8String(jsonStr);
            log.info("DeviceActionSubscriptionController json start:"+json);
            return deviceActionSubscriptionService.dealSignal(json);
        }catch (Exception e){
            e.printStackTrace();
            return "500";
        }
    }

    public  String toUtf8String(String string) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c <= 255) {
                stringBuffer.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes(StandardCharsets.UTF_8);
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int value : b) {
                    int k = value;
                    if (k < 0) k += 256;
                    stringBuffer.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return stringBuffer.toString();
    }


}
