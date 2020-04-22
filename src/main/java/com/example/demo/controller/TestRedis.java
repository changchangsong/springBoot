package com.example.demo.controller;

import java.util.concurrent.TimeUnit;

import com.example.demo.core.utils.redis.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("redis")
@Api(value = "/redis", description = "测试redis")
public class TestRedis {

    public static final Logger log = LoggerFactory.getLogger(TestRedis.class);

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "返回数据")
    @ApiImplicitParam(name = "key", paramType = "query", value = "根据ID返回数据", required = true, dataType = "String", defaultValue = "1")
    public String query( @RequestParam(name = "key", required = true) String key) {
        //查询缓存中是否存在
        boolean hasKey = redisUtils.exists(key);
        String str = "";
        if (hasKey) {
            //获取缓存
            Object object = redisUtils.get(key);
            str ="从缓存获取的数据key="+key+",value=" + object.toString();
            log.info(str);
        } else {
            System.out.println("缓存中没有key="+key);
            str = "缓存中没有key="+key+"的值";
            log.info(str);
        }
        return str;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ApiOperation(value = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", paramType = "query", value = "key", required = true, dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "value", paramType = "query", value = "value", required = true, dataType = "String", defaultValue = "1")
    })
    public String add( @RequestParam(name = "key", required = true) String key,
                       @RequestParam(name = "value", required = true) String value) {
        //查询缓存中是否存在
        boolean hasKey = redisUtils.exists(key);
        String str = "";
        if (hasKey) {
            //获取缓存
            Object object = redisUtils.get(key);
            str = "缓存中已经存在key=" + key+"数据"+",value="+object.toString();
            log.info(str);
        } else {
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            redisUtils.set(key, value, 10L, TimeUnit.MINUTES);
            str = "数据插入缓存key=" + key+",value="+value+"成功";
            log.info(str);
        }
        return str;
    }


    /**
     * 单条删除
     **/
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除数据")
    @ApiImplicitParam(name = "key", paramType = "query", value = "根据ID删除数据", required = true, dataType = "String", defaultValue = "1")
    public String delete( @RequestParam(name = "key", required = true) String key) {
        //查询缓存中是否存在
        boolean hasKey = redisUtils.exists(key);
        String str ="";
        if(hasKey){
            this.redisUtils.remove(key);
            str = "删除缓存数据key="+key;
            log.info(str);
        }else{
            System.out.println("缓存中没有key="+key);
            str = "缓存中没有key="+key;
            log.info(str);
        }
        return str;
    }


    @RequestMapping(value = "/post/hashKey", method = RequestMethod.POST)
    @ApiOperation(value = "新增数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", paramType = "query", value = "key", required = true, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "hashKey", paramType = "query", value = "hashKey", required = true, dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "value", paramType = "query", value = "value", required = true, dataType = "String", defaultValue = "")
    })
    public String addHMset(
            @RequestParam(name = "key", required = true) String key,
            @RequestParam(name = "hashKey", required = true) String hashKey,
            @RequestParam(name = "value", required = true) String value) {
        //查询缓存中是否存在
        Object hasValue = redisUtils.hmGet(key,hashKey);
        String str = "";
        if (StringUtils.isNotEmpty((String) hasValue)) {
            //获取缓存
            str = "缓存中已经存在key=" + key+",hashKey="+hashKey+",value="+hasValue.toString();
            log.info(str);
        } else {
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            redisUtils.hmSet(key, hashKey, value);
            str = "数据插入缓存key=" + key+",hashKey="+hashKey+"成功"+",value="+value;
            log.info(str);
        }
        return str;
    }
}
