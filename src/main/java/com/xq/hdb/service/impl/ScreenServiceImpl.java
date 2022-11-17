package com.xq.hdb.service.impl;

import com.xq.hdb.entity.screen.ScreenData;
import com.xq.hdb.mapper.db3.JobSignalStatusDeviceNewMapper;
import com.xq.hdb.mapper.db3.ScreenDataMapper;
import com.xq.hdb.service.ScreenService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.vo.DeviceDataVO;
import com.xq.hdb.vo.screen.ScreenDataVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private JobSignalStatusDeviceNewMapper deviceNewMapper;

    @Autowired
    private ScreenDataMapper screenDataMapper;

    @Override
    public Map<String,Integer> getDeviceSpeed(Date date) {
        Map<String,Integer> result = new HashMap<>();
        result.put("CD1025",0);
        result.put("CD1020",0);
        result.put("CX1047",0);
        result.put("XL1624",0);
        List<Map> mapGroup=deviceNewMapper.getDeviceSpeedGroup(DateUtils.getDayStart(date), DateUtils.getDayEnd(date));
        for (Map Device : mapGroup) {
            String deviceId = Device.get("device_id").toString();
            //查询成品数量
            List<DeviceDataVO> goodList = deviceNewMapper.getPrintAmountByDeviceId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
            if(goodList.size()>0){
                Integer last=Integer.valueOf(goodList.get(goodList.size()-1).getSpeed());
                switch (deviceId){
                    case "1025":
                        result.put("CD1025",last);
                        break;
                    case "4406":
                        result.put("CD1020",last);
                        break;
                    case "7706":
                        result.put("CX1047",last);
                        break;
                    case "1162":
                        result.put("XL1624",last);
                        break;
                }
            }
        }
        return result;
    }

    @Override
    public List<Map> getProGoodAmountMonth(Date date) {
        List<Map> groupDevice = deviceNewMapper.getGroupDevice(DateUtils.getDayStart(date), DateUtils.getDayEnd(date));
        int printCount = 0;
        if (groupDevice.size() > 0) {
            for (Map Device : groupDevice) {
                String deviceId = Device.get("device_id").toString();
                //查询成品数量
                List<DeviceDataVO> goodList = deviceNewMapper.getPrintAmountByDeviceId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
                if(goodList.size()>0){
                    Integer first=Integer.valueOf(goodList.get(0).getTotalProductionCounter());
                    Integer last=Integer.valueOf(goodList.get(goodList.size()-1).getTotalProductionCounter());
                    printCount = printCount+(last-first);
                }
            }
        }

        //先删除
        screenDataMapper.removeScreenByTime(DateUtils.dateToStr(date));
        //保存到screen表里
        ScreenData screenData=new ScreenData();
        screenData.setId(AssignUtils.getUUid());
        screenData.setProductGoodAmount(printCount);
        screenData.setQueryTime(DateUtils.dateToStr(date));
        screenData.setCreateTime(new Date());
        screenDataMapper.insert(screenData);

        List<Map> maps=screenDataMapper.getScreenData(DateUtils.dateToStr(date));

        return maps;
    }

    @Override
    public Map<String, Object> getDeviceDataToDay(Date date) {
        List<Map> groupDevice = deviceNewMapper.getGroupDevice(DateUtils.getDayStart(date), DateUtils.getDayEnd(date));
        Map<String, Object> result = new HashMap<>();
        result.put("CD1025",0);
        result.put("CD1020",0);
        result.put("CX1047",0);
        result.put("XL1624",0);
        if (groupDevice.size() > 0) {
            for (Map Device : groupDevice) {
                String deviceId = Device.get("device_id").toString();
                //查询每台设备每日印张数
                List<DeviceDataVO> goodList = deviceNewMapper.getPrintAmountByDeviceId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
                if(goodList.size()>0){
                  Integer first=Integer.valueOf(goodList.get(0).getTotalProductionCounter());
                  Integer last=Integer.valueOf(goodList.get(goodList.size()-1).getTotalProductionCounter());
                  Integer printAmount=last-first;
                    switch (deviceId){
                        case "1025":
                            result.put("CD1025",printAmount);
                            break;
                        case "4406":
                            result.put("CD1020",printAmount);
                            break;
                        case "7706":
                            result.put("CX1047",printAmount);
                            break;
                        case "1162":
                            result.put("XL1624",printAmount);
                            break;
                    }

                }
            }
        }

        return result;
    }

    @Override
    public ScreenDataVO getScreenData(Date date) {
        ScreenDataVO vo=new ScreenDataVO();
        vo.setDeviceSpeed(this.getDeviceSpeed(date));
        vo.setProGoodAmountMonth(this.getProGoodAmountMonth(date));
        vo.setDeviceDataToDay(this.getDeviceDataToDay(date));
        return vo;
    }


    //过滤成品
    public Map getGood(List<DeviceDataVO> voList, String startStr, String endStr) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        String jobid = null;
        boolean flag = true;
        for (DeviceDataVO endProduct : voList) {
            if (flag && endProduct.getStatusDetails().equals(startStr)) {
                flag = false;
                jobid = endProduct.getJobId();
                map.put(jobid, endProduct.getProductionCounter());
            }

            if (!flag && endProduct.getStatusDetails().equals(endStr)) {
                flag = true;
                if (endProduct.getJobId().equals(jobid)) {
                    int a = Integer.valueOf(map.get(jobid).toString());
                    int b = Integer.valueOf(endProduct.getProductionCounter());
                    int c;
                    if (map2.get(jobid) == null) {
                        c = b - a;
                    } else {
                        c = Integer.valueOf(map2.get(jobid).toString()) + (b - a);
                    }
                    map.put(jobid, c);
                    map2.put(jobid, c);
                    map3.put(jobid, c);
                }
            }
        }
        return map3;
    }
}
