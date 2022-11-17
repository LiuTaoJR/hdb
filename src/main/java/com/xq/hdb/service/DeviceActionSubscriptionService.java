package com.xq.hdb.service;


import java.util.Map;

public interface DeviceActionSubscriptionService {


    /**
     * 设备行动订阅
     *
     * @param action
     * @param url
     * @param type
     * @return
     */
    Map<String, Object> subscription(String action, String url, String type);


    /**
     * @param jsonStr
     */
    String dealSignal(String jsonStr);


}
