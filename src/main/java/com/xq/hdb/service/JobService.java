package com.xq.hdb.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xq.hdb.entity.Job;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


public interface JobService extends IService<Job> {


    /**
     * job插库测试
     *
     * @param jsonStr
     * @return
     */
    Map jobInsert(@RequestBody String jsonStr);


    /**
     * 定时拉取活件信息
     */
    void pullJob();


    Map getJobByJobId(String jobId);


    //解密
    Object decryption(String str);


}
