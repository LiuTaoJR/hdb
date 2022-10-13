package com.xq.hdb.service;


import com.xq.hdb.vo.SignalStatusVO;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SignalStatusService {

    List<SignalStatusVO> postPullSignalStatus(SignalStatusVO signalStatusVO );

    Map getPullSignalStatus(String jobId, Long speed, String deviceId, String eventId, String stutas);

    Map getPullSignalStatusByDate(Date date, String deviceId, Integer currentPage, Integer pageSize);

    Map getPullSignalStatusCopyByDate(Date date, String deviceId, Integer currentPage, Integer pageSize);

    //今日统计查询
    List<Map> getStatisticsDate(Date date,String deviceId,String eventId);

    //分组统计查询job_id
    List<Map> getJobIdOnly(Date date, String deviceId);


}
