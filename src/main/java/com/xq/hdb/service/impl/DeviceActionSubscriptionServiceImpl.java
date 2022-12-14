package com.xq.hdb.service.impl;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xq.hdb.config.HdbConstantConfig;
import com.xq.hdb.config.LockConfig;
import com.xq.hdb.config.Log;
import com.xq.hdb.entity.*;
import com.xq.hdb.entity.decrypt.*;
import com.xq.hdb.enums.BusinessType;
import com.xq.hdb.mapper.db1.*;
import com.xq.hdb.mapper.db3.*;
import com.xq.hdb.service.DeviceActionSubscriptionService;
import com.xq.hdb.service.JobSyncRecordService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.utils.DateUtils;
import com.xq.hdb.utils.StringUtils;
import com.xq.hdb.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
@Slf4j
public class DeviceActionSubscriptionServiceImpl implements DeviceActionSubscriptionService {


    @Autowired
    private HdbConstantConfig hdbConstantConfig;

    @Autowired
    private JobSignalStatusMapper jobSignalStatusMapper;

    @Autowired
    private JobSignalStatusDeviceMapper jobSignalStatusDeviceMapper;

    @Autowired
    private JobSignalStatusHeaderMapper jobSignalStatusHeaderMapper;

    @Autowired
    private JobSignalStatusDevicePhaseMapper jobSignalStatusDevicePhaseMapper;

    @Autowired
    private JobSignalStatusDevicePhaseMisDetailsMapper jobSignalStatusDevicePhaseMisDetailsMapper;

    @Autowired
    private JobSignalStatusDeviceEventMapper jobSignalStatusDeviceEventMapper;

    @Autowired
    private JobSignalStatusDevicePhaseActivityMapper jobSignalStatusDevicePhaseActivityMapper;

    @Autowired
    private JobSignalStatusDevicePhasePartMapper JobSignalStatusDevicePhasePartMapper;

    @Autowired
    private JobSignalResinfoResourcesetResourceAmountpoolMapper jobSignalResinfoResourcesetResourceAmountpoolMapper;

    @Autowired
    private JobSignalResinfoResourcesetResourceMiscconsumableMapper jobSignalResinfoResourcesetResourceMiscconsumableMapper;


    @Autowired
    private JobSignalResMapper jobSignalResMapper;

    @Autowired
    private JobSignalResinfoMapper jobSignalResinfoMapper;

    @Autowired
    private JobSignalResinfoCommentMapper jobSignalResinfoCommentMapper;

    @Autowired
    private JobSignalResinfoResourcesetMapper jobSignalResinfoResourcesetMapper;

    @Autowired
    private JobSignalResinfoResourcesetResourceMapper jobSignalResinfoResourcesetResourceMapper;


    @Autowired
    private JobSyncRecordService jobSyncRecordService;


    //new
    @Autowired
    private JobSignalStatusNewMapper jobSignalStatusNewMapper;

    @Autowired
    private JobSignalStatusHeaderNewMapper jobSignalStatusHeaderNewMapper;

    @Autowired
    private JobSignalStatusDevicePhaseNewMapper jobSignalStatusDevicePhaseNewMapper;

    @Autowired
    private JobSignalStatusDevicePhaseMisDetailsNewMapper jobSignalStatusDevicePhaseMisDetailsNewMapper;

    @Autowired
    private JobSignalStatusDevicePhaseActivityNewMapper jobSignalStatusDevicePhaseActivityNewMapper;

    @Autowired
    private JobSignalStatusDevicePhasePartNewMapper jobSignalStatusDevicePhasePartNewMapper;

    @Autowired
    private JobSignalStatusDeviceEventNewMapper jobSignalStatusDeviceEventNewMapper;

    @Autowired
    private JobSignalStatusDeviceNewMapper jobSignalStatusDeviceNewMapper;

    @Autowired
    private JobSyncRecordNewMapper jobSyncRecordNewMapper;


