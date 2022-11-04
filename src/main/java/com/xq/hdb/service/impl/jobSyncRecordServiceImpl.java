package com.xq.hdb.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xq.hdb.entity.JobSyncRecord;
import com.xq.hdb.entity.decrypt.JobIdTime;
import com.xq.hdb.entity.decrypt.JobSyncRecordNew;
import com.xq.hdb.mapper.db1.JobSyncRecordMapper;
import com.xq.hdb.mapper.db3.JobIdTimeMapper;
import com.xq.hdb.mapper.db3.JobSignalStatusDevicePhaseNewMapper;
import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import com.xq.hdb.service.JobSyncRecordService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class jobSyncRecordServiceImpl extends ServiceImpl<JobSyncRecordMapper, JobSyncRecord> implements JobSyncRecordService {


    @Autowired
    private JobSyncRecordMapper jobSyncRecordMapper;

    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;

    @Autowired
    private JobIdTimeMapper jobIdTimeMapper;

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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateJobIdNew(String jobId,String time) {
        try {
            //先删除sync
            jobSyncRecordNewMapper.delByJobId(jobId);
            //再插入sync
            JobSyncRecordNew recordNew = new JobSyncRecordNew();
            recordNew.setId(AssignUtils.getUUid());
            recordNew.setJobId(jobId);
            recordNew.setSyncStatusJob("N");
            recordNew.setSyncStatusWorkstep("N");
            recordNew.setCreateTime(new Date());
            recordNew.setUpdateTime(new Date());
            recordNew.setInsertDateMonth(DateUtils.currentMonth());
            jobSyncRecordNewMapper.insert(recordNew);
            log.info("job_sync_record表插入成功"+recordNew);

            //先判断jobIdTime表是否存在
            List<Map> maps=jobIdTimeMapper.getOnly(jobId,time);
            if (maps.size()<1){
                JobIdTime jobIdTime=new JobIdTime();
                jobIdTime.setJobTime(jobId+time);
                jobIdTime.setJobId(jobId);
                jobIdTime.setTime(time);
                jobIdTimeMapper.insert(jobIdTime);
                log.info("job_id_time表插入成功"+jobIdTime);
            }

        }catch (Exception e){
            e.printStackTrace();
            log.error("updateJobIdNew方法异常:{}",e.getMessage());
        }
    }


}
