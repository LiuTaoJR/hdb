package com.xq.hdb.service;


import com.xq.hdb.vo.DeviceDataResultVO;
import com.xq.hdb.vo.DeviceDataVO;
import com.xq.hdb.vo.SignalStatusVO;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SignalStatusService {

    List<SignalStatusVO> postPullSignalStatus(SignalStatusVO signalStatusVO);

    Map getPullSignalStatus(String jobId, Long speed, String deviceId, String eventId, String stutas);

    Map getPullSignalStatusByDate(Date date, String deviceId, Integer currentPage, Integer pageSize);

    Map getPullSignalStatusCopyByDate(Date date, String deviceId, Integer currentPage, Integer pageSize);

    //今日统计查询
    List<Map> getStatisticsDate(Date date, String deviceId, String eventId);

    //分组统计查询job_id
    List<Map> getJobIdOnly(Date date);

    //根据设备id与日期查询出所有相关数据
    List<DeviceDataVO> getJobSignalStatus(Date date, String deviceId);

    //查询成品数量
    List<Map> getProductGood(Date date, String deviceId);

    //查询生产废张
    List<Map> getProductWaste(Date date, String deviceId);

    //查询过版纸
    List<Map> getPassPaper(Date date, String deviceId);

    //查询印刷时间
    List<Map> getPrintTime(Date date, String deviceId);

    DeviceDataResultVO getDeviceDataResult(Date date, String deviceId);

    Map<String, Object> aa(Date date, String deviceId);

}
