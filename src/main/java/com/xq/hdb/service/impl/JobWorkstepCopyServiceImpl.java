package com.xq.hdb.service.impl;

import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.config.Log;
import com.xq.hdb.entity.decrypt.JobWorkstepActualtimesNew;
import com.xq.hdb.entity.decrypt.JobWorkstepNew;
import com.xq.hdb.entity.decrypt.JobWorkstepTypeNew;
import com.xq.hdb.enums.BusinessType;
import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import com.xq.hdb.mapper.db4.JobWorkstepActualtimesNewMapper;
import com.xq.hdb.mapper.db4.JobWorkstepNewMapper;
import com.xq.hdb.mapper.db4.JobWorkstepTypeNewMapper;
import com.xq.hdb.service.JobWorkstepCopyService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
@Service
public class JobWorkstepCopyServiceImpl implements JobWorkstepCopyService {

    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobWorkstepNewMapper jobWorkstepNewMapper;

    @Autowired
    private JobWorkstepActualtimesNewMapper actualtimesNewMapper;

    @Autowired
    private JobWorkstepTypeNewMapper jobWorkstepTypeNewMapper;

    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;

    /**
     * 获取工作步骤
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "pullWorkstep", businessType = BusinessType.INSERT)
    public synchronized void pullWorkstepCopy(){
        try{
            //获取jobId
            List<String> jobIdList = jobSyncRecordNewMapper.getjobIdBySyncStatusWorkstep("N");
            if(jobIdList != null && jobIdList.size()>0){
                for(String jobId : jobIdList){
                    String x = URLEncoder.encode(jobId, "UTF-8");
                    String url = hdbConstantConfig.getDomainName() +"/PrinectAPILocal/rest/job/"+x+"/workstep";
                    String  param = null;
                    Map<String, Object> map = HttpUtils.sendGetReturnMap(url,param,hdbConstantConfig.getAuthorization());
                    log.info("获取工作步骤数据"+map);

                    if(map != null){
                        List<Map> worksteps = (List) map.get("worksteps");
                        if(worksteps != null && worksteps.size()>0){
                            for(Map workstep : worksteps){
                                JobWorkstepNew jobWorkstep = new JobWorkstepNew();
                                jobWorkstep.setId(workstep.get("id") == null?null:workstep.get("id").toString());
                                jobWorkstep.setName(workstep.get("name") == null?null:workstep.get("name").toString());
                                Map job = (Map)workstep.get("job");
                                if(job != null){
                                    jobWorkstep.setJobId(job.get("id") == null?null:job.get("id").toString());
                                    jobWorkstep.setJobName(job.get("name") == null?null:job.get("name").toString());
                                }
                                jobWorkstep.setStatus(workstep.get("status") == null?null:workstep.get("status").toString());
                                jobWorkstep.setAmountPlanned(workstep.get("amountPlanned") == null?null:workstep.get("amountPlanned").toString());
                                jobWorkstep.setWastePlanned(workstep.get("wastePlanned") == null?null:workstep.get("wastePlanned").toString());
                                jobWorkstep.setAmountProduced(workstep.get("amountProduced") == null?null:workstep.get("amountProduced").toString());
                                jobWorkstep.setWasteProduced(workstep.get("wasteProduced") == null?null:workstep.get("wasteProduced").toString());
                                jobWorkstep.setDeviceId(workstep.get("deviceId") == null?null:workstep.get("deviceId").toString());
                                jobWorkstep.setSequenceType(workstep.get("sequenceType") == null?null:workstep.get("sequenceType").toString());
                                jobWorkstep.setStart(workstep.get("start") == null?null:workstep.get("start").toString());
                                jobWorkstep.setEnd(workstep.get("end") == null?null:workstep.get("end").toString());
                                jobWorkstep.setStartPlanned(workstep.get("startPlanned") == null?null:workstep.get("startPlanned").toString());
                                jobWorkstep.setEndPlanned(workstep.get("endPlanned") == null?null:workstep.get("endPlanned").toString());
                                jobWorkstep.setSetuptimePlanned(workstep.get("setuptimePlanned") == null?null:workstep.get("setuptimePlanned").toString());
                                jobWorkstep.setProdtimePlanned(workstep.get("prodtimePlanned") == null?null:workstep.get("prodtimePlanned").toString());

                                //处理插入时间
                                jobWorkstep.setInsertTime(new Date().getTime());
                                jobWorkstep.setInsertDate(DateUtils.theDayStart(new Date()));
                                if(workstep.get("start") != null){
                                    jobWorkstep.setStartTime(DateUtils.timeZoneToDate(workstep.get("start").toString()));
                                }

                                if(workstep.get("end") != null){
                                    jobWorkstep.setEndTime(DateUtils.timeZoneToDate(workstep.get("end").toString()));
                                }
                                jobWorkstep.setCreateTime(new Date());
                                jobWorkstep.setInsertDateMonth(Integer.valueOf(DateUtils.currentYearMonth()));

                                int i = jobWorkstepNewMapper.isExists(jobWorkstep.getId());
                                if(i==0){
                                    jobWorkstepNewMapper.insert(jobWorkstep);
                                    log.info("work_step插入成功："+jobId);
                                }else if(i>0){
                                    jobWorkstepNewMapper.updateById(jobWorkstep);
                                    log.info("work_step更新成功："+jobId);
                                }

                                //处理syncStatusJob
                                jobSyncRecordNewMapper.upsateSyncStatusWorkstep(jobId, "Y");
                                log.info("job_sync_record表中workStep完成更新，jobId："+jobId);

                                //types
                                if(workstep.get("types") != null){
                                    types((List)workstep.get("types"), jobWorkstep.getId());
                                }

                                //sequences
                                if(workstep.get("actualTimes") != null){
                                    actualTimes((List)workstep.get("actualTimes"), jobWorkstep.getId());
                                }



                            }
                        }

                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("拉取worksteps数据异常", e);
        }finally {
            //LockConfig.setLockStatus(0);
        }
    }

    @Override
    public Map getPullWorkstepByJobId(String jobId) {
        Map result = new HashMap();

        List<Map> workstepStrList = jobWorkstepNewMapper.getPullWorkstepByJobId(jobId);

        if(workstepStrList != null && workstepStrList.size() > 0){
            List resultList = new ArrayList();
            for(Map workstepMap : workstepStrList){
                Map map = new HashMap();
                map.put("jobId",workstepMap.get("job_id"));
                map.put("status",workstepMap.get("status"));
                map.put("amountPlanned",workstepMap.get("amount_planned"));
                map.put("wastePlanned",workstepMap.get("waste_planned"));
                map.put("amountProduced",workstepMap.get("amount_produced"));
                map.put("wasteProduced",workstepMap.get("waste_produced"));
                map.put("deviceId",workstepMap.get("device_id"));
                map.put("start",workstepMap.get("start"));
                map.put("end",workstepMap.get("end"));
                map.put("createTime",workstepMap.get("createTime"));

                resultList.add(map);
            }
            result.put("worksteps", resultList);
        }

        return result;
    }


    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "types", businessType = BusinessType.INSERT)
    public void types(List<String> types, String workstepId) throws Exception {
        if (types != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //先清空此workstepId关联的旧数据
            jobWorkstepTypeNewMapper.deleteTypeByWorkstepId(workstepId);

            for(String type : types){
                JobWorkstepTypeNew jobWorkstepType = new JobWorkstepTypeNew();
                jobWorkstepType.setId(AssignUtils.getUUid());
                jobWorkstepType.setWorkstepId(workstepId);
                jobWorkstepType.setType(type);
                jobWorkstepType.setInsertDateMonth(insertDateMonth);
                jobWorkstepTypeNewMapper.insert(jobWorkstepType);
            }

        }
    }




    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "actualTimes", businessType = BusinessType.INSERT)
    public void actualTimes(List<Map> jobActualTimes, String workstepId) throws Exception {
        if (jobActualTimes != null && jobActualTimes.size() > 0) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //先清空此workstepId关联的旧数据
            actualtimesNewMapper.deleteActualtimeByWorkstepId(workstepId);

            //actualTimes
            for(Map actualTimesMap : jobActualTimes){
                JobWorkstepActualtimesNew actualtimes = new JobWorkstepActualtimesNew();
                actualtimes.setId(AssignUtils.getUUid());
                actualtimes.setWorkstepId(workstepId);
                actualtimes.setTimeTypeGroupName(actualTimesMap.get("timeTypeGroupName") == null?null:actualTimesMap.get("timeTypeGroupName").toString());
                actualtimes.setTimeTypeName(actualTimesMap.get("timeTypeName") == null?null:actualTimesMap.get("timeTypeName").toString());
                actualtimes.setDuration(actualTimesMap.get("duration") == null?null:actualTimesMap.get("duration").toString());
                actualtimes.setInsertDateMonth(insertDateMonth);
                actualtimesNewMapper.insert(actualtimes);
            }

        }
    }
}
