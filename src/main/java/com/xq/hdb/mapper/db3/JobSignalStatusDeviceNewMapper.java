package com.xq.hdb.mapper.db3;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xq.hdb.entity.decrypt.JobSignalStatusDeviceNew;
import com.xq.hdb.vo.DeviceDataVO;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JobSignalStatusDeviceNewMapper extends BaseMapper<JobSignalStatusDeviceNew> {

    List<Map> getDeviceSpeedBySignalStatusId(String signalStatusId);

    List<Map> getDeviceSpeedGroup(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //按设备分组统计
    List<Map> getGroupDevice(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //按设备id统计查询每日数据
    List<DeviceDataVO> getPrintAmountByDeviceId(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("deviceId") String deviceId);


}
