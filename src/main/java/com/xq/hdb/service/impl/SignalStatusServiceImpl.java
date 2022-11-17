package com.xq.hdb.service.impl;

import com.xq.hdb.entity.JobSignalStatus;
import com.xq.hdb.entity.JobSignalStatusDevice;
import com.xq.hdb.entity.JobSignalStatusDeviceEvent;
import com.xq.hdb.entity.JobSignalStatusDevicePhase;
import com.xq.hdb.mapper.db1.*;
import com.xq.hdb.mapper.db3.*;
import com.xq.hdb.service.SignalStatusService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.StringUtils;
import com.xq.hdb.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.*;

@Slf4j
@Service
public class SignalStatusServiceImpl implements SignalStatusService {


    @Autowired
    private JobSignalStatusMapper jobSignalStatusMapper;

    @Autowired
    private JobSignalStatusDeviceMapper jobSignalStatusDeviceMapper;

    @Autowired
    private JobSignalStatusHeaderMapper jobSignalStatusHeaderMapper;

    @Autowired
    private JobSignalStatusDeviceEventMapper jobSignalStatusDeviceEventMapper;

    @Autowired
    private JobSignalStatusDevicePhaseMapper jobSignalStatusDevicePhaseMapper;

    @Autowired
    private JobSignalStatusNewMapper jobSignalStatusNewMapper;

    @Autowired
    private JobSignalStatusHeaderNewMapper jobSignalStatusHeaderNewMapper;

    @Autowired
    private JobSignalStatusDeviceNewMapper jobSignalStatusDeviceNewMapper;

    @Autowired
    private JobSignalStatusDeviceEventNewMapper jobSignalStatusDeviceEventNewMapper;

    @Autowired
    private JobSignalStatusDevicePhaseNewMapper jobSignalStatusDevicePhaseNewMapper;


    @Override
    public List<SignalStatusVO> postPullSignalStatus(SignalStatusVO signalStatusVO) {
        return null;
    }


    @Override
    public Map getPullSignalStatus(String jobId, Long speed, String deviceId, String eventId, String stutas) {
        Map map = new HashMap();
        List<SignalStatusVO> statusVOList = new ArrayList<>();

        List<JobSignalStatus> JobSignalStatus = jobSignalStatusMapper.getStatusList(AssignUtils.paramEncrypt(jobId), AssignUtils.paramEncrypt(speed), AssignUtils.paramEncrypt(deviceId), AssignUtils.paramEncrypt(eventId), AssignUtils.paramEncrypt(stutas));
        if (JobSignalStatus != null && JobSignalStatus.size() > 0) {
            for (JobSignalStatus signalStatus : JobSignalStatus) {
                SignalStatusVO signalStatusVO = new SignalStatusVO();

                List<JobSignalStatusDevice> statusDeviceList = jobSignalStatusDeviceMapper.getStatusDeviceListByStatusId(signalStatus.getId());
                if (statusDeviceList != null && statusDeviceList.size() > 0) {
                    List<SignalStatusDeviceInfoVO> deviceInfoVOList = new ArrayList();
                    for (JobSignalStatusDevice statusDeviceStr : statusDeviceList) {
                        SignalStatusDeviceInfoVO statusDeviceInfoVO = deviceStrToDeviceInfoVO(statusDeviceStr);
                        deviceInfoVOList.add(statusDeviceInfoVO);
                    }
                    signalStatusVO.setDeviceInfo(deviceInfoVOList);
                }
                statusVOList.add(signalStatusVO);
            }
        }


        map.put("SignalStatus", statusVOList);
        return map;
    }


