package com.xq.hdb.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Slf4j
public class IndexController {


    /**
     * 打开首页
     *
     * @return
     */
    @GetMapping("/index")
    public String opendIndex() {
        log.info("这是index页面");
        return "index.html";
    }

    @GetMapping("/decryption")
    public String openDecryption() {
        return "decryption.html";
    }


}
