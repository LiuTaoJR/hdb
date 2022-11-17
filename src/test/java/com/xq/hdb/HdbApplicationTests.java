package com.xq.hdb;

import com.xq.hdb.entity.decrypt.JobIdTime;
import com.xq.hdb.mapper.db3.JobIdTimeMapper;
import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@Slf4j
class HdbApplicationTests {
    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;

    @Autowired
    private JobIdTimeMapper jobIdTimeMapper;

    @Test
    public void aa()  {
        JobIdTime jobIdTime = new JobIdTime();
        jobIdTime.setJobTime("123MGSUK-01DJ(蓝紫)-外箱-XS无批号");
        jobIdTime.setJobId("23123123123");
        jobIdTime.setTime("sasdasda");
        jobIdTime.setCreateTime(new Date());
        jobIdTimeMapper.insert(jobIdTime);

    }


}
