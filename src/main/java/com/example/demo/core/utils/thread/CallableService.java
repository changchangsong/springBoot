package com.example.demo.core.utils.thread;

import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by lenovo on 2020/4/12.
 */
@Component
public class CallableService {

    public void callable() throws Exception {
        class Thread3 implements Callable {
            @Override
            public Object call() {
                try {
                    Thread.sleep(2000);
                    System.out.println("实现Callable: ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Thread.currentThread().getName();
            }
        }

        FutureTask<String> stringFutureTask = new FutureTask<String>(new Thread3());
        Thread thread2 = new Thread(stringFutureTask);
        thread2.start();
        //获取返回值
        String s = stringFutureTask.get();
        System.out.println(s);
    }
}
