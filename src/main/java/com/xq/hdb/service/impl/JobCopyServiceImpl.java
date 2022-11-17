package com.xq.hdb.service.impl;

import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.config.Log;
import com.xq.hdb.entity.decrypt.*;
import com.xq.hdb.enums.BusinessType;
import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import com.xq.hdb.mapper.db4.*;
import com.xq.hdb.service.JobCopyService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JobCopyServiceImpl implements JobCopyService {

    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobNewMapper jobNewMapper;

    @Autowired
    private JobStatusNewMapper jobStatusNewMapper;

    @Autowired
    private JobStatusMilestonesNewMapper milestonesNewMapper;

    @Autowired
    private JobGangJobNewMapper jobGangJobNewMapper;

    @Autowired
    private JobSequencesNewMapper jobSequencesNewMapper;

    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "pullJob", businessType = BusinessType.INSERT)
    public synchronized void pullJobCopy() {
        try {
            List<String> jobIds = jobSyncRecordNewMapper.getjobIdBySyncStatusJob("N");
            if (jobIds != null && jobIds.size() > 0) {
                for (String jobId : jobIds) {
                    //请求获取job
                    String jId = URLEncoder.encode(jobId, "UTF-8");
                    String jobUrl = hdbConstantConfig.getDomainName() + "/PrinectAPILocal/rest/job/" + jId;
                    String jobParam = null;
                    Map<String, Object> mapResult = HttpUtils.sendGetReturnMap(jobUrl, jobParam, hdbConstantConfig.getAuthorization());
                    log.info("请求获取job数据：" + mapResult);

                    if (mapResult != null) {
                        Map jobMap = (Map) mapResult.get("job");
                        if (jobMap != null) {
                            JobNew job = new JobNew();
                            job.setId(jobMap.get("id") == null ? null : jobMap.get("id").toString());
                            job.setName(jobMap.get("name") == null ? null : jobMap.get("name").toString());
                            job.setCustomerId(jobMap.get("customerId") == null ? null : jobMap.get("customerId").toString());
                            job.setCustomerName(jobMap.get("customerName") == null ? null : jobMap.get("customerName").toString());
                            job.setCustomerOrderId(jobMap.get("customerOrderId") == null ? null : jobMap.get("customerOrderId").toString());
                            job.setDueDate(jobMap.get("dueDate") == null ? null : jobMap.get("dueDate").toString());
                            job.setDeliveryAmount(jobMap.get("deliveryAmount") == null ? null : jobMap.get("deliveryAmount").toString());
                            job.setCreationDate(jobMap.get("creationDate") == null ? null : jobMap.get("creationDate").toString());
                            job.setLastModified(jobMap.get("lastModified") == null ? null : jobMap.get("lastModified").toString());
                            job.setAuthor(jobMap.get("author") == null ? null : jobMap.get("author").toString());
                            job.setPriority(jobMap.get("priority") == null ? null : jobMap.get("priority").toString());
                            job.setNumberPlannedPages(jobMap.get("numberPlannedPages") == null ? null : jobMap.get("numberPlannedPages").toString());
                            job.setDescription(jobMap.get("description") == null ? null : jobMap.get("description").toString());
                            job.setShopId(jobMap.get("shopId") == null ? null : jobMap.get("shopId").toString());
                            job.setSourceJobIds(jobMap.get("shopId") == null ? null : jobMap.get("shopId").toString());

                            int i = jobNewMapper.isExists(job.getId());
                            if (i == 0) {
                                jobNewMapper.insert(job);
                            } else if (i > 0) {
                                jobNewMapper.updateById(job);
                            }

                            //处理syncStatusJob
                            jobSyncRecordNewMapper.upsateSyncStatusJob(jobId, "Y");
                            log.info("job_sync_record表中job完成更新，jobId：" + jobId);

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

    @Log(title = "jobStatus", businessType = BusinessType.INSERT)
    public void jobStatus(Map jobStatus, String jobId) throws Exception {
        if (jobStatus != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //清空与jobId相关联的就数据
            jobStatusNewMapper.delectJobStatusByjobId(jobId);

            //jobStatus
            JobStatusNew status = new JobStatusNew();
            status.setId(AssignUtils.getUUid());
            status.setJobId(jobId);
            status.setGlobalStatus(jobStatus.get("globalStatus") == null ? null : jobStatus.get("globalStatus").toString());
            status.setInsertDateMonth(insertDateMonth);
            jobStatusNewMapper.insert(status);

            //milestones
            List<Map> milestonesList = (List) jobStatus.get("milestones");
            if (milestonesList != null && milestonesList.size() > 0) {
                for (Map milestonesMap : milestonesList) {
                    JobStatusMilestonesNew milestones = new JobStatusMilestonesNew();
                    milestones.setId(AssignUtils.getUUid());
                    milestones.setStatusId(status.getId());
                    milestones.setMilestoneDefid(milestonesMap.get("milestoneDefId") == null ? null : milestonesMap.get("milestoneDefId").toString());
                    milestones.setStatus(milestonesMap.get("status") == null ? null : milestonesMap.get("status").toString());
                    milestones.setCalculatedProgress(milestonesMap.get("calculatedProgress") == null ? null : milestonesMap.get("calculatedProgress").toString());
                    milestones.setComment(milestonesMap.get("comment") == null ? null : milestonesMap.get("comment").toString());
                    milestonesNewMapper.insert(milestones);
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
            jobSequencesNewMapper.delectJobSequencesByjobId(jobId);

            //sequences
            for (Map sequencesMap : jobSequences) {
                JobSequencesNew sequences = new JobSequencesNew();
                sequences.setId(AssignUtils.getUUid());
                sequences.setJobId(jobId);
                sequences.setName(sequencesMap.get("name") == null ? null : sequencesMap.get("name").toString());
                sequences.setType(sequencesMap.get("type") == null ? null : sequencesMap.get("type").toString());
                sequences.setInsertDateMonth(insertDateMonth);
                jobSequencesNewMapper.insert(sequences);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "gangJobs", businessType = BusinessType.INSERT)
    public void gangJobs(List<Map> gangJobs, String jobId) throws Exception {
        if (gangJobs != null) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());

            //清空与jobId相关联的就数据
            jobGangJobNewMapper.delectJobGangJobsByjobId(jobId);

            //sequences
            for (Map gangJobsMap : gangJobs) {
                JobGangJobNew JobGangJob = new JobGangJobNew();
                JobGangJob.setId(AssignUtils.getUUid());
                JobGangJob.setJobId(jobId);
                JobGangJob.setGangJobId(gangJobsMap.get("gangJobId") == null ? null : gangJobsMap.get("gangJobId").toString());
                JobGangJob.setSheetNameListStr(gangJobsMap.get("sheetName") == null ? null : gangJobsMap.get("sheetName").toString());
                JobGangJob.setInsertDateMonth(insertDateMonth);
                jobGangJobNewMapper.insert(JobGangJob);
            }

        }
    }
}