    /**
     * ??????????????????
     *
     * @param action
     * @param url
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> subscription(String action, String url, String type) {
        Map result = new HashMap();
        result.put("code", 500);

        String param = "";
        String msg = "";

        try {
            //????????????
            param = "action=" + action + "&" + "url=" + url + "&" + "type=" + type;

            //??????????????????
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("action", action);
            map.add("url", url);
            map.add("type", StringUtils.isEmpty(type) ? null : type);

            ResponseEntity<String> answerResult = sendPostFormData(hdbConstantConfig.getServerUrl(), map, hdbConstantConfig.getAuthorization());

            msg = "???????????? ???" + answerResult;
            result.put("code", 200);
            result.put("msg", msg);

        } catch (ResourceAccessException e) {
            e.printStackTrace();
            msg = "???????????? ResourceAccessException, url=" + hdbConstantConfig.getServerUrl() + ",   ?????????=" + param;
            log.error("???????????? ResourceAccessException, url=" + url + ",param=" + param, e);
            result.put("code", 500);
            result.put("msg", msg);
        } catch (ConnectException e) {
            e.printStackTrace();
            msg = "???????????? ConnectException, url=" + hdbConstantConfig.getServerUrl() + ",   ?????????=" + param;
            log.error("???????????? ConnectException, url=" + url + ",param=" + param, e);
            result.put("code", 500);
            result.put("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "???????????? Exception, url=" + hdbConstantConfig.getServerUrl() + ",   ?????????=" + param;
            log.error(msg, e);
            result.put("code", 500);
            result.put("msg", msg);
        }

        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public synchronized String dealSignal(String jsonStr) {

        //??????json?????????
        Gson gson = new Gson();
        Map<String, Map<String, List>> mapResult = gson.fromJson(jsonStr, new HashMap<String, Object>().getClass());
        try {
            //??????????????????
            List<Map> signalRessource = mapResult.get("XJMF").get("SignalRessource");
            if (signalRessource != null && signalRessource.size() > 0) {
                manageRessource(signalRessource);
            }

            List<Map> signalStatus = mapResult.get("XJMF").get("SignalStatus");
            if (signalStatus != null && signalStatus.size() > 0) {
                manageStatus(signalStatus);
            }

            List<Map> signalNotification = mapResult.get("XJMF").get("SignalNotification");
            if (signalNotification != null && signalNotification.size() > 0) {
                manageNotification(signalNotification);
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.error("????????????????????????", e);
            return "500";

        } finally {
            //LockConfig.setLockStatus(0);
        }


        return "200";
    }


    /**
     * signalRessource
     *
     * @param signalRessourceList
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "manageRessource", businessType = BusinessType.INSERT)
    public void manageRessource(List<Map> signalRessourceList) throws Exception {
        int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
        if (signalRessourceList != null && signalRessourceList.size() > 0) {
            for (Map signalRessource : signalRessourceList) {
                if (signalRessource != null) {
                    //??????
                    String id = AssignUtils.getUUid();
                    JobSignalRes jobSignalRes = new JobSignalRes();
                    jobSignalRes.setId(id);
                    jobSignalRes.setPersonalId(AssignUtils.encrypt(signalRessource.get("PersonalID")));
                    jobSignalRes.setTime(AssignUtils.encrypt(signalRessource.get("Time")));
                    jobSignalRes.setDeviceId(AssignUtils.encrypt(signalRessource.get("DeviceID")));
                    jobSignalRes.setInsertDateMonth(insertDateMonth);
                    jobSignalResMapper.insert(jobSignalRes);


                    //RessourceInfo
                    List<Map> ressourceInfoList = (ArrayList) signalRessource.get("RessourceInfo");
                    if (ressourceInfoList != null && ressourceInfoList.size() > 0) {
                        for (Map ressourceInfo : ressourceInfoList) {
                            if (ressourceInfo != null) {
                                //??????????????????
                                JobSignalResinfo signalResinfo = new JobSignalResinfo();
                                signalResinfo.setId(AssignUtils.getUUid());
                                signalResinfo.setSignalRessourceId(jobSignalRes.getId());
                                signalResinfo.setStatus(AssignUtils.encrypt(ressourceInfo.get("Status")));
                                signalResinfo.setModuleId(AssignUtils.encrypt(ressourceInfo.get("ModuleID")));
                                signalResinfo.setActualAmount(AssignUtils.encrypt(ressourceInfo.get("ActualAmount")));
                                signalResinfo.setUnit(AssignUtils.encrypt(ressourceInfo.get("Unit")));
                                //??????jobId
                                if (ressourceInfo.get("JobID") != null) {
                                    jobSyncRecordService.updateJobId(AssignUtils.encrypt(ressourceInfo.get("JobID")));
                                }
                                signalResinfo.setJobId(AssignUtils.encrypt(ressourceInfo.get("JobID")));
                                signalResinfo.setExternalId(AssignUtils.encrypt(ressourceInfo.get("ExternalID")));
                                signalResinfo.setProductId(AssignUtils.encrypt(ressourceInfo.get("ProductID")));
                                signalResinfo.setInsertDateMonth(insertDateMonth);

                                jobSignalResinfoMapper.insert(signalResinfo);

                                //Comment
                                if (ressourceInfo.get("Comment") != null) {
                                    commentData((ArrayList) ressourceInfo.get("Comment"), signalResinfo.getId());
                                }

                                //ResourceSet
                                if (ressourceInfo.get("ResourceSet") != null) {
                                    resourceSetData((ArrayList) ressourceInfo.get("ResourceSet"), signalResinfo.getId());
                                }

                            }
                        }
                    }
                }
            }
        }


    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "commentData", businessType = BusinessType.INSERT)
    public void commentData(List<Map> commentList, String signalResinfoId) throws Exception {
        if (commentList.size() > 0) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map commentMap : commentList) {
                JobSignalResinfoComment comment = new JobSignalResinfoComment();
                comment.setId(AssignUtils.getUUid());
                comment.setSignalRessourceinfoId(signalResinfoId);
                comment.setName(AssignUtils.encrypt(commentMap.get("Name")));
                //comment.setCommentText(AssignUtils.encrypt(commentMap.get("#text")));
                comment.setInsertDateMonth(insertDateMonth);

                jobSignalResinfoCommentMapper.insert(comment);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "resourceSetData", businessType = BusinessType.INSERT)
    public void resourceSetData(List<Map> signalResinfoResourcesetList, String signalResinfoId) throws Exception {
        if (signalResinfoResourcesetList.size() > 0) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //Resourceset
            for (Map resourcesetMap : signalResinfoResourcesetList) {
                JobSignalResinfoResourceset resourceset = new JobSignalResinfoResourceset();
                resourceset.setId(AssignUtils.getUUid());
                resourceset.setSignalRessourceinfoId(signalResinfoId);
                resourceset.setModuleId(AssignUtils.encrypt(resourcesetMap.get("ModuleID")));
                resourceset.setResourceName(AssignUtils.encrypt(resourcesetMap.get("ResourceName")));
                resourceset.setLevel(AssignUtils.encrypt(resourcesetMap.get("Level")));
                resourceset.setUnit(AssignUtils.encrypt(resourcesetMap.get("Unit")));
                resourceset.setName(AssignUtils.encrypt(resourcesetMap.get("Name")));
                resourceset.setInsertDateMonth(insertDateMonth);

                jobSignalResinfoResourcesetMapper.insert(resourceset);

                //Resource
                if (resourcesetMap.get("Resource") != null) {
                    List<Map> resourceList = (ArrayList) resourcesetMap.get("Resource");
                    if (resourceList.size() > 0) {
                        for (Map resourceMap : resourceList) {
                            JobSignalResinfoResourcesetResource resource = new JobSignalResinfoResourcesetResource();
                            resource.setId(AssignUtils.getUUid());
                            resource.setSignalRessourceinfoResourcesetId(resourceset.getId());
                            resource.setDescriptiveName(AssignUtils.encrypt(resourceMap.get("DescriptiveName")));
                            resource.setInsertDateMonth(insertDateMonth);

                            jobSignalResinfoResourcesetResourceMapper.insert(resource);

                            //AmountPool
                            if (resourceMap.get("AmountPool") != null) {
                                AmountPoolData((ArrayList) resourceMap.get("AmountPool"), resource.getId());
                            }

                            //MiscConsumable
                            if (resourceMap.get("MiscConsumable") != null) {
                                MiscConsumableData((ArrayList) resourceMap.get("MiscConsumable"), resource.getId());
                            }

                        }

                    }
                }

            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "AmountPoolData", businessType = BusinessType.INSERT)
    public void AmountPoolData(List<Map> AmountPoolList, String resourceId) throws Exception {
        if (AmountPoolList.size() > 0) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map amountPoolMap : AmountPoolList) {
                JobSignalResinfoResourcesetResourceAmountpool amountpool = new JobSignalResinfoResourcesetResourceAmountpool();
                amountpool.setId(AssignUtils.getUUid());
                amountpool.setSignalRessourceinfoResourcesetResourceId(resourceId);
                amountpool.setActualAmount(AssignUtils.encrypt(amountPoolMap.get("ActualAmount")));
                amountpool.setAmount(AssignUtils.encrypt(amountPoolMap.get("Amount")));
                amountpool.setInsertDateMonth(insertDateMonth);

                jobSignalResinfoResourcesetResourceAmountpoolMapper.insert(amountpool);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "MiscConsumableData", businessType = BusinessType.INSERT)
    public void MiscConsumableData(List<Map> MiscConsumableList, String resourceId) throws Exception {
        if (MiscConsumableList.size() > 0) {
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map miscConsumableMap : MiscConsumableList) {
                JobSignalResinfoResourcesetResourceMiscconsumable miscConsumable = new JobSignalResinfoResourcesetResourceMiscconsumable();
                miscConsumable.setId(AssignUtils.getUUid());
                miscConsumable.setSignaRessourceinfoResourcesetResourceId(resourceId);
                miscConsumable.setConsumableType(AssignUtils.encrypt(miscConsumableMap.get("ConsumableType")));
                miscConsumable.setInsertDateMonth(insertDateMonth);

                jobSignalResinfoResourcesetResourceMiscconsumableMapper.insert(miscConsumable);
            }

        }
    }


    /*****************************************************signalStatus************************************************************/


