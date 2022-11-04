package com.xq.hdb.service;

import java.util.Map;

public interface JobWorkstepCopyService {

    /**
     * 定时拉取工作步骤信息
     */
    void pullWorkstepCopy();

    Map getPullWorkstepByJobId(String jobId);
}
