package com.xq.hdb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.hdb.entity.JobSyncRecord;


public interface JobSyncRecordService extends IService<JobSyncRecord> {


    void jobSyncRecordTest();


    void updateJobId(String jobId);

    void updateJobIdNew(String jobId, String time);


}
