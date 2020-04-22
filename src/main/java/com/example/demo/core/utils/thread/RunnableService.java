package com.example.demo.core.utils.thread;

import org.springframework.stereotype.Component;

@Component
public class RunnableService {

    public void runnable() throws Exception {
        class Thread2 implements Runnable {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("实现Runnable " );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Thread thread = new Thread(new Thread2());
        thread.start();
    }

}