    /**
     * signalStatus
     *
     * @param signalStatusList
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "manageStatus", businessType = BusinessType.INSERT)
    public void manageStatus(List<Map> signalStatusList) throws Exception {
        String id = null;
        String time = null;//time??????
        int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
        if (signalStatusList != null && signalStatusList.size() > 0) {
            for (Map signalStatus : signalStatusList) {
                if (signalStatus != null) {

                    id = AssignUtils.getUUid();
                    //??????
                    JobSignalStatus jobSignalStatus = new JobSignalStatus();
                    jobSignalStatus.setId(id);
                    jobSignalStatus.setTime(AssignUtils.encrypt(signalStatus.get("Time")));
                    jobSignalStatus.setDeviceId(AssignUtils.encrypt(signalStatus.get("DeviceID")));
                    jobSignalStatus.setInsertDateMonth(insertDateMonth);
                    jobSignalStatus.setInsertTime(new Date().getTime());
                    jobSignalStatus.setCreateTime(new Date());
                    jobSignalStatus.setInsertDate(DateUtils.theDayStart(new Date()));
                    jobSignalStatusMapper.insert(jobSignalStatus);

                    //?????????
                    JobSignalStatusNew jobSignalStatusNew = new JobSignalStatusNew();
                    jobSignalStatusNew.setId(id);
                    jobSignalStatusNew.setTime(signalStatus.get("Time") == null ? null : signalStatus.get("Time").toString());
                    jobSignalStatusNew.setDeviceId(signalStatus.get("DeviceID") == null ? null : signalStatus.get("DeviceID").toString());
                    jobSignalStatusNew.setInsertDateMonth(insertDateMonth);
                    jobSignalStatusNew.setInsertTime(new Date().getTime());
                    jobSignalStatusNew.setCreateTime(new Date());
                    jobSignalStatusNew.setInsertDate(DateUtils.theDayStart(new Date()));
                    jobSignalStatusNewMapper.insert(jobSignalStatusNew);


                    //Header
                    List<Map> headerList = (ArrayList) signalStatus.get("Header");
                    if (headerList != null && headerList.size() > 0) {
                        for (Map headerMap : headerList) {
                            if (headerMap != null) {
                                id = AssignUtils.getUUid();

                                if (headerMap.get("Time") != null) {
                                    time = DateUtils.strToDateStr(headerMap.get("Time").toString());
                                }
                                //??????????????????
                                //??????
                                JobSignalStatusHeader header = new JobSignalStatusHeader();
                                header.setId(id);
                                header.setSignalStatusId(jobSignalStatus.getId());
                                header.setRefId(AssignUtils.encrypt(headerMap.get("refID")));
                                header.setDeviceId(AssignUtils.encrypt(headerMap.get("DeviceID")));
                                header.setTime(AssignUtils.encrypt(time));
                                header.setDataTime(DateUtils.timeZoneToDate(headerMap.get("Time").toString()));
                                header.setCreateTime(new Date());
                                header.setInsertTime(new Date().getTime());
                                header.setInsertDateMonth(insertDateMonth);
                                jobSignalStatusHeaderMapper.insert(header);

                                //?????????
                                JobSignalStatusHeaderNew headerNew = new JobSignalStatusHeaderNew();
                                headerNew.setId(id);
                                headerNew.setSignalStatusId(jobSignalStatus.getId());
                                headerNew.setRefId(headerMap.get("refID") == null ? null : headerMap.get("refID").toString());
                                headerNew.setDeviceId(headerMap.get("DeviceID") == null ? null : headerMap.get("DeviceID").toString());
                                headerNew.setTime(time);
                                headerNew.setDataTime(DateUtils.timeZoneToDate(headerMap.get("Time").toString()));
                                headerNew.setCreateTime(new Date());
                                headerNew.setInsertTime(new Date().getTime());
                                headerNew.setInsertDateMonth(insertDateMonth);
                                jobSignalStatusHeaderNewMapper.insert(headerNew);
                            }
                        }
                    }


                    //DeviceInfo
                    List<Map> deviceInfoList = (ArrayList) signalStatus.get("DeviceInfo");
                    if (deviceInfoList != null && deviceInfoList.size() > 0) {
                        for (Map deviceInfo : deviceInfoList) {
                            if (deviceInfo != null) {
                                id = AssignUtils.getUUid();
                                //??????????????????
                                //??????
                                JobSignalStatusDevice device = new JobSignalStatusDevice();
                                device.setId(id);
                                device.setSignalStatusId(jobSignalStatus.getId());
                                device.setDeviceId(AssignUtils.encrypt(deviceInfo.get("DeviceID")));
                                device.setStatus(AssignUtils.encrypt(deviceInfo.get("Status")));
                                device.setStatusDetails(AssignUtils.encrypt(deviceInfo.get("StatusDetails")));
                                device.setSpeed(AssignUtils.encrypt(deviceInfo.get("Speed")));
                                device.setEventId(AssignUtils.encrypt(deviceInfo.get("EventID")));
                                device.setTotalProductionCounter(AssignUtils.encrypt(deviceInfo.get("TotalProductionCounter")));
                                device.setProductionCounter(AssignUtils.encrypt(deviceInfo.get("ProductionCounter")));
                                device.setModuleIds(AssignUtils.encrypt(deviceInfo.get("ModuleIDs")));
                                device.setInsertDateMonth(insertDateMonth);
                                jobSignalStatusDeviceMapper.insert(device);

                                //?????????
                                JobSignalStatusDeviceNew deviceNew = new JobSignalStatusDeviceNew();
                                deviceNew.setId(id);
                                deviceNew.setSignalStatusId(jobSignalStatus.getId());
                                deviceNew.setDeviceId(deviceInfo.get("DeviceID") == null ? null : deviceInfo.get("DeviceID").toString());
                                deviceNew.setStatus(deviceInfo.get("Status") == null ? null : deviceInfo.get("Status").toString());
                                deviceNew.setStatusDetails(deviceInfo.get("StatusDetails") == null ? null : deviceInfo.get("StatusDetails").toString());
                                deviceNew.setSpeed(deviceInfo.get("Speed") == null ? null : deviceInfo.get("Speed").toString());
                                deviceNew.setEventId(deviceInfo.get("EventID") == null ? null : deviceInfo.get("EventID").toString());
                                deviceNew.setTotalProductionCounter(deviceInfo.get("TotalProductionCounter") == null ? null : deviceInfo.get("TotalProductionCounter").toString());
                                deviceNew.setProductionCounter(deviceInfo.get("ProductionCounter") == null ? null : deviceInfo.get("ProductionCounter").toString());
                                deviceNew.setModuleIds(deviceInfo.get("ModuleIDs") == null ? null : deviceInfo.get("ModuleIDs").toString());
                                deviceNew.setInsertDateMonth(insertDateMonth);
                                jobSignalStatusDeviceNewMapper.insert(deviceNew);

                                //JobPhase
                                if (deviceInfo.get("JobPhase") != null) {
                                    jobPhaseData((ArrayList) deviceInfo.get("JobPhase"), device.getId());
                                }

                                //Event
                                if (deviceInfo.get("Event") != null) {
                                    jobEventData((ArrayList) deviceInfo.get("Event"), device.getId());
                                }

                            }
                        }
                    }
                }
            }
        }


    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "jobPhaseData", businessType = BusinessType.INSERT)
    public void jobPhaseData(List<Map> jobPhaseList, String jobSignalStatusDeviceId) throws Exception {
        if (jobPhaseList.size() > 0) {
            String id = null;
            String time = null;  //time????????????
            String jobId = null;   //jobId??????
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            //JobPhase
            for (Map jobPhase : jobPhaseList) {
                id = AssignUtils.getUUid();

                if (jobPhase.get("StartTime") != null) {
                    time = DateUtils.strToDateStr(jobPhase.get("StartTime").toString());
                    log.info("JobPhase StartTime:" + time);
                }

                //??????
                JobSignalStatusDevicePhase devicePhase = new JobSignalStatusDevicePhase();
                devicePhase.setId(id);
                devicePhase.setSignalStatusDeviceId(jobSignalStatusDeviceId);
                devicePhase.setStatus(AssignUtils.encrypt(jobPhase.get("Status")));
                devicePhase.setAmount(AssignUtils.encrypt(jobPhase.get("Amount")));
                devicePhase.setStartTime(AssignUtils.encrypt(time));
                devicePhase.setTotalAmount(AssignUtils.encrypt(jobPhase.get("TotalAmount")));
                devicePhase.setWaste(AssignUtils.encrypt(jobPhase.get("Waste")));
                devicePhase.setPercentCompleted(AssignUtils.encrypt(jobPhase.get("PercentCompleted")));
                devicePhase.setCostCenterId(AssignUtils.encrypt(jobPhase.get("CostCenterID")));
                //??????jobId
                if (jobPhase.get("JobID") != null) {
                    jobId = URLDecoder.decode(jobPhase.get("JobID").toString(), "utf-8");
                    log.info("JobPhase jodId??????:" + jobId);
                    jobSyncRecordService.updateJobIdNew(jobId, time);
                    devicePhase.setJobId(AssignUtils.encrypt(jobId));
                } else {
                    devicePhase.setJobId("");
                }
                devicePhase.setWorkstepId(AssignUtils.encrypt(jobPhase.get("WorkStepID")));
                devicePhase.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhaseMapper.insert(devicePhase);


                //?????????
                JobSignalStatusDevicePhaseNew devicePhaseNew = new JobSignalStatusDevicePhaseNew();
                devicePhaseNew.setId(id);
                devicePhaseNew.setSignalStatusDeviceId(jobSignalStatusDeviceId);
                devicePhaseNew.setStatus(jobPhase.get("Status") == null ? null : jobPhase.get("Status").toString());
                devicePhaseNew.setAmount(jobPhase.get("Amount") == null ? null : jobPhase.get("Amount").toString());
                devicePhaseNew.setStartTime(time);
                devicePhaseNew.setTotalAmount(jobPhase.get("TotalAmount") == null ? null : jobPhase.get("TotalAmount").toString());
                devicePhaseNew.setWaste(jobPhase.get("Waste") == null ? null : jobPhase.get("Waste").toString());
                devicePhaseNew.setPercentCompleted(jobPhase.get("PercentCompleted") == null ? null : jobPhase.get("PercentCompleted").toString());
                devicePhaseNew.setCostCenterId(jobPhase.get("CostCenterID") == null ? null : jobPhase.get("CostCenterID").toString());
                devicePhaseNew.setJobId(jobId);
                devicePhaseNew.setWorkstepId(jobPhase.get("WorkStepID") == null ? null : jobPhase.get("WorkStepID").toString());
                devicePhaseNew.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhaseNewMapper.insert(devicePhaseNew);


                //MISDetails
                if (jobPhase.get("MISDetails") != null) {
                    MISDetailsData((ArrayList) jobPhase.get("MISDetails"), devicePhase.getId());
                }

                //Activity
                if (jobPhase.get("Activity") != null) {
                    ActivityData((ArrayList) jobPhase.get("Activity"), devicePhase.getId());
                }

                //Part
                if (jobPhase.get("Part") != null) {
                    PartData((ArrayList) jobPhase.get("Part"), devicePhase.getId());
                }
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "MISDetailsData", businessType = BusinessType.INSERT)
    public void MISDetailsData(List<Map> MISDetailsList, String jobSignalStatusDevicePhaseId) throws Exception {
        if (MISDetailsList.size() > 0) {
            String id = AssignUtils.getUUid();
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map MISDetails : MISDetailsList) {
                //??????
                JobSignalStatusDevicePhaseMisDetails misDetails = new JobSignalStatusDevicePhaseMisDetails();
                misDetails.setId(id);
                misDetails.setSignalStatusDevicePhaseId(jobSignalStatusDevicePhaseId);
                misDetails.setWorkType(AssignUtils.encrypt(MISDetails.get("WorkType")));
                misDetails.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhaseMisDetailsMapper.insert(misDetails);

                //?????????
                JobSignalStatusDevicePhaseMisDetailsNew misDetailsNew = new JobSignalStatusDevicePhaseMisDetailsNew();
                misDetailsNew.setId(id);
                misDetailsNew.setSignalStatusDevicePhaseId(jobSignalStatusDevicePhaseId);
                misDetailsNew.setWorkType(MISDetails.get("WorkType") == null ? null : MISDetails.get("WorkType").toString());
                misDetailsNew.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhaseMisDetailsNewMapper.insert(misDetailsNew);

            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "ActivityData", businessType = BusinessType.INSERT)
    public void ActivityData(List<Map> ActivityList, String jobSignalStatusDevicePhaseId) throws Exception {
        if (ActivityList.size() > 0) {
            String id = null;
            String time = null; //time????????????
            String activityName = null; //activityName ??????
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map Activity : ActivityList) {
                id = AssignUtils.getUUid();
                //time??????
                if (Activity.get("StartTime") != null) {
                    time = DateUtils.strToDateStr(Activity.get("StartTime").toString());
                }
                //??????
                JobSignalStatusDevicePhaseActivity activity = new JobSignalStatusDevicePhaseActivity();
                activity.setId(id);
                activity.setActivityId(Activity.get("ActivityID").toString());
                activity.setSignalStatusDevicePhaseId(jobSignalStatusDevicePhaseId);
                if (Activity.get("ActivityName") != null) {
                    activityName = URLDecoder.decode(Activity.get("ActivityName").toString(), "utf-8");
                    log.info("activityName: " + activityName);
                    activity.setActivityName(AssignUtils.encrypt(activityName));
                } else {
                    activity.setActivityName("");
                }
                activity.setActivityStartTime(AssignUtils.encrypt(time));
                activity.setActivityPersonalId(AssignUtils.encrypt(Activity.get("PersonalID")));
                activity.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhaseActivityMapper.insert(activity);

                //?????????
                JobSignalStatusDevicePhaseActivityNew activityNew = new JobSignalStatusDevicePhaseActivityNew();
                activityNew.setId(id);
                activityNew.setActivityId(Activity.get("ActivityID") == null ? null : Activity.get("ActivityID").toString());
                activityNew.setSignalStatusDevicePhaseId(jobSignalStatusDevicePhaseId);
                activityNew.setActivityName(activityName);
                activityNew.setActivityStartTime(time);
                activityNew.setActivityPersonalId(Activity.get("PersonalID") == null ? null : Activity.get("PersonalID").toString());
                activityNew.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhaseActivityNewMapper.insert(activityNew);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "PartData", businessType = BusinessType.INSERT)
    public void PartData(List<Map> PartList, String jobSignalStatusDevicePhaseId) throws Exception {
        if (PartList.size() > 0) {
            String id = null;
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map Part : PartList) {
                id = AssignUtils.getUUid();
                //??????
                JobSignalStatusDevicePhasePart part = new JobSignalStatusDevicePhasePart();
                part.setId(id);
                part.setSignalStatusDevicePhaseId(jobSignalStatusDevicePhaseId);
                part.setSheetName(AssignUtils.encrypt(Part.get("SheetName")));
                part.setSide(AssignUtils.encrypt(Part.get("Side")));
                part.setInsertDateMonth(insertDateMonth);
                JobSignalStatusDevicePhasePartMapper.insert(part);

                //?????????
                JobSignalStatusDevicePhasePartNew partNew = new JobSignalStatusDevicePhasePartNew();
                partNew.setId(id);
                partNew.setSignalStatusDevicePhaseId(jobSignalStatusDevicePhaseId);
                partNew.setSheetName(Part.get("SheetName") == null ? null : Part.get("SheetName").toString());
                partNew.setSide(Part.get("Side") == null ? null : Part.get("Side").toString());
                partNew.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDevicePhasePartNewMapper.insert(partNew);
            }

        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "jobEventData", businessType = BusinessType.INSERT)
    public void jobEventData(List<Map> jobEventList, String jobSignalStatusDeviceId) throws Exception {
        if (jobEventList.size() > 0) {
            String id = null;
            String eventValue = null;  //eventValue??????
            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
            for (Map jobEvent : jobEventList) {
                id = AssignUtils.getUUid();
                String str = String.valueOf(jobEvent.get("EventID"));

                //??????
                JobSignalStatusDeviceEvent event = new JobSignalStatusDeviceEvent();
                event.setId(id);
                event.setEventId(AssignUtils.encrypt(AssignUtils.formatValue(str)));
                event.setSignaStatusDeviceId(jobSignalStatusDeviceId);
                if (jobEvent.get("EventValue") != null) {
                    eventValue = URLDecoder.decode(jobEvent.get("EventValue").toString(), "utf-8");
                    log.info("eventValue:" + eventValue);
                    event.setEventValue(AssignUtils.encrypt(eventValue));
                } else {
                    event.setEventValue("");
                }
                event.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDeviceEventMapper.insert(event);

                //?????????
                JobSignalStatusDeviceEventNew eventNew = new JobSignalStatusDeviceEventNew();
                eventNew.setId(id);
                eventNew.setEventId(AssignUtils.formatValue(str));
                eventNew.setSignaStatusDeviceId(jobSignalStatusDeviceId);
                eventNew.setEventValue(eventValue);
                eventNew.setInsertDateMonth(insertDateMonth);
                jobSignalStatusDeviceEventNewMapper.insert(eventNew);
            }

        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    @Log(title = "manageNotification", businessType = BusinessType.INSERT)
    public void manageNotification(List<Map> signalNotificationList) throws Exception {
        if (signalNotificationList != null && signalNotificationList.size() > 0) {
            for (Map signalNotification : signalNotificationList) {
                //Notification
                List<Map> NotificationList = (ArrayList) signalNotification.get("Notification");
                if (NotificationList.size() > 0) {
                    for (Map notification : NotificationList) {
                        //??????job_id????????????job_sync_record??????
                        if (notification.get("JobID") != null) {
                            int insertDateMonth = Integer.valueOf(DateUtils.currentYearMonth());
                            JobSyncRecordNew recordNew = new JobSyncRecordNew();
                            recordNew.setId(AssignUtils.getUUid());
                            recordNew.setJobId(URLDecoder.decode(notification.get("JobID").toString(), "utf-8"));
                            recordNew.setSyncStatusJob("N");
                            recordNew.setSyncStatusWorkstep("N");
                            recordNew.setCreateTime(new Date());
                            recordNew.setUpdateTime(new Date());
                            recordNew.setInsertDateMonth(insertDateMonth);
                            jobSyncRecordNewMapper.insert(recordNew);
                        }
                    }

                }
            }
        }
    }


    /**
     * ??????post??????
     *
     * @param serviceUrl
     * @param map
     * @param authorization
     */
    public ResponseEntity<String> sendPostFormData(String serviceUrl, MultiValueMap<String, String> map, String authorization) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json,application/xml,application/xhtml+xml,text/html,text/xml,text/plain");
        headers.add("Authorization", authorization);

        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        JSONObject jsonObject = null;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, String.class);

        //System.out.println("3.2.5????????????????????? : "+responseEntity);
        log.info("3.2.5????????????????????? : " + responseEntity);

        return responseEntity;
    }


}
