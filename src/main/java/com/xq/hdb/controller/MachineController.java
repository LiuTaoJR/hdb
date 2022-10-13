package com.xq.hdb.controller;


import com.xq.hdb.service.JobService;
import com.xq.hdb.utils.AssignUtils;
import com.xq.hdb.vo.ResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/code")
public class MachineController {

    @Autowired
    private JobService jobService;

    @GetMapping("/decrypt")
    public Object decrypt(String str){
        return jobService.decryption(str);
    }

    @GetMapping("/jm")
    public Object jm(String str){
        return AssignUtils.encrypt(str);
    }

    @PostMapping("/getMachineList")
    public ResVo getMachineList(){
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("machineId",4);
        map.put("machineCode","XB00");
        map.put("machineName","CD 102-5+L");
        map.put("machineCategoryCode","2");
        map.put("machineCategoryCodeStr","打印机");
        map.put("status",1);
        map.put("statusStr",null);
        map.put("factoryId","1025");
        map.put("workDetailCode",null);
        map.put("workDetailId",null);

        Map<String,Object> map1=new HashMap<>();
        map1.put("machineId",5);
        map1.put("machineCode","XB001388");
        map1.put("machineName","CD 102-8+L C01-0325");
        map1.put("machineCategoryCode","2");
        map1.put("machineCategoryCodeStr","打印机");
        map1.put("status",1);
        map1.put("statusStr",null);
        map1.put("factoryId","4406");
        map1.put("workDetailCode",null);
        map1.put("workDetailId",null);

        Map<String,Object> map2=new HashMap<>();
        map2.put("machineId",6);
        map2.put("machineCode","XB001627");
        map2.put("machineName","CX 104-7+L(RS) C01-0441");
        map2.put("machineCategoryCode","2");
        map2.put("machineCategoryCodeStr","打印机");
        map2.put("status",1);
        map2.put("statusStr",null);
        map2.put("factoryId","7706");
        map2.put("workDetailCode",null);
        map2.put("workDetailId",null);

        Map<String,Object> map3=new HashMap<>();
        map3.put("machineId",7);
        map3.put("machineCode","XG000087");
        map3.put("machineName","XL 162-4 (RS)");
        map3.put("machineCategoryCode","2");
        map3.put("machineCategoryCodeStr","打印机");
        map3.put("status",1);
        map3.put("statusStr",null);
        map3.put("factoryId","1162");
        map3.put("workDetailCode",null);
        map3.put("workDetailId",null);
        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return new ResVo("1","查询成功",list);
    }
}