    public SignalStatusDeviceInfoVO deviceStrToDeviceInfoVO(JobSignalStatusDevice statusDeviceStr) {
        SignalStatusDeviceInfoVO deviceInfoVO = new SignalStatusDeviceInfoVO();

        deviceInfoVO.setDeviceID(AssignUtils.decryptionToStr(statusDeviceStr.getDeviceId()));
        deviceInfoVO.setStatus(AssignUtils.decryptionToStr(statusDeviceStr.getStatus()));
        deviceInfoVO.setStatusDetails(AssignUtils.decryptionToStr(statusDeviceStr.getStatusDetails()));
        deviceInfoVO.setSpeed(AssignUtils.decryptionToLong(statusDeviceStr.getSpeed()));
        deviceInfoVO.setEventID(AssignUtils.decryptionToStr(statusDeviceStr.getEventId()));
        deviceInfoVO.setTotalProductionCounter(AssignUtils.decryptionToStr(statusDeviceStr.getTotalProductionCounter()));
        deviceInfoVO.setProductionCounter(AssignUtils.decryptionToStr(statusDeviceStr.getProductionCounter()));
        deviceInfoVO.setModuleIDs(AssignUtils.decryptionToStr(statusDeviceStr.getModuleIds()));

        //处理Event
        List<JobSignalStatusDeviceEvent> deviceEventList = jobSignalStatusDeviceEventMapper.getDeviceEventByDeviceId(statusDeviceStr.getId());
        if (deviceEventList != null && deviceEventList.size() > 0) {
            List<SignalStatusDeviceEventVO> eventVOList = new ArrayList<>();
            for (JobSignalStatusDeviceEvent deviceEventStr : deviceEventList) {
                SignalStatusDeviceEventVO eventVO = new SignalStatusDeviceEventVO();
                eventVO.setEventID(AssignUtils.decryptionToStr(deviceEventStr.getEventId()));
                eventVO.setEventValue(AssignUtils.decryptionToStr(deviceEventStr.getEventValue()));
                eventVOList.add(eventVO);
            }
            deviceInfoVO.setEvent(eventVOList);
        }

        //处理JobPhase
        List<JobSignalStatusDevicePhase> devicePhaseList = jobSignalStatusDevicePhaseMapper.getDevicePhaseListByDeviceId(statusDeviceStr.getId());
        if (devicePhaseList != null && devicePhaseList.size() > 0) {
            List<SignalStatusDevicePhaseVO> phaseVOList = new ArrayList<>();
            for (JobSignalStatusDevicePhase devicePhaseStr : devicePhaseList) {
                SignalStatusDevicePhaseVO phaseVO = new SignalStatusDevicePhaseVO();
                phaseVO.setStatus(AssignUtils.decryptionToStr(devicePhaseStr.getStatus()));
                phaseVO.setAmount(AssignUtils.decryptionToLong(devicePhaseStr.getAmount()));
                phaseVO.setStartTime(AssignUtils.decryptionToDate(devicePhaseStr.getStartTime()));
                phaseVO.setTotalAmount(AssignUtils.decryptionToLong(devicePhaseStr.getTotalAmount()));
                phaseVO.setWaste(AssignUtils.decryptionToLong(devicePhaseStr.getWaste()));
                phaseVO.setPercentCompleted(AssignUtils.decryptionToLong(devicePhaseStr.getPercentCompleted()));
                phaseVO.setCostCenterID(AssignUtils.decryptionToStr(devicePhaseStr.getCostCenterId()));
                phaseVO.setJobID(AssignUtils.decryptionToStr(devicePhaseStr.getJobId()));
                phaseVO.setWorkstepID(AssignUtils.decryptionToStr(devicePhaseStr.getWorkstepId()));

                phaseVOList.add(phaseVO);
            }
            deviceInfoVO.setJobPhase(phaseVOList);
        }


        return deviceInfoVO;
    }


    @Override
    public Map getPullSignalStatusByDate(Date date, String deviceId, Integer currentPage, Integer pageSize) {
        Map result = new HashMap();

        //加密deviceId
        if (StringUtils.isNotEmpty(deviceId)) {
            deviceId = AssignUtils.encrypt(deviceId);
        }


        //当前页默认第一页
        //每页显示数量
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }

