package com.xq.hdb.scheduledtask;

import com.xq.hdb.service.JobCopyService;
import com.xq.hdb.service.JobWorkstepCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@EnableAsync
@Component
@Slf4j
public class SchedulerTask {

    @Autowired
    private JobWorkstepCopyService jobWorkstepCopyService;

    @Autowired
    private JobCopyService jobCopyService;


    @Async
    @Scheduled(cron = "0 1 0 15 * ?")
    public void backupByMonth() {
        try {
            //SqliteUtils.backupByMonth(hdbConstantConfig.getSqliteDBOtherUrl(), hdbConstantConfig.getBackupDBUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public synchronized void pullJob() throws InterruptedException {
        log.info("SchedulerTask pullJob start：");
        jobCopyService.pullJobCopy();
    }


    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public synchronized void pullWorkstep() {
        log.info("SchedulerTask pullWorkstep start：");
        jobWorkstepCopyService.pullWorkstepCopy();

    }


}
