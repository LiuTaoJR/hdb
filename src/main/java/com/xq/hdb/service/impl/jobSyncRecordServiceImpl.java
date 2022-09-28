package com.xq.hdb.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.hdb.entity.JobSyncRecord;
import com.xq.hdb.mapper.db1.JobSyncRecordMapper;
import com.xq.hdb.service.JobSyncRecordService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class jobSyncRecordServiceImpl extends ServiceImpl<JobSyncRecordMapper, JobSyncRecord> implements JobSyncRecordService {


    @Autowired
    private JobSyncRecordMapper jobSyncRecordMapper;

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
            int i = jobSyncRecordMapper.isExists(jobId);
            if(i < 1){
                JobSyncRecord jobSyncRecord = new JobSyncRecord();
                jobSyncRecord.setId(AssignUtils.getUUid());
                jobSyncRecord.setJobId(jobId);
                jobSyncRecord.setSyncStatusJob("N");
                jobSyncRecord.setSyncStatusWorkstep("N");
                jobSyncRecord.setCreateTime(new Date());
                jobSyncRecord.setUpdateTime(new Date());
                jobSyncRecord.setInsertDateMonth(DateUtils.currentMonth());
                jobSyncRecordMapper.insert(jobSyncRecord);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("updateJobId方法异常:{}",e.getMessage());
        }

    }



}
