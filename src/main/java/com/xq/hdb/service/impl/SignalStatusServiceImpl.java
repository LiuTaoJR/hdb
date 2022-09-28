package com.xq.hdb.service.impl;

import com.xq.hdb.entity.JobSignalStatus;
import com.xq.hdb.entity.JobSignalStatusDevice;
import com.xq.hdb.entity.JobSignalStatusDeviceEvent;
import com.xq.hdb.entity.JobSignalStatusDevicePhase;
import com.xq.hdb.mapper.db1.*;
import com.xq.hdb.service.SignalStatusService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.StringUtils;
import com.xq.hdb.vo.SignalStatusDeviceEventVO;
import com.xq.hdb.vo.SignalStatusDeviceInfoVO;
import com.xq.hdb.vo.SignalStatusDevicePhaseVO;
import com.xq.hdb.vo.SignalStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



    @Override
    public List<SignalStatusVO> postPullSignalStatus(SignalStatusVO signalStatusVO) {
        return null;
    }




    @Override
    public Map getPullSignalStatus(String jobId, Long speed, String deviceId, String eventId, String stutas) {
        Map map = new HashMap();
        List<SignalStatusVO> statusVOList = new ArrayList<>();

        List<JobSignalStatus> JobSignalStatus = jobSignalStatusMapper.getStatusList(AssignUtils.paramEncrypt(jobId), AssignUtils.paramEncrypt(speed),AssignUtils.paramEncrypt(deviceId),AssignUtils.paramEncrypt(eventId),AssignUtils.paramEncrypt(stutas));
        if(JobSignalStatus != null && JobSignalStatus.size() > 0){
            for(JobSignalStatus signalStatus : JobSignalStatus){
                SignalStatusVO signalStatusVO = new SignalStatusVO();

                List<JobSignalStatusDevice> statusDeviceList = jobSignalStatusDeviceMapper.getStatusDeviceListByStatusId(signalStatus.getId());
                if(statusDeviceList != null && statusDeviceList.size() > 0){
                    List<SignalStatusDeviceInfoVO> deviceInfoVOList = new ArrayList();
                    for(JobSignalStatusDevice statusDeviceStr : statusDeviceList){
                        SignalStatusDeviceInfoVO statusDeviceInfoVO = deviceStrToDeviceInfoVO(statusDeviceStr);
                        deviceInfoVOList.add(statusDeviceInfoVO);
                    }
                    signalStatusVO.setDeviceInfo(deviceInfoVOList);
                }
                statusVOList.add(signalStatusVO);
            }
        }


        map.put("SignalStatus",statusVOList);
        return map;
    }



    public SignalStatusDeviceInfoVO deviceStrToDeviceInfoVO(JobSignalStatusDevice statusDeviceStr){
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
        if(deviceEventList != null && deviceEventList.size() > 0){
            List<SignalStatusDeviceEventVO> eventVOList = new ArrayList<>();
            for(JobSignalStatusDeviceEvent deviceEventStr : deviceEventList){
                SignalStatusDeviceEventVO eventVO = new SignalStatusDeviceEventVO();
                eventVO.setEventID(AssignUtils.decryptionToStr(deviceEventStr.getEventId()));
                eventVO.setEventValue(AssignUtils.decryptionToStr(deviceEventStr.getEventValue()));
                eventVOList.add(eventVO);
            }
            deviceInfoVO.setEvent(eventVOList);
        }

        //处理JobPhase
        List<JobSignalStatusDevicePhase> devicePhaseList = jobSignalStatusDevicePhaseMapper.getDevicePhaseListByDeviceId(statusDeviceStr.getId());
        if(devicePhaseList != null && devicePhaseList.size() > 0){
            List<SignalStatusDevicePhaseVO> phaseVOList = new ArrayList<>();
            for(JobSignalStatusDevicePhase devicePhaseStr : devicePhaseList){
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
        if(StringUtils.isNotEmpty(deviceId)){
            deviceId = AssignUtils.encrypt(deviceId);
        }


        //当前页默认第一页
        //每页显示数量
        if(currentPage == null || currentPage < 1){
            currentPage = 1;
        }

        //每页显示数量
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        //获取总记录数
        int recordCount = jobSignalStatusMapper.getSignalStatusCountByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId);

        //总页数
        Integer pageCount;
        if(recordCount % pageSize == 0){
            pageCount = recordCount / pageSize;
        }else{
            pageCount = recordCount / pageSize + 1;
        }

        int startIndex = (currentPage-1) * pageSize;

        List<Map> signalStatusStrList = jobSignalStatusMapper.pagingGetSignalStatusByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date), deviceId, startIndex, pageSize);

        //注：下面数据没有用sql查一条而是查出合集，是为了以后扩展性；

        if(signalStatusStrList != null && signalStatusStrList.size() > 0){
            List resultList = new ArrayList();
            for(Map signalStatusMap : signalStatusStrList){

                String signalStatusId = signalStatusMap.get("id").toString();

                //deviceId
                List<Map> headerList = jobSignalStatusHeaderMapper.getHeaderBySignalStatusId(signalStatusId);
                if(headerList != null && headerList.size() > 0){
                    Map map = new HashMap();
                    map.put("deviceId",AssignUtils.decryptionToStr(headerList.get(0).get("device_id")));

                    //根据signalStatusId与deviceId获取header里时间
                    String deviceId1 = headerList.get(0).get("device_id").toString();
                    Date time = jobSignalStatusHeaderMapper.getTimeByStatusIdAndDeviceId(signalStatusId, deviceId1);
                    map.put("time",time);
                    Date createTime = jobSignalStatusHeaderMapper.getCreateTimeByStatusIdAndDeviceId(signalStatusId, deviceId1);
                    map.put("createTime",createTime);

                    //Speed
                    List<Map> deviceInfoList = jobSignalStatusDeviceMapper.getDeviceSpeedBySignalStatusId(signalStatusId);
                    if(deviceInfoList != null && deviceInfoList.size() > 0){
                        map.put("Speed",AssignUtils.decryptionToLong(String.valueOf(deviceInfoList.get(0).get("speed"))));

                        //Event
                        List<Map> eventList = jobSignalStatusDeviceEventMapper.getDeviceEventBySignalStatusId(deviceInfoList.get(0).get("id").toString());
                        if(eventList != null && eventList.size() > 0){
                            map.put("EventID",AssignUtils.decryptionToStr(eventList.get(0).get("event_id")));
                        }

                        //JobID与Status
                        List<Map> jobPhaseList = jobSignalStatusDevicePhaseMapper.getJobPhaseByDeviceInfoId(deviceInfoList.get(0).get("id").toString());
                        if(jobPhaseList != null && jobPhaseList.size() > 0){
                            map.put("Status",AssignUtils.decryptionToStr(jobPhaseList.get(0).get("status")));
                            map.put("JobID",AssignUtils.decryptionToStr(jobPhaseList.get(0).get("job_id")));
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




}