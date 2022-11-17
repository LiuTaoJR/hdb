package com.xq.hdb.controller;


import com.xq.hdb.service.ScreenService;
import com.xq.hdb.vo.ResVo;
import com.xq.hdb.vo.screen.ScreenDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("screen")
public class screenController {

    @Autowired
    private ScreenService screenService;

    @GetMapping("/getScreenData")
    public ResVo getScreenData(
            @RequestParam(value = "date") Date date){
        try {
            return new ResVo("200","success",screenService.getScreenData(date));
        }catch (Exception e){
            return new ResVo("-1","fail","服务异常");
        }
    }
}
