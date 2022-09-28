package com.xq.hdb.controller;



import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.service.JobService;
import com.xq.hdb.service.JobSyncRecordService;
import com.xq.hdb.service.SqliteService;
import com.xq.hdb.utils.StringUtils;
import com.xq.hdb.utils.file.FileUtils;
import com.xq.hdb.utils.sqlite.SqliteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/ceshi")
public class CeController {


    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobService jobService;

    @Autowired
    private SqliteService sqliteService;

    @Autowired
    private JobSyncRecordService jobSyncRecordService;


    @PostMapping
    @ResponseBody
    public String ceshi(){
        try{
            jobSyncRecordService.jobSyncRecordTest();
        }catch (Exception e){
            e.printStackTrace();
            log.error("ceshi接口异常:{}",e.getMessage());
            return "error";
        }
        return "success";
    }



    @PostMapping("/multipleDataSources")
    @ResponseBody
    public String multipleDataSources(){
        try{

        }catch (Exception e){
            e.printStackTrace();
            log.error("multipleDataSources接口异常:{}",e.getMessage());
            return "error";
        }
        return "success";
    }




    @PostMapping("/copyFile")
    @ResponseBody
    public String showTable() {
        try{
            FileUtils.copySingleFile("D:/sqlite/db1/hdb_other.db","D:/sqlite/DBbackup/hdb202205.db");
        }catch (Exception e){
            log.error("showTable接口异常:{}",e.getMessage());
            e.printStackTrace();
            return "error";
        }
        return "success";
    }



    @PostMapping("/dbBackup")
    @ResponseBody
    public String dbBackup(){
        try{
            String backupResult = SqliteUtils.backupByMonth(hdbConstantConfig.getSqliteDBOtherUrl(), hdbConstantConfig.getBackupDBUrl());
            if(!backupResult.equals("200")){
                return backupResult;
            }
            //处理原始数据库
            List<String> tables = sqliteService.getAllTable();
            sqliteService.refreshData(tables);
        }catch (Exception e){
            e.printStackTrace();
            log.error("dbBackup接口异常:{}",e.getMessage());
            return "error";
        }
        return "success";
    }







    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/testPost")
    @ResponseBody
    public void RestTemplateTestPost() throws Exception {

        String url = "http://192.168.1.86:80/test/postTest";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", "application/json,application/xml,application/xhtml+xml,text/html,text/xml,text/plain");

        JSONObject requestMap = new JSONObject();
        requestMap.put("action", "delete");
        requestMap.put("url", "http://targethost:8888");
        requestMap.put("type", "ColorMeasurement");

        HttpEntity<JSONObject> entity = new HttpEntity<>(requestMap, headers);
        ObjectMapper objectMapper = new ObjectMapper();

        String similarJSON = objectMapper.writeValueAsString(requestMap);

        //使用JSONObject，不需要创建实体类VO来接受返参，缺点是别人不知道里面有哪些字段,即不知道有那些key
        JSONObject body1 = restTemplate.postForObject(url, entity, JSONObject.class);


        //ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, requestMap, JSONObject.class);
        //JSONObject body2 = responseEntity.getBody(); //响应体
        //HttpStatus statusCode = responseEntity.getStatusCode(); //状态码
        //HttpHeaders headers1 = responseEntity.getHeaders();//获取到头信息


    }





}
