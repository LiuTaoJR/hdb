package com.xq.hdb.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.config.LockConfig;
import com.xq.hdb.config.Log;
import com.xq.hdb.entity.*;
import com.xq.hdb.enums.BusinessType;
import com.xq.hdb.mapper.db1.JobSyncRecordMapper;
import com.xq.hdb.mapper.db2.*;
import com.xq.hdb.service.JobService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.URLEncoder;;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.catalina.manager.Constants.CHARSET;
import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;

@Slf4j
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {


    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private JobStatusMapper jobStatusMapper;

    @Autowired
    private JobStatusMilestonesMapper jobStatusMilestonesMapper;

    @Autowired
    private JobGangJobMapper jobGangJobMapper;

    @Autowired
    private JobSequencesMapper jobSequencesMapper;

    @Autowired
    private JobWorkstepMapper jobWorkstepMapper;

    @Autowired
    private JobSyncRecordMapper jobSyncRecordMapper;


    @Override
    public Map jobInsert(String jsonStr) {
        Map result = new HashMap();
        result.put("code", 200);

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapResult = gson.fromJson(jsonStr, map.getClass());

        try {

            if (mapResult != null) {
                Map jobMap = (Map) mapResult.get("job");
                if (jobMap != null) {
                    Job job = new Job();
                    job.setId(AssignUtils.encrypt(jobMap.get("id")));
                    job.setName(AssignUtils.encrypt(jobMap.get("name")));
                    job.setCustomerId(AssignUtils.encrypt(jobMap.get("customerId")));
                    job.setCustomerName(AssignUtils.encrypt(jobMap.get("customerName")));
                    job.setCustomerOrderId(AssignUtils.encrypt(jobMap.get("customerOrderId")));
                    job.setDueDate(AssignUtils.encrypt(jobMap.get("dueDate")));
                    job.setDeliveryAmount(AssignUtils.encrypt(jobMap.get("deliveryAmount")));
                    job.setCreationDate(AssignUtils.encrypt(jobMap.get("creationDate")));
                    job.setLastModified(AssignUtils.encrypt(jobMap.get("lastModified")));
                    job.setAuthor(AssignUtils.encrypt(jobMap.get("author")));
                    job.setPriority(AssignUtils.encrypt(jobMap.get("priority")));
                    job.setNumberPlannedPages(AssignUtils.encrypt(jobMap.get("numberPlannedPages")));
                    job.setDescription(AssignUtils.encrypt(jobMap.get("description")));
                    job.setShopId(AssignUtils.encrypt(jobMap.get("shopId")));
                    job.setSourceJobIds(AssignUtils.encrypt(jobMap.get("shopId")));

                    int i = jobMapper.isExists(job.getId());
                    if (i == 0) {
                        jobMapper.insert(job);
                    } else if (i > 0) {
                        jobMapper.updateById(job);
                    }

                    //jobStatus
                    if (jobMap.get("jobStatus") != null) {
                        jobStatus((Map) jobMap.get("jobStatus"), job.getId());
                    }

                    //sequences
                    if (jobMap.get("sequences") != null) {
                        jobSequences((List) jobMap.get("sequences"), job.getId());
                    }

                    //gangJobs
                    if (jobMap.get("gangJobs") != null) {
                        gangJobs((List) jobMap.get("gangJobs"), job.getId());
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("jobInsert方法异常:{}", e.getMessage());
            result.put("code", 500);
        }


        return result;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "pullJob", businessType = BusinessType.INSERT)
    public synchronized void pullJob() {

        //校验数据库是否被操作
        /*if(LockConfig.getLockStatus() == 1){
            return;
        }else if(LockConfig.getLockStatus() == 0){
            LockConfig.setLockStatus(1);
        }*/

        try {
            List<String> jobIds = jobSyncRecordMapper.getjobIdBySyncStatusJob("N");
            if (jobIds != null && jobIds.size() > 0) {
                for (String jobId : jobIds) {
                    jobId = AssignUtils.decryptionToStr(jobId);
                    //请求获取job
                    String jId = URLEncoder.encode(jobId, "UTF-8");
                    String jobUrl = hdbConstantConfig.getDomainName() + "/PrinectAPILocal/rest/job/" + jId;
                    String jobParam = null;
                    Map<String, Object> mapResult = HttpUtils.sendGetReturnMap(jobUrl, jobParam, hdbConstantConfig.getAuthorization());
                    log.info("请求获取job数据：" + mapResult);

                    if (mapResult != null) {
                        Map jobMap = (Map) mapResult.get("job");
                        if (jobMap != null) {
                            Job job = new Job();
                            job.setId(AssignUtils.encrypt(jobMap.get("id")));
                            job.setName(AssignUtils.encrypt(jobMap.get("name")));
                            job.setCustomerId(AssignUtils.encrypt(jobMap.get("customerId")));
                            job.setCustomerName(AssignUtils.encrypt(jobMap.get("customerName")));
                            job.setCustomerOrderId(AssignUtils.encrypt(jobMap.get("customerOrderId")));
                            job.setDueDate(AssignUtils.encrypt(jobMap.get("dueDate")));
                            job.setDeliveryAmount(AssignUtils.encrypt(jobMap.get("deliveryAmount")));
                            job.setCreationDate(AssignUtils.encrypt(jobMap.get("creationDate")));
                            job.setLastModified(AssignUtils.encrypt(jobMap.get("lastModified")));
                            job.setAuthor(AssignUtils.encrypt(jobMap.get("author")));
                            job.setPriority(AssignUtils.encrypt(jobMap.get("priority")));
                            job.setNumberPlannedPages(AssignUtils.encrypt(jobMap.get("numberPlannedPages")));
                            job.setDescription(AssignUtils.encrypt(jobMap.get("description")));
                            job.setShopId(AssignUtils.encrypt(jobMap.get("shopId")));
                            job.setSourceJobIds(AssignUtils.encrypt(jobMap.get("shopId")));

                            int i = jobMapper.isExists(job.getId());
                            if (i == 0) {
                                jobMapper.insert(job);
                            } else if (i > 0) {
                                jobMapper.updateById(job);
                            }

                            //处理syncStatusJob
                            jobSyncRecordMapper.upsateSyncStatusJob(jobId, "Y");

                            //jobStatus
                            if (jobMap.get("jobStatus") != null) {
                                jobStatus((Map) jobMap.get("jobStatus"), job.getId());
                            }

                            //sequences
                            if (jobMap.get("sequences") != null) {
                                jobSequences((List) jobMap.get("sequences"), job.getId());
                            }

                            //gangJobs
                            if (jobMap.get("gangJobs") != null) {
                                gangJobs((List) jobMap.get("gangJobs"), job.getId());
                            }

                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("拉取job数据异常", e);
        } finally {
            //LockConfig.setLockStatus(0);
        }

    }


    //@Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Log(title = "jobStatus", businessType = BusinessType.INSERT)
    public void jobStatus(Map jobStatus, String jobId) throws Exception {
        if (jobStatus != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //清空与jobId相关联的就数据
            jobStatusMapper.delectJobStatusByjobId(jobId);

            //jobStatus
            JobStatus status = new JobStatus();
            status.setId(AssignUtils.getUUid());
            status.setJobId(jobId);
            status.setGlobalStatus(AssignUtils.encrypt(jobStatus.get("globalStatus")));
            status.setInsertDateMonth(insertDateMonth);
            jobStatusMapper.insert(status);

            //milestones
            List<Map> milestonesList = (List) jobStatus.get("milestones");
            if (milestonesList != null && milestonesList.size() > 0) {
                for (Map milestonesMap : milestonesList) {
                    JobStatusMilestones milestones = new JobStatusMilestones();
                    milestones.setId(AssignUtils.getUUid());
                    milestones.setStatusId(status.getId());
                    milestones.setMilestoneDefid(AssignUtils.encrypt(milestonesMap.get("milestoneDefId")));
                    milestones.setStatus(AssignUtils.encrypt(milestonesMap.get("status")));
                    milestones.setCalculatedProgress(AssignUtils.encrypt(milestonesMap.get("calculatedProgress")));
                    milestones.setComment(AssignUtils.encrypt(milestonesMap.get("comment")));
                    jobStatusMilestonesMapper.insert(milestones);
                }

            }
        }

    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "jobSequences", businessType = BusinessType.INSERT)
    public void jobSequences(List<Map> jobSequences, String jobId) throws Exception {
        if (jobSequences != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //清空与jobId相关联的就数据
            jobSequencesMapper.delectJobSequencesByjobId(jobId);

            //sequences
            for (Map sequencesMap : jobSequences) {
                JobSequences sequences = new JobSequences();
                sequences.setId(AssignUtils.getUUid());
                sequences.setJobId(jobId);
                sequences.setName(AssignUtils.encrypt(sequencesMap.get("name")));
                sequences.setType(AssignUtils.encrypt(sequencesMap.get("type")));
                sequences.setInsertDateMonth(insertDateMonth);
                jobSequencesMapper.insert(sequences);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "gangJobs", businessType = BusinessType.INSERT)
    public void gangJobs(List<Map> gangJobs, String jobId) throws Exception {
        if (gangJobs != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());

            //清空与jobId相关联的就数据
            jobGangJobMapper.delectJobGangJobsByjobId(jobId);

            //sequences
            for (Map gangJobsMap : gangJobs) {
                JobGangJob JobGangJob = new JobGangJob();
                JobGangJob.setId(AssignUtils.getUUid());
                JobGangJob.setJobId(jobId);
                JobGangJob.setGangJobId(AssignUtils.encrypt(gangJobsMap.get("gangJobId")));
                JobGangJob.setSheetNameListStr(AssignUtils.encrypt(gangJobsMap.get("sheetName")));
                JobGangJob.setInsertDateMonth(insertDateMonth);
                jobGangJobMapper.insert(JobGangJob);
            }

        }
    }


    @Override
    public Map getJobByJobId(String jobId) {
        return null;
    }

    @Override
    public Object decryption(String str) {
        try {
            String a = AssignUtils.decryptionToStr(str);
            System.out.println("--------------" + a + "------------");
            return a;
        } catch (Exception e) {
            return null;
        }

    }


}
