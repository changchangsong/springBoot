package com.example.demo.controller;

import com.example.demo.core.utils.thread.CallableService;
import com.example.demo.core.utils.thread.RunnableService;
import com.example.demo.core.utils.thread.ThreadService;
import com.example.demo.core.utils.thread.ThreadPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testThread")
@Api(value = "/testThread", description = "测试线程相关")
public class TestThread {

    @Autowired
    private ThreadPool threadPool;
    @Autowired
    private RunnableService runnableService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private CallableService callableService;


    @ApiOperation(value="继承thread")
    @RequestMapping(value="/thread",method= RequestMethod.GET)
    public void TestThread(){
        System.out.println("开始");
        threadService.thread();
        System.out.println("结束");
}
    

    @ApiOperation(value="Runnable")
    @RequestMapping(value="/Runnable",method= RequestMethod.GET)
    public void testRunnable() throws Exception{
        System.out.println("开始");
        runnableService.runnable();
        System.out.println("结束");
    }

    @ApiOperation(value="Callable")
    @RequestMapping(value="/Callable",method= RequestMethod.GET)
    public void testCallable() throws Exception{
        System.out.println("开始");
        callableService.callable();
        System.out.println("结束");
    }

    @ApiOperation(value="匿名内部类的方式创建线程")
    @RequestMapping(value="/testAnonymousInnerClass",method= RequestMethod.GET)
    public void testAnonymousInnerClass() throws Exception{
        System.out.println("开始");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("匿名内部类的方式创建线程");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("结束");
    }

    @ApiOperation(value="线程池")
    @RequestMapping(value="/threadPool",method= RequestMethod.GET)
    public void testThreadPool() throws Exception{
        threadPool.cachedThreadPool();
    }

}
