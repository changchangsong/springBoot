package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lenovo on 2020/3/19.
 */
@RestController
@RequestMapping("testSwagger")
@Api(value = "/testSwagger", description = "测试swagger")
public class TestSwagger {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "返回分页数据，参数:page,record,sortName,sortType以及其他需要的查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", paramType = "query", value = "分页参数，页码，从1开始，默认第1页", required = false, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "record", paramType = "query", value = "分页参数，每页记录数，默认10条", required = false, dataType = "String", defaultValue = "10"),
    })
    public Map greeting(@RequestParam(name = "page", required = false) String page,
                        @RequestParam(name = "record", required = false) String record,
                        HttpServletRequest request) {
        //获取参数
        Map<String, String[]> params = request.getParameterMap();
        Set<String> keys = params.keySet();
        for(String key: keys){
            String[] value = params.get(key);
            System.out.println(key+"="+value[0]);
        }

        List list = new ArrayList();
        Map map = new HashMap();
        map.put("code","1");
        map.put("msg","查询成功");
        map.put("rows",list);
        return map;
    }


}
