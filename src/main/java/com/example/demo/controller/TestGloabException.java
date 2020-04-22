package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TestGloabException")
@Api(value = "/TestGloabException", description = "测试全局异常拦截")
public class TestGloabException {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "异常拦截")
    public void query() throws Exception{
         throw new Exception("异常");
    }
}
