package com.xq.hdb.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.config.LockConfig;
import com.xq.hdb.config.Log;
import com.xq.hdb.entity.*;
import com.xq.hdb.enums.BusinessType;
import com.xq.hdb.mapper.db1.JobSyncRecordMapper;
import com.xq.hdb.mapper.db2.*;
import com.xq.hdb.service.JobWorkstepService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.http.HttpUtils;
import com.xq.hdb.vo.WorkstepActualTimesVO;
import com.xq.hdb.vo.WorkstepVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.*;
@Slf4j
@Service
public class JobWorkstepServiceImpl extends ServiceImpl<JobWorkstepMapper, JobWorkstep> implements JobWorkstepService {


    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobWorkstepMapper jobWorkstepMapper;

    @Autowired
    private JobWorkstepActualtimesMapper jobWorkstepActualtimesMapper;

    @Autowired
    private JobWorkstepTypeMapper jobWorkstepTypeMapper;

    @Autowired
    private JobSyncRecordMapper jobSyncRecordMapper;







    @Override
    public Map workstepInsert(String jsonStr) {
        Map result = new HashMap();
        result.put("code",200);
        System.out.println(jsonStr);

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapResult = gson.fromJson(jsonStr, map.getClass());

        List<Map> worksteps = (List) mapResult.get("worksteps");
        try{

            if(worksteps != null && worksteps.size()>0){
                for(Map workstep : worksteps){
                    JobWorkstep jobWorkstep = new JobWorkstep();
                    jobWorkstep.setId(AssignUtils.encrypt(workstep.get("id")));
                    jobWorkstep.setName(AssignUtils.encrypt(workstep.get("name")));
                    Map job = (Map)workstep.get("job");
                    if(job != null){
                        jobWorkstep.setJobId(AssignUtils.encrypt(job.get("id")));
                        jobWorkstep.setJobName(AssignUtils.encrypt(job.get("name")));
                    }
                    jobWorkstep.setStatus(AssignUtils.encrypt(workstep.get("status")));
                    jobWorkstep.setAmountPlanned(AssignUtils.encrypt(workstep.get("amountPlanned")));
                    jobWorkstep.setWastePlanned(AssignUtils.encrypt(workstep.get("wastePlanned")));
                    jobWorkstep.setAmountProduced(AssignUtils.encrypt(workstep.get("amountProduced")));
                    jobWorkstep.setWasteProduced(AssignUtils.encrypt(workstep.get("wasteProduced")));
                    jobWorkstep.setDeviceId(AssignUtils.encrypt(workstep.get("deviceId")));
                    jobWorkstep.setSequenceType(AssignUtils.encrypt(workstep.get("sequenceType")));

                    jobWorkstep.setStart(AssignUtils.encrypt(workstep.get("start")));
                    jobWorkstep.setEnd(AssignUtils.encrypt(workstep.get("end")));

                    jobWorkstep.setStartPlanned(AssignUtils.encrypt(workstep.get("startPlanned")));
                    jobWorkstep.setEndPlanned(AssignUtils.encrypt(workstep.get("endPlanned")));
                    jobWorkstep.setSetuptimePlanned(AssignUtils.encrypt(workstep.get("setuptimePlanned")));
                    jobWorkstep.setProdtimePlanned(AssignUtils.encrypt(workstep.get("prodtimePlanned")));

                    //处理插入时间
                    jobWorkstep.setInsertTime(new Date().getTime());
                    jobWorkstep.setInsertDate(DateUtils.theDayStart(new Date()));
                    /*if(workstep.get("start") != null){
                        jobWorkstep.setStartTime(DateUtils.timeZoneToDate(workstep.get("start").toString()));
                    }

                    if(workstep.get("end") != null){
                        jobWorkstep.setEndTime(DateUtils.timeZoneToDate(workstep.get("end").toString()));
                    }*/


                    jobWorkstep.setCreateTime(new Date());
                    jobWorkstep.setInsertDateMonth(Integer.valueOf(DateUtils.currentYearMonth()));

                    int i = jobWorkstepMapper.isExists(AssignUtils.encrypt(workstep.get("id")));
                    if(i==0){
                        jobWorkstepMapper.insert(jobWorkstep);
                    }else if(i>0){
                        jobWorkstepMapper.updateById(jobWorkstep);
                    }

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

        }catch (Exception e){
            e.printStackTrace();
            log.error("workstepInsert方法异常:{}",e.getMessage());
            result.put("code",500);
        }

        return result;
    }







    /**
     * 获取工作步骤
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "pullWorkstep", businessType = BusinessType.INSERT)
    public synchronized void pullWorkstep(){
        //校验数据库是否被操作
        /*if(LockConfig.getLockStatus() == 1){
            return;
        }else if(LockConfig.getLockStatus() == 0){
            LockConfig.setLockStatus(1);
        }*/
        try{
            //获取jobId
            List<String> jobIdList = jobSyncRecordMapper.getjobIdBySyncStatusWorkstep("N");
            if(jobIdList != null && jobIdList.size()>0){
                for(String jobId : jobIdList){
                    jobId = AssignUtils.decryptionToStr(jobId);
                    String x = URLEncoder.encode(jobId, "UTF-8");
                    String url = hdbConstantConfig.getDomainName() +"/PrinectAPILocal/rest/job/"+x+"/workstep";
                    String  param = null;
                    Map<String, Object> map = HttpUtils.sendGetReturnMap(url,param,hdbConstantConfig.getAuthorization());
                    if(map != null){
                        List<Map> worksteps = (List) map.get("worksteps");
                        if(worksteps != null && worksteps.size()>0){
                            for(Map workstep : worksteps){
                                JobWorkstep jobWorkstep = new JobWorkstep();
                                jobWorkstep.setId(AssignUtils.encrypt(workstep.get("id")));
                                jobWorkstep.setName(AssignUtils.encrypt(workstep.get("name")));
                                Map job = (Map)workstep.get("job");
                                if(job != null){
                                    jobWorkstep.setJobId(AssignUtils.encrypt(job.get("id")));
                                    jobWorkstep.setJobName(AssignUtils.encrypt(job.get("name")));
                                }
                                jobWorkstep.setStatus(AssignUtils.encrypt(workstep.get("status")));
                                jobWorkstep.setAmountPlanned(AssignUtils.encrypt(workstep.get("amountPlanned")));
                                jobWorkstep.setWastePlanned(AssignUtils.encrypt(workstep.get("wastePlanned")));
                                jobWorkstep.setAmountProduced(AssignUtils.encrypt(workstep.get("amountProduced")));
                                jobWorkstep.setWasteProduced(AssignUtils.encrypt(workstep.get("wasteProduced")));
                                jobWorkstep.setDeviceId(AssignUtils.encrypt(workstep.get("deviceId")));
                                jobWorkstep.setSequenceType(AssignUtils.encrypt(workstep.get("sequenceType")));
                                jobWorkstep.setStart(AssignUtils.encrypt(workstep.get("start")));
                                jobWorkstep.setEnd(AssignUtils.encrypt(workstep.get("end")));
                                jobWorkstep.setStartPlanned(AssignUtils.encrypt(workstep.get("startPlanned")));
                                jobWorkstep.setEndPlanned(AssignUtils.encrypt(workstep.get("endPlanned")));
                                jobWorkstep.setSetuptimePlanned(AssignUtils.encrypt(workstep.get("setuptimePlanned")));
                                jobWorkstep.setProdtimePlanned(AssignUtils.encrypt(workstep.get("prodtimePlanned")));

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

                                int i = jobWorkstepMapper.isExists(AssignUtils.encrypt(workstep.get("id")));
                                if(i==0){
                                    jobWorkstepMapper.insert(jobWorkstep);
                                }else if(i>0){
                                    jobWorkstepMapper.updateById(jobWorkstep);
                                }

                                //处理syncStatusJob
                                jobSyncRecordMapper.upsateSyncStatusWorkstep(jobId, "Y");

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



    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "types", businessType = BusinessType.INSERT)
    public void types(List<String> types, String workstepId) throws Exception {
        if (types != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //先清空此workstepId关联的旧数据
            jobWorkstepTypeMapper.deleteTypeByWorkstepId(workstepId);

            for(String type : types){
                JobWorkstepType jobWorkstepType = new JobWorkstepType();
                jobWorkstepType.setId(AssignUtils.getUUid());
                jobWorkstepType.setWorkstepId(workstepId);
                jobWorkstepType.setType(AssignUtils.encrypt(type));
                jobWorkstepType.setInsertDateMonth(insertDateMonth);
                jobWorkstepTypeMapper.insert(jobWorkstepType);
            }

        }
    }




    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "actualTimes", businessType = BusinessType.INSERT)
    public void actualTimes(List<Map> jobActualTimes, String workstepId) throws Exception {
        if (jobActualTimes != null && jobActualTimes.size() > 0) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //先清空此workstepId关联的旧数据
            jobWorkstepActualtimesMapper.deleteActualtimeByWorkstepId(workstepId);

            //actualTimes
            for(Map actualTimesMap : jobActualTimes){
                JobWorkstepActualtimes actualtimes = new JobWorkstepActualtimes();
                actualtimes.setId(AssignUtils.getUUid());
                actualtimes.setWorkstepId(workstepId);
                actualtimes.setTimeTypeGroupName(AssignUtils.encrypt(actualTimesMap.get("timeTypeGroupName")));
                actualtimes.setTimeTypeName(AssignUtils.encrypt(actualTimesMap.get("timeTypeName")));
                actualtimes.setDuration(AssignUtils.encrypt(actualTimesMap.get("duration")));
                actualtimes.setInsertDateMonth(insertDateMonth);
                jobWorkstepActualtimesMapper.insert(actualtimes);
            }

        }
    }





    @Override
    public List<WorkstepVO> postPullWorkstep(WorkstepVO workstepVO) {

        return null;
    }




    @Override
    public Map getPullWorkstep(String jobId, String status, String deviceId, Date start, Date end) {
        Map map = new HashMap();
        List<WorkstepVO> workstepVOList = new ArrayList<>();

        List<JobWorkstep> workstepList = jobWorkstepMapper.getWorkstepList(AssignUtils.paramEncrypt(jobId), AssignUtils.paramEncrypt(status),AssignUtils.paramEncrypt(deviceId),AssignUtils.paramEncrypt(start),AssignUtils.paramEncrypt(end));
        if(workstepList != null && workstepList.size() > 0){
            for(JobWorkstep workstepStr : workstepList){
                WorkstepVO workstepVO = workstepStrToWorkstepVO(workstepStr);

                //查询types
                List<String> types = jobWorkstepMapper.getTypesByWorkstepId(workstepStr.getId());
                if(types != null){
                    List typeList = new ArrayList();
                    for(String mStr : types){
                        typeList.add(AssignUtils.decryptionToStr(mStr));
                    }
                    workstepVO.setTypes(typeList);
                }

                //查询actualTimes
                List<JobWorkstepActualtimes> actualtimes = jobWorkstepActualtimesMapper.getActualTimesByWorkstepId(workstepStr.getId());
                if(actualtimes != null && actualtimes.size() > 0){
                    List actualTimeList = new ArrayList();
                    for(JobWorkstepActualtimes actualtimeStr : actualtimes){
                        WorkstepActualTimesVO actualTimesVO = actualtimeStrToActualTimesVO(actualtimeStr);
                        actualTimeList.add(actualTimesVO);
                    }
                    workstepVO.setActualTimes(actualTimeList);
                }

                workstepVOList.add(workstepVO);
            }
        }

        map.put("worksteps",workstepVOList);
        return map;
    }




    /**
     * 类型转换
     * @param workstepStr
     * @return
     */
    public WorkstepVO workstepStrToWorkstepVO(JobWorkstep workstepStr){
        WorkstepVO workstepVO = new WorkstepVO();

        workstepVO.setId(AssignUtils.decryptionToStr(workstepStr.getId()));
        workstepVO.setName(AssignUtils.decryptionToStr(workstepStr.getName()));
        Map job = new HashMap();
        job.put("id",AssignUtils.decryptionToStr(workstepStr.getJobId()));
        job.put("name",AssignUtils.decryptionToStr(workstepStr.getJobName()));
        workstepVO.setJob(job);
        workstepVO.setStatus(AssignUtils.decryptionToStr(workstepStr.getStatus()));
        workstepVO.setAmountPlanned(AssignUtils.decryptionToLong(workstepStr.getAmountPlanned()));
        workstepVO.setWastePlanned(AssignUtils.decryptionToLong(workstepStr.getWastePlanned()));
        workstepVO.setAmountProduced(AssignUtils.decryptionToLong(workstepStr.getAmountProduced()));
        workstepVO.setWasteProduced(AssignUtils.decryptionToLong(workstepStr.getWasteProduced()));
        workstepVO.setDeviceId(AssignUtils.decryptionToStr(workstepStr.getDeviceId()));
        workstepVO.setSequenceType(AssignUtils.decryptionToStr(workstepStr.getSequenceType()));
        workstepVO.setSetuptimePlanned(AssignUtils.decryptionToLong(workstepStr.getSetuptimePlanned()));
        workstepVO.setEndPlanned(AssignUtils.decryptionToDate(workstepStr.getEndPlanned()));
        workstepVO.setStart(AssignUtils.decryptionToDate(workstepStr.getStart()));
        workstepVO.setEnd(AssignUtils.decryptionToDate(workstepStr.getEnd()));
        workstepVO.setSetuptimePlanned(AssignUtils.decryptionToLong(workstepStr.getSetuptimePlanned()));
        workstepVO.setProdtimePlanned(AssignUtils.decryptionToLong(workstepStr.getProdtimePlanned()));

        return workstepVO;
    }


    public WorkstepActualTimesVO actualtimeStrToActualTimesVO(JobWorkstepActualtimes actualtimeStr){
        WorkstepActualTimesVO actualTimesVO = new WorkstepActualTimesVO();
        actualTimesVO.setTimeTypeName(AssignUtils.decryptionToStr(actualtimeStr.getTimeTypeName()));
        actualTimesVO.setTimeTypeGroupName(AssignUtils.decryptionToStr(actualtimeStr.getTimeTypeGroupName()));
        actualTimesVO.setDuration(AssignUtils.decryptionToLong(actualtimeStr.getDuration()));
        return actualTimesVO;
    }





    /**
     * 根据时间获取Workstep
     * @param date
     * @return
     */
    @Override
    public Map getPullWorkstepByDate(Date date) {
        Map result = new HashMap();

        List<Map> workstepStrList = jobWorkstepMapper.getPullWorkstepByDate(DateUtils.getDayStart(date), DateUtils.getDayEnd(date));
        if(workstepStrList != null && workstepStrList.size() > 0){
            List resultList = new ArrayList();
            for(Map workstepMap : workstepStrList){
                Map map = new HashMap();
                map.put("jobId",AssignUtils.decryptionToStr(workstepMap.get("job_id")));
                map.put("status",AssignUtils.decryptionToStr(workstepMap.get("status")));
                map.put("amountPlanned",AssignUtils.decryptionToLong(workstepMap.get("amount_planned").toString()));
                map.put("wastePlanned",AssignUtils.decryptionToLong(workstepMap.get("waste_planned").toString()));
                map.put("amountProduced",AssignUtils.decryptionToLong(workstepMap.get("amount_produced").toString()));
                map.put("wasteProduced",AssignUtils.decryptionToLong(workstepMap.get("waste_produced").toString()));
                map.put("deviceId",AssignUtils.decryptionToStr(workstepMap.get("device_id")));
                map.put("start",AssignUtils.decryptionToDate(workstepMap.get("start").toString()));
                map.put("end",AssignUtils.decryptionToDate(workstepMap.get("end").toString()));
                map.put("createTime",String.valueOf(workstepMap.get("createTime")));
                resultList.add(map);
            }
            result.put("worksteps", resultList);
        }

        return result;
    }




    /**
     * 根据时间获取Workstep
     * @param jobId
     * @return
     */
    @Override
    public Map getPullWorkstepByJobId(String jobId) {
        Map result = new HashMap();

        List<Map> workstepStrList = jobWorkstepMapper.getPullWorkstepByJobId(AssignUtils.paramEncrypt(jobId));

        if(workstepStrList != null && workstepStrList.size() > 0){
            List resultList = new ArrayList();
            for(Map workstepMap : workstepStrList){
                Map map = new HashMap();
                map.put("jobId",AssignUtils.decryptionToStr(workstepMap.get("job_id")));
                map.put("status",AssignUtils.decryptionToStr(workstepMap.get("status")));
                map.put("amountPlanned",AssignUtils.decryptionToLong(String.valueOf(workstepMap.get("amount_planned"))));
                map.put("wastePlanned",AssignUtils.decryptionToLong(String.valueOf(workstepMap.get("waste_planned"))));
                map.put("amountProduced",AssignUtils.decryptionToLong(String.valueOf(workstepMap.get("amount_produced"))));
                map.put("wasteProduced",AssignUtils.decryptionToLong(String.valueOf(workstepMap.get("waste_produced"))));
                map.put("deviceId",AssignUtils.decryptionToStr(workstepMap.get("device_id")));
                map.put("start",AssignUtils.decryptionToDate(String.valueOf(workstepMap.get("start"))));
                map.put("end",AssignUtils.decryptionToDate(String.valueOf(workstepMap.get("end"))));
                map.put("createTime",workstepMap.get("createTime"));

                resultList.add(map);
            }
            result.put("worksteps", resultList);
        }

        return result;
    }



}
