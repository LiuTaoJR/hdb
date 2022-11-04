package com.xq.hdb.service.impl;


import com.xq.hdb.mapper.db3.JobIdTimeMapper;
import com.xq.hdb.service.JobIdTimeService;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JobIdTimeServiceImpl implements JobIdTimeService {

    @Autowired
    private JobIdTimeMapper jobIdTimeMapper;

    @Override
    public List<Map> getJobIdTime(Date date) {
        return jobIdTimeMapper.getJobIdTime(DateUtils.getDayStart(date), DateUtils.getDayEnd(date));
    }
}
