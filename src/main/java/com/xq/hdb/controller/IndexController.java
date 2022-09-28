package com.xq.hdb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {


    /**
     * 打开首页
     * @return
     */
    @GetMapping("/index")
    public String opendIndex(){
        return "index.html";
    }

    @GetMapping("/decryption")
    public String openDecryption(){
        return "decryption.html";
    }


}
