package com.xq.hdb.scheduledtask;

import com.google.gson.Gson;
import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.config.LockConfig;
import com.xq.hdb.entity.Job;
import com.xq.hdb.service.JobService;
import com.xq.hdb.service.JobWorkstepService;
import com.xq.hdb.utils.http.HttpUtils;
import com.xq.hdb.utils.sqlite.SqliteUtils;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
@EnableAsync
@Component
@Slf4j
public class SchedulerTask {


    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobWorkstepService jobWorkstepService;

    @Autowired
    private JobService jobService;





    @Async
    @Scheduled(cron = "0 1 0 15 * ?")
    public void backupByMonth(){
        try{
            //SqliteUtils.backupByMonth(hdbConstantConfig.getSqliteDBOtherUrl(), hdbConstantConfig.getBackupDBUrl());
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public synchronized void pullJob() throws InterruptedException {
        //System.out.println("这是Job");
        log.info("SchedulerTask pullJob start：");
        jobService.pullJob();



    }




    @Transactional
    @Scheduled(cron = "0 0/10 * * * ?")
    public synchronized void pullWorkstep(){

        System.out.println("这是Workstep");
        jobWorkstepService.pullWorkstep();

    }



}
