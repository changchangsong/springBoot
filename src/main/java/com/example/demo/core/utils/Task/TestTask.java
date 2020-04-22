package com.example.demo.core.utils.Task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class TestTask {

    public void Sleep() {
        try {
            Thread.sleep(5000);
            System.out.print("耗时5秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async //对这个方法使用异步处理
    public void Async() {
        try {
            Thread.sleep(3000);
            System.out.println("线程名字------->" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void Task() {
        try {
            Thread.sleep(3000);
            System.out.println("线程名字------->" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public Future<String> asyncMethodWithReturnType() {
        Future<String> future;
        try {
            Thread.sleep(3000);
            System.out.println("线程名字------->"+ Thread.currentThread().getName());
            future = new AsyncResult<>("success !");
        } catch (InterruptedException e) {
            future = new AsyncResult<>("fail !");
        }
        return future;
    }

}
