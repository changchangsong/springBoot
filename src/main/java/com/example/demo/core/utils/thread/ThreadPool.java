package com.example.demo.core.utils.thread;

import com.example.demo.core.service.BaseService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池
 */
@Component
public class ThreadPool extends BaseService {

    public void cachedThreadPool() throws Exception {
        Map<String, Object> result = new HashMap<>();
        /**
         * 工具类 ： Executors
         *     ExecutorService newFixedThreadPool() : 创建固定大小的线程池
         *     ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
         *     ExecutorService newSingleThreadExecutor() : 创建单个线程池。 线程池中只有一个线程
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        /**
         * CountDownLatch典型用法1：某一线程在开始运行前等待n个线程执行完毕。
         *   将CountDownLatch的计数器初始化为n new CountDownLatch(n) ，
         *   每当一个任务线程执行完毕，就将计数器减1 countdownlatch.countDown()，
         *   当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
         *   一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
         */
        CountDownLatch countDownLatch = new CountDownLatch(3);
        List<Future<?>> futures = new ArrayList<>();
        futures.add(cachedThreadPool.submit(() -> {
            try {
                System.out.println("线程池: 线程一");
                return "线程池: 线程一";
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                // 每当一个任务线程执行完毕，就将计数器减1
                countDownLatch.countDown();
            }
        }));

        futures.add(cachedThreadPool.submit(() -> {
            try {
                System.out.println("线程池: 线程二");
                return "线程池: 线程二";
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                // 每当一个任务线程执行完毕，就将计数器减1
                countDownLatch.countDown();
            }
        }));

        futures.add(cachedThreadPool.submit(() -> {
            try {
                System.out.println("线程池: 线程三");
                return "线程池: 线程三";
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                // 每当一个任务线程执行完毕，就将计数器减1
                countDownLatch.countDown();
            }
        }));

        //关闭线程池。在实际业务中是不需要关闭线程池的，因为线程池是随着系统启动。随着系统停止而停止
        cachedThreadPool.shutdown();
        //当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
        //一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
        countDownLatch.await();
        result.put("status", true);
        for (Future<?> future : futures) {
            try {
                System.out.println("线程回调结果：" + future.get());
            } catch (Exception e) {
                e.printStackTrace();
                result.put("status", false);
                result.put("msg", e.getMessage());
                break;
            }
        }
        this.setResult(result);
    }
}
