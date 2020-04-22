package com.example.demo.controller;

import com.example.demo.service.TestWangYingSpringMVCService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${comments}
 *
 * @author Song
 * @email 852146603@qq.com
 * @date 2019-03-20 10:37:40
 */
@RestController
@RequestMapping("TestWangYingSpringMVC")
@Api(value = "/TestWangYingSpringMVC", description = "异常表（王银版测试）")
public class TestWangYingSpringMVC {

    @Autowired(required = false)
    private TestWangYingSpringMVCService testWangYingSpringMVCService;

    /**
     * 信息
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    @ApiOperation(value = "根据id返回一条数据，参数:id")
    @ApiImplicitParam(name = "id", paramType = "query", value = "编号", required = true, dataType = "String", defaultValue = "0001qqfrwYoDqCy4yQaxia")
    public void info(@RequestParam(name = "id", required = true) String id) {
        testWangYingSpringMVCService.queryObject(id);
    }
}
