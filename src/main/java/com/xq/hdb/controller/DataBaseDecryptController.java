package com.xq.hdb.controller;

import com.xq.hdb.service.DataBaseDecryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decrypt")
public class DataBaseDecryptController {

    @Autowired
    private DataBaseDecryptService dataBaseDecryptService;

    @GetMapping("/test")
    public String test(){
        dataBaseDecryptService.decrypt();
        return "成功";
    }
}
