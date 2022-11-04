package com.xq.hdb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xq.hdb.entity.*;
import com.xq.hdb.entity.decrypt.*;
import com.xq.hdb.mapper.db1.*;
import com.xq.hdb.mapper.db3.*;
import com.xq.hdb.service.DataBaseDecryptService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class DataBaseDecryptServiceImpl implements DataBaseDecryptService {

    final int pageSize = 10000;

    @Autowired
    private JobSignalStatusDeviceMapper deviceOldMapper;
    @Autowired
    private JobSignalStatusDeviceNewMapper deviceNewMapper;


    @Autowired
    private JobSignalStatusDeviceEventMapper eventOldMapper;
    @Autowired
    private JobSignalStatusDeviceEventNewMapper eventNewMapper;


    @Autowired
    private JobSignalStatusDevicePhaseMapper phaseOldMapper;
    @Autowired
    private JobSignalStatusDevicePhaseNewMapper phaseNewMapper;


    @Autowired
    private JobSignalStatusMapper statusOldMapper;
    @Autowired
    private JobSignalStatusNewMapper statusNewMapper;

    @Autowired
    private JobSignalStatusDevicePhaseActivityMapper activityOldMapper;
    @Autowired
    private JobSignalStatusDevicePhaseActivityNewMapper newMapper;


    @Autowired
    private JobSignalStatusDevicePhaseMisDetailsMapper detailsOldMapper;
    @Autowired
    private JobSignalStatusDevicePhaseMisDetailsNewMapper detailsNewMapper;

    @Autowired
    private JobSignalStatusDevicePhasePartMapper partOldMapper;
    @Autowired
    private JobSignalStatusDevicePhasePartNewMapper partNewMapper;


    @Autowired
    private JobSignalStatusHeaderMapper headerOldMapper;
    @Autowired
    private JobSignalStatusHeaderNewMapper headerNewMapper;

    @Autowired
    private JobSignalStatusDeviceEventCopyMapper eventCopyMapper;

    ExecutorService pool = Executors.newFixedThreadPool(40);

    @Override
    public void decrypt() {
        this.update_event_id();
    }

    public void job_signal_status_device(){
        Integer totalCount = deviceOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDevice> oldList=deviceOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusDeviceNew deviceNew=new JobSignalStatusDeviceNew();
                for(JobSignalStatusDevice old:oldList){
                    BeanUtils.copyProperties(old,deviceNew);
                    deviceNew.setDeviceId(AssignUtils.decryptionToStr(old.getDeviceId()));
                    deviceNew.setStatus(AssignUtils.decryptionToStr(old.getStatus()));
                    deviceNew.setStatusDetails(AssignUtils.decryptionToStr(old.getStatusDetails()));
                    deviceNew.setSpeed(AssignUtils.decryptionToStr(old.getSpeed()));
                    deviceNew.setTotalProductionCounter(AssignUtils.decryptionToStr(old.getTotalProductionCounter()));
                    deviceNew.setProductionCounter(AssignUtils.decryptionToStr(old.getProductionCounter()));
                    deviceNewMapper.insert(deviceNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status_device_event(){
        Integer totalCount = eventCopyMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDeviceEventCopy> oldList=eventCopyMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusDeviceEventNew eventNew=new JobSignalStatusDeviceEventNew();
                for(JobSignalStatusDeviceEventCopy old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setEventId(AssignUtils.formatValue(old.getEventId()));
                    eventNewMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status_device_phase(){
        Integer totalCount = phaseOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDevicePhase> oldList=phaseOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusDevicePhaseNew eventNew=new JobSignalStatusDevicePhaseNew();
                for(JobSignalStatusDevicePhase old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setStatus(AssignUtils.decryptionToStr(old.getStatus()));
                    eventNew.setAmount(AssignUtils.decryptionToStr(old.getAmount()));
                    eventNew.setStartTime(AssignUtils.decryptionToStr(old.getStartTime()));
                    eventNew.setWaste(AssignUtils.decryptionToStr(old.getWaste()));
                    eventNew.setPercentCompleted(AssignUtils.decryptionToStr(old.getPercentCompleted()));
                    eventNew.setCostCenterId(AssignUtils.decryptionToStr(old.getCostCenterId()));
                    eventNew.setJobId(AssignUtils.decryptionToStr(old.getJobId()));
                    eventNew.setWorkstepId(AssignUtils.decryptionToStr(old.getWorkstepId()));
                    phaseNewMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status(){
        Integer totalCount = statusOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatus> oldList=statusOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusNew eventNew=new JobSignalStatusNew();
                for(JobSignalStatus old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setInsertTime(AssignUtils.decryptionToLong(old.getInsertTime().toString()));
                    eventNew.setInsertDate(AssignUtils.decryptionToLong(old.getInsertDate().toString()));
                    statusNewMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status_device_phase_activity(){
        Integer totalCount = activityOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDevicePhaseActivity> oldList=activityOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusDevicePhaseActivityNew eventNew=new JobSignalStatusDevicePhaseActivityNew();
                for(JobSignalStatusDevicePhaseActivity old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setActivityName(AssignUtils.decryptionToStr(old.getActivityName()));
                    eventNew.setActivityPersonalId(AssignUtils.decryptionToStr(old.getActivityPersonalId()));
                    eventNew.setActivityStartTime(AssignUtils.decryptionToStr(old.getActivityStartTime()));
                    newMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status_device_phase_mis_details(){
        Integer totalCount = detailsOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDevicePhaseMisDetails> oldList=detailsOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusDevicePhaseMisDetailsNew eventNew=new JobSignalStatusDevicePhaseMisDetailsNew();
                for(JobSignalStatusDevicePhaseMisDetails old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setWorkType(AssignUtils.decryptionToStr(old.getWorkType()));
                    detailsNewMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status_device_phase_part(){
        Integer totalCount = partOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDevicePhasePart> oldList=partOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusDevicePhasePartNew eventNew=new JobSignalStatusDevicePhasePartNew();
                for(JobSignalStatusDevicePhasePart old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setSheetName(AssignUtils.decryptionToStr(old.getSheetName()));
                    partNewMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void job_signal_status_header(){
        Integer totalCount = headerOldMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusHeader> oldList=headerOldMapper.pageList(10000*(page-1),10000);
                System.out.println(oldList.size()+"++++"+page+"+++++"+pageSize);
                JobSignalStatusHeaderNew eventNew=new JobSignalStatusHeaderNew();
                for(JobSignalStatusHeader old:oldList){
                    BeanUtils.copyProperties(old,eventNew);
                    eventNew.setRefId(AssignUtils.decryptionToStr(old.getRefId()));
                    eventNew.setDeviceId(AssignUtils.decryptionToStr(old.getDeviceId()));
                    eventNew.setTime(DateUtils.strToDateStr(AssignUtils.decryptionToStr(old.getTime())));
                    headerNewMapper.insert(eventNew);
                }
                System.out.println("+++++++++++"+page+"+++++++++++++++++");
            });
        }
        pool.shutdown();
    }

    public void update_event_id(){
        Integer totalCount = eventNewMapper.selectList(new QueryWrapper<>()).size();
        final int pageCount = totalCount % pageSize == 0 ? totalCount/pageSize : totalCount/pageSize +1;
        for (int i=1;i<= pageCount;i++){
            Integer page=new Integer(i);
            pool.submit(() -> {
                List<JobSignalStatusDeviceEventNew> oldList=eventNewMapper.pageList(10000*(page-1),10000);
                for(JobSignalStatusDeviceEventNew old:oldList){
                  if(old.getEventId().length()!=6){
                      //更新
                      old.setEventId(AssignUtils.formatValue(old.getEventId()));
                      eventNewMapper.updateById(old);
                  }
                }
                log.info("eventId规则转码完成");
            });
        }
        pool.shutdown();
    }
}
