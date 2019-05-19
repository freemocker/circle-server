package com.ubatis.circleserver.util.threadpool;

import java.util.concurrent.*;

public class ThreadPool {

    public ThreadPool() {
        executor.prestartAllCoreThreads(); // 启动所有核心线程
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    private static int corePoolSize = 2; // 核心线程池大小
    private static int maximumPoolSize = 6; // 最大线程池大小
    private static long keepAliveTime = 10; // 线程最大空闲时间
    private static TimeUnit unit = TimeUnit.SECONDS;
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2); // 线程等待队列
    private static ThreadFactory threadFactory = new NameTreadFactory(); // 线程创建工厂
    private static RejectedExecutionHandler handler = new MyIgnorePolicy(); // 拒绝策略
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            corePoolSize
            , maximumPoolSize
            , keepAliveTime
            , unit
            , workQueue
            , threadFactory
            , handler);

    private static volatile ThreadPool singleton = null;

    public static ThreadPool getInstance() {
        if (singleton == null) {
            synchronized (ThreadPool.class) {
                singleton = new ThreadPool();
            }
        }
        return singleton;
    }

}
