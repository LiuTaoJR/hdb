package com.xq.hdb.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.hdb.entity.JobSyncRecord;
import com.xq.hdb.entity.decrypt.JobSyncRecordNew;
import com.xq.hdb.mapper.db1.JobSyncRecordMapper;
import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import com.xq.hdb.service.JobSyncRecordService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class jobSyncRecordServiceImpl extends ServiceImpl<JobSyncRecordMapper, JobSyncRecord> implements JobSyncRecordService {


    @Autowired
    private JobSyncRecordMapper jobSyncRecordMapper;

    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;

    @Override
    public void jobSyncRecordTest() {
        JobSyncRecord jobSyncRecord = new JobSyncRecord();
        jobSyncRecord.setId(AssignUtils.getUUid());
        jobSyncRecord.setCreateTime(new Date());
        jobSyncRecord.setUpdateTime(new Date());
        jobSyncRecord.setInsertDateMonth(DateUtils.currentMonth());
        try{
            jobSyncRecordMapper.insert(jobSyncRecord);
        }catch (Exception e){
            e.printStackTrace();
            log.error("jobSyncRecordTest方法异常:{}",e.getMessage());
        }
    }



    @Override
    public void updateJobId(String jobId) {

        try{
            //查看jobId是否已在记录表存在
            //加密库的job_sync_record
            int i = jobSyncRecordMapper.isExists(jobId);
            String id=AssignUtils.getUUid();
            if(i < 1){
                JobSyncRecord jobSyncRecord = new JobSyncRecord();
                jobSyncRecord.setId(id);
                jobSyncRecord.setJobId(jobId);
                jobSyncRecord.setSyncStatusJob("N");
                jobSyncRecord.setSyncStatusWorkstep("N");
                jobSyncRecord.setCreateTime(new Date());
                jobSyncRecord.setUpdateTime(new Date());
                jobSyncRecord.setInsertDateMonth(DateUtils.currentMonth());
                jobSyncRecordMapper.insert(jobSyncRecord);
            }

            //未加密库的job_sync_record
            int j =jobSyncRecordNewMapper.isExists(AssignUtils.decryptionToStr(jobId));
            if(j < 1){
                JobSyncRecordNew recordNew = new JobSyncRecordNew();
                recordNew.setId(id);
                recordNew.setJobId(AssignUtils.decryptionToStr(jobId));
                recordNew.setSyncStatusJob("N");
                recordNew.setSyncStatusWorkstep("N");
                recordNew.setCreateTime(new Date());
                recordNew.setUpdateTime(new Date());
                recordNew.setInsertDateMonth(DateUtils.currentMonth());
                jobSyncRecordNewMapper.insert(recordNew);

                log.info("jobSyncRecordServiceImpl updateJobId end"+recordNew);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateJobId方法异常:{}",e.getMessage());
        }

    }


}
