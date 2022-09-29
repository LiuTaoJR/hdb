package com.xq.hdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xq.hdb.entity.JobSyncRecord;
import com.xq.hdb.entity.decrypt.JobSyncRecordNew;
import com.xq.hdb.mapper.db1.JobSyncRecordMapper;
import com.xq.hdb.mapper.db3.JobSyncRecordNewMapper;
import com.xq.hdb.service.JobService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@Slf4j
class HdbApplicationTests {


    @Test
    void contextLoads() {
//        String miwen = AssignUtils.encrypt("杨文田双层带盖外套8151");
//        System.out.println(miwen);
        String jiemi = AssignUtils.decryptionToStr("7D0E22986DAE80FBBE049610C088B07241CD80FDC98C443062A11CBC4947F712");
        String str = toUtf8String(jiemi);
        System.out.println(str);
    }

    public  String toUtf8String(String string) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c <= 255) {
                stringBuffer.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes(StandardCharsets.UTF_8);
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (int value : b) {
                    int k = value;
                    if (k < 0) k += 256;
                    stringBuffer.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return stringBuffer.toString();
    }

    @Test
    public void aa() {
//       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Long l =new Long("1662108764104");
//        System.out.println(simpleDateFormat.format(l));
//        String time = AssignUtils.decryptionToStr("2D5DDEC14692FFE89B94827CBD86A16CD85289C176D486ACC4DA6232A065D03D");
        System.out.println(dateString2formatString("2022-08-30T08:46:03.452+08:00"));
    }

    private String dateString2formatString(String s)
    {
        String str="";
        try
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            Date date=sd.parse(s);
            str=sdf.format(date);
        }
        catch(Exception e)
        {
            return str;
        }
        return str;
    }

    @Autowired
    private JobSyncRecordMapper jobSyncRecordMapper;

    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;

    @Test void bb() throws UnsupportedEncodingException {
        String a=this.toUtf8String("缇庡厠姝ｆ壙440x380x270");
        String b = URLDecoder.decode("%E7%BE%8E%E5%85%8B%E6%AD%A3%E6%89%BF440x380x270","utf-8");
        System.out.println(b);

    }




}
