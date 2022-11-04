package com.xq.hdb;

import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;;
import java.util.*;

@SpringBootTest
@Slf4j
class HdbApplicationTests {
    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;

    @Test
    public void aa() throws ParseException, UnsupportedEncodingException {
//        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date a=  sd.parse("2022-10-10 10:44:54");
//        Date b=  sd.parse("2022-10-10 10:48:48");
//        int c= (int) ((b.getTime() - a.getTime()) / (1000 * 60));
//        System.out.println(c);
//        String time="2022-11-01";
//        int strMonth=Integer.valueOf(time.substring(8,10));
      List<String> list=new ArrayList<>();
      list.add("1");
        list.add("2");
        System.out.println(list);

    }




}
