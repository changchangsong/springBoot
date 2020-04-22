package com.example.demo.core.utils.thread;

import com.example.demo.controller.TestThread;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    public void thread(){
        class Thread1 extends Thread {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    System.out.println("继承THread:");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        Thread1 thread1 = new Thread1();
        thread1.start();
    }

}
