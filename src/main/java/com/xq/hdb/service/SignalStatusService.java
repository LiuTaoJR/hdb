package com.xq.hdb.service;


import com.xq.hdb.vo.SignalStatusVO;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SignalStatusService {



    List<SignalStatusVO> postPullSignalStatus(SignalStatusVO signalStatusVO );




    Map getPullSignalStatus(String jobId, Long speed, String deviceId, String eventId, String stutas);




    Map getPullSignalStatusByDate(Date date, String deviceId, Integer currentPage, Integer pageSize);


}
