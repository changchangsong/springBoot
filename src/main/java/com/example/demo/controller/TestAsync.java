package com.example.demo.controller;

import com.example.demo.core.utils.Task.TestTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@RequestMapping("testAsync")
@Api(value = "/testAsync", description = "测试异步任务")
public class TestAsync {

    @Autowired
    private TestTask testTask;

    /**
     * 测试@Async
     */
    @RequestMapping(value = "/Async", method = RequestMethod.GET)
    @ApiOperation(value = "测试@Async")
    public void Async() throws Exception {
        System.out.println("执行开始");
//        testAsyncService.Sleep();
        testTask.Async();
        System.out.println("执行完毕");
    }


    /**
     * 测试线程池(异步无返回值)
     */
    @RequestMapping(value = "/TaskNoVlue", method = RequestMethod.GET)
    @ApiOperation(value = "测试线程池(异步无返回值)")
    public void Task() throws Exception {
        System.out.println("执行开始");
        testTask.Task();
        System.out.println("执行完毕");
    }

    /**
     * 测试线程池（异步调用有返回值）
     */
    @RequestMapping(value = "/asyncMethodWithReturnType", method = RequestMethod.GET)
    @ApiOperation(value = "测试线程池（异步调用有返回值）")
    public void asyncMethodWithReturnType() throws Exception {
        System.out.println("执行开始");
        Future<String> future = testTask.asyncMethodWithReturnType();
        System.out.println("执行完毕");
        while (true) {  ///这里使用了循环判断，等待获取结果信息
            if (future.isDone()) {  //判断是否执行完毕
                System.out.println("相应成功---->" + future.get());
                break;
            }
            System.out.println("等待返回值..... ");
            Thread.sleep(1000);
        }
    }


}