        //每页显示数量
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        //获取总记录数
        int recordCount = jobSignalStatusMapper.getSignalStatusCountByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);

        //总页数
        Integer pageCount;
        if (recordCount % pageSize == 0) {
            pageCount = recordCount / pageSize;
        } else {
            pageCount = recordCount / pageSize + 1;
        }

        int startIndex = (currentPage - 1) * pageSize;

        List<Map> signalStatusStrList = jobSignalStatusMapper.pagingGetSignalStatusByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, startIndex, pageSize);

        //注：下面数据没有用sql查一条而是查出合集，是为了以后扩展性；

        if (signalStatusStrList != null && signalStatusStrList.size() > 0) {
            List resultList = new ArrayList();
            for (Map signalStatusMap : signalStatusStrList) {

                String signalStatusId = signalStatusMap.get("id").toString();

                //deviceId
                List<Map> headerList = jobSignalStatusHeaderMapper.getHeaderBySignalStatusId(signalStatusId);
                if (headerList != null && headerList.size() > 0) {
                    Map map = new HashMap();
                    map.put("deviceId", AssignUtils.decryptionToStr(headerList.get(0).get("device_id")));

                    //根据signalStatusId与deviceId获取header里时间
                    String deviceId1 = headerList.get(0).get("device_id").toString();
                    Date time = jobSignalStatusHeaderMapper.getTimeByStatusIdAndDeviceId(signalStatusId, deviceId1);
                    map.put("time", time);
                    Date createTime = jobSignalStatusHeaderMapper.getCreateTimeByStatusIdAndDeviceId(signalStatusId, deviceId1);
                    map.put("createTime", createTime);

                    //Speed
                    List<Map> deviceInfoList = jobSignalStatusDeviceMapper.getDeviceSpeedBySignalStatusId(signalStatusId);
                    if (deviceInfoList != null && deviceInfoList.size() > 0) {
                        map.put("Speed", AssignUtils.decryptionToLong(String.valueOf(deviceInfoList.get(0).get("speed"))));

                        //Event
                        List<Map> eventList = jobSignalStatusDeviceEventMapper.getDeviceEventBySignalStatusId(deviceInfoList.get(0).get("id").toString());
                        if (eventList != null && eventList.size() > 0) {
                            map.put("EventID", AssignUtils.decryptionToStr(eventList.get(0).get("event_id")));
                        }

                        //JobID与Status
                        List<Map> jobPhaseList = jobSignalStatusDevicePhaseMapper.getJobPhaseByDeviceInfoId(deviceInfoList.get(0).get("id").toString());
                        if (jobPhaseList != null && jobPhaseList.size() > 0) {
                            map.put("Status", AssignUtils.decryptionToStr(jobPhaseList.get(0).get("status")));
                            map.put("JobID", AssignUtils.decryptionToStr(jobPhaseList.get(0).get("job_id")));
                        }
                    }
                    resultList.add(map);
                }

            }

            result.put("currentPage", currentPage);
            result.put("pageSize", pageSize);
            result.put("pageCount", pageCount);
            result.put("recordCount", recordCount);
            result.put("SignalStatus", resultList);

        }

        return result;
    }

    @Override
    public Map getPullSignalStatusCopyByDate(Date date, String deviceId, Integer currentPage, Integer pageSize) {
        Map result = new HashMap();

        //当前页默认第一页
        //每页显示数量
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }

        //每页显示数量
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        //获取总记录数
        int recordCount = jobSignalStatusNewMapper.getSignalStatusCountByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);

        //总页数
        Integer pageCount;
        if (recordCount % pageSize == 0) {
            pageCount = recordCount / pageSize;
        } else {
            pageCount = recordCount / pageSize + 1;
        }

        int startIndex = (currentPage - 1) * pageSize;

        List<Map> signalStatusStrList = jobSignalStatusNewMapper.pagingGetSignalStatusByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, startIndex, pageSize);

        //注：下面数据没有用sql查一条而是查出合集，是为了以后扩展性；

        if (signalStatusStrList != null && signalStatusStrList.size() > 0) {
            List resultList = new ArrayList();
            for (Map signalStatusMap : signalStatusStrList) {

                String signalStatusId = signalStatusMap.get("id").toString();

                //deviceId
                List<Map> headerList = jobSignalStatusHeaderNewMapper.getHeaderBySignalStatusId(signalStatusId);
                if (headerList != null && headerList.size() > 0) {
                    Map map = new HashMap();
                    map.put("id", signalStatusId);
                    map.put("deviceId", headerList.get(0).get("device_id"));

                    //根据signalStatusId与deviceId获取header里时间
                    String deviceId1 = headerList.get(0).get("device_id").toString();
                    Date time = jobSignalStatusHeaderNewMapper.getTimeByStatusIdAndDeviceId(signalStatusId, deviceId1);
                    map.put("time", time);
                    Date createTime = jobSignalStatusHeaderNewMapper.getCreateTimeByStatusIdAndDeviceId(signalStatusId, deviceId1);
                    map.put("createTime", createTime);

                    //Speed
                    List<Map> deviceInfoList = jobSignalStatusDeviceNewMapper.getDeviceSpeedBySignalStatusId(signalStatusId);
                    if (deviceInfoList != null && deviceInfoList.size() > 0) {
                        map.put("Speed", String.valueOf(deviceInfoList.get(0).get("speed")));

                        //Event
                        List<Map> eventList = jobSignalStatusDeviceEventNewMapper.getDeviceEventBySignalStatusId(deviceInfoList.get(0).get("id").toString());
                        if (eventList != null && eventList.size() > 0) {
                            map.put("EventID", eventList.get(0).get("event_id"));
                        }

                        //JobID与Status
                        List<Map> jobPhaseList = jobSignalStatusDevicePhaseNewMapper.getJobPhaseByDeviceInfoId(deviceInfoList.get(0).get("id").toString());
                        if (jobPhaseList != null && jobPhaseList.size() > 0) {
                            map.put("Status", jobPhaseList.get(0).get("status"));
                            map.put("JobID", jobPhaseList.get(0).get("job_id"));
                        }
                    }
                    resultList.add(map);
                }

            }

            result.put("currentPage", currentPage);
            result.put("pageSize", pageSize);
            result.put("pageCount", pageCount);
            result.put("recordCount", recordCount);
            result.put("SignalStatus", resultList);

        }

        return result;
    }

    @Override
    public List<Map> getStatisticsDate(Date date, String deviceId, String eventId) {
        return jobSignalStatusHeaderNewMapper.getStatisticsDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, eventId);
    }

    @Override
    public List<Map> getJobIdOnly(Date date) {
        return jobSignalStatusNewMapper.getJobIdOnly(DateUtils.getDayStart(date), DateUtils.getDayEnd(date));
    }

    @Override
    public List<DeviceDataVO> getJobSignalStatus(Date date, String deviceId) {
        List<DeviceDataVO> maps = jobSignalStatusNewMapper.getJobSignalStatus(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
        log.info("JobSignalStatus 返回数据：" + maps);
        return maps;
    }

    //成品 Good Waste
    @Override
    public List<Map> getProductGood(Date date, String deviceId) {
        List<DeviceDataVO> maps = jobSignalStatusNewMapper.getJobSignalStatus(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
        List<Map> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        String jobid = null;
        boolean flag = true;
        for (DeviceDataVO endProduct : maps) {
            if (flag && endProduct.getStatusDetails().equals("Good")) {
                flag = false;
                jobid = endProduct.getJobId();
                map.put(jobid, endProduct.getProductionCounter());
            }

            if (!flag && endProduct.getStatusDetails().equals("Waste")) {
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
        result.add(map3);
        return result;
    }

    //生产废张 Waste Idling
    @Override
    public List<Map> getProductWaste(Date date, String deviceId) {
        List<DeviceDataVO> maps = jobSignalStatusNewMapper.getProductWaste(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, "合格品产量");
        return this.getDeviceInfoData(maps, "Waste", "Idling");
    }

    //过版纸 Waste Idling
    @Override
    public List<Map> getPassPaper(Date date, String deviceId) {
        List<DeviceDataVO> maps = jobSignalStatusNewMapper.getProductWaste(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, "基本准备");
        return this.getDeviceInfoData(maps, "Waste", "Idling");
    }

    @Override
    public List<Map> getPrintTime(Date date, String deviceId) {
        List<DeviceDataVO> maps = jobSignalStatusNewMapper.getJobSignalStatus(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
        List<Map> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        String jobid = null;
        boolean flag = true;
        for (DeviceDataVO endProduct : maps) {
            if (flag && endProduct.getStatusDetails().equals("Good")) {
                flag = false;
                jobid = endProduct.getJobId();
                map.put(jobid, endProduct.getTime());
            }

            if (!flag && endProduct.getStatusDetails().equals("Waste")) {
                flag = true;
                if (endProduct.getJobId().equals(jobid)) {
                    Date a = (Date) map.get(jobid);
                    Date b = endProduct.getTime();
                    int c;
                    if (map2.get(jobid) == null) {
                        c = (int) ((b.getTime() - a.getTime()) / (1000));
                    } else {
                        int d = (int) ((b.getTime() - a.getTime()) / (1000));
                        c = Integer.valueOf(map2.get(jobid).toString()) + d;
                    }
                    map.put(jobid, c);
                    map2.put(jobid, c);
                    map3.put(jobid, c);
                }
            }
        }
        result.add(map3);
        return result;
    }

    @Override
    public DeviceDataResultVO getDeviceDataResult(Date date, String deviceId) {
        DeviceDataResultVO vo = new DeviceDataResultVO();
        List<Map> productGoods = this.getProductGood(date, deviceId);
        List<Map> productWastes = this.getProductWaste(date, deviceId);
        List<Map> passPapers = this.getPassPaper(date, deviceId);
        List<Map> printTimes = this.getPrintTime(date, deviceId);
        vo.setProductGoodList(productGoods);
        vo.setProductWasteList(productWastes);
        vo.setPassPaperList(passPapers);
        vo.setPrintTimeList(printTimes);
        return vo;
    }

    @Override
    public Map<String, Object> aa(Date date, String deviceId) {
        List<Map> groupDevice = jobSignalStatusNewMapper.getGroupDeviceJobId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);
        Map<String, Object> result = new HashMap<>();
        if (groupDevice.size() > 0) {
            for (Map Device : groupDevice) {
                Map<String, Object> detailMap = new HashMap<>();
                String jobId = Device.get("jobid").toString();
                //查询成品数量
                List<DeviceDataVO> goodList = jobSignalStatusNewMapper.getDeviceByJobId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, jobId, null);
                Map goodMap = this.getGood(goodList, "Good", "Waste");
                if (goodMap.keySet().size() > 0) {
                    for (Object key : goodMap.keySet()) {
                        detailMap.put("Good", goodMap.get(key));
                    }
                } else {
                    detailMap.put("Good", 0);
                }


                //查询生产废张
                List<DeviceDataVO> productWasteList = jobSignalStatusNewMapper.getDeviceByJobId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, jobId, "合格品产量");
                Map productWasteMap = this.getWaste(productWasteList, "Waste", "Idling");
                if (productWasteMap.keySet().size() > 0) {
                    for (Object key : productWasteMap.keySet()) {
                        detailMap.put("WasteInProduction", productWasteMap.get(key));
                    }
                } else {
                    detailMap.put("WasteInProduction", 0);
                }


                //查询过版纸
                List<DeviceDataVO> passWasteList = jobSignalStatusNewMapper.getDeviceByJobId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, jobId, "基本准备");
                Map passWasteMap = this.getWaste(passWasteList, "Waste", "Idling");
                if (passWasteMap.keySet().size() > 0) {
                    for (Object key : passWasteMap.keySet()) {
                        detailMap.put("WasteInMakeready", passWasteMap.get(key));
                    }
                } else {
                    detailMap.put("WasteInMakeready", 0);
                }


                //查询成品印刷时间
                List<DeviceDataVO> goodTimeList = jobSignalStatusNewMapper.getDeviceByJobId(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, jobId, null);
                Map goodTimeMap = this.getProductGoodTime(goodTimeList, "Good", "Waste");
                if (goodTimeMap.keySet().size() > 0) {
                    for (Object key : goodTimeMap.keySet()) {
                        detailMap.put("ProductionGoodTime", goodTimeMap.get(key));
                    }
                } else {
                    detailMap.put("ProductionGoodTime", 0);
                }


                result.put(jobId, detailMap);
            }
        }

        return result;
    }

    //过滤相关设备数据
    public List<Map> getDeviceInfoData(List<DeviceDataVO> voList, String startStr, String endStr) {
        List<Map> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        String jobid = null;
        boolean flag = true;
        for (DeviceDataVO endProduct : voList) {
            if (flag && endProduct.getStatusDetails().equals(startStr)) {
                flag = false;
                jobid = endProduct.getJobId();
                map.put(jobid, endProduct.getTotalProductionCounter());
            }

            if (!flag && endProduct.getStatusDetails().equals(endStr)) {
                flag = true;
                if (endProduct.getJobId().equals(jobid)) {
                    int a = Integer.valueOf(map.get(jobid).toString());
                    int b = Integer.valueOf(endProduct.getTotalProductionCounter());
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
        result.add(map3);
        return result;
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


    //过滤废张
    public Map getWaste(List<DeviceDataVO> voList, String startStr, String endStr) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        String jobid = null;
        boolean flag = true;
        for (DeviceDataVO endProduct : voList) {
            if (flag && endProduct.getStatusDetails().equals(startStr)) {
                flag = false;
                jobid = endProduct.getJobId();
                map.put(jobid, endProduct.getTotalProductionCounter());
            }

            if (!flag && endProduct.getStatusDetails().equals(endStr)) {
                flag = true;
                if (endProduct.getJobId().equals(jobid)) {
                    int a = Integer.valueOf(map.get(jobid).toString());
                    int b = Integer.valueOf(endProduct.getTotalProductionCounter());
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

    //成品印刷时间
    public Map getProductGoodTime(List<DeviceDataVO> voList, String startStr, String endStr) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        String jobid = null;
        boolean flag = true;
        for (DeviceDataVO endProduct : voList) {
            if (flag && endProduct.getStatusDetails().equals(startStr)) {
                flag = false;
                jobid = endProduct.getJobId();
                map.put(jobid, endProduct.getTime());
            }

            if (!flag && endProduct.getStatusDetails().equals(endStr)) {
                flag = true;
                if (endProduct.getJobId().equals(jobid)) {
                    Date a = (Date) map.get(jobid);
                    Date b = endProduct.getTime();
                    int c;
                    if (map2.get(jobid) == null) {
                        c = (int) ((b.getTime() - a.getTime()) / (1000));
                    } else {
                        int d = (int) ((b.getTime() - a.getTime()) / (1000));
                        c = Integer.valueOf(map2.get(jobid).toString()) + d;
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
